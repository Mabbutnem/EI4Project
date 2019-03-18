package game;

import java.util.Arrays;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;

import boardelement.Character;
import boardelement.Corpse;
import boardelement.IBoardElement;
import boardelement.Monster;
import boardelement.MonsterFactory;
import boardelement.Wizard;
import boardelement.WizardFactory;
import characterlistener.IRangeListener;
import constant.GameConstant;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import listener.ICardDaoListener;
import spell.Card;
import spell.Incantation;
import target.TargetConstraint;
import utility.MapConverter;
import utility.Proba;
import zone.CastZone;
import zone.ZonePick;
import zone.ZoneType;


public class Game
{
	private static ICardDaoListener cardDaoListener;
	
	private static GameConstant gameConstant;

	private String name;
	private int nbWizards;
	private int nbMonstersAndCorpses;
	private Character currentCharacter;
	@JsonIgnore
	private ObservableList<IBoardElement> board;
	@JsonIgnore
	private ObservableList<Boolean> wizardsRange;
	@JsonIgnore
	private ObservableList<Boolean> currentCharacterRange;
	private boolean wizardsTurn;
	private CastZone castZone;
	@JsonIgnore
	private Queue<MonsterFactory> monstersToSpawn;
	private int levelDifficulty;
	
	
	
	public Game()
	{
		board = FXCollections.observableArrayList();
		for(int i = 0; i < gameConstant.getBoardLenght(); i++) { board.add(null); }
		wizardsRange = FXCollections.observableArrayList();
		for(int i = 0; i < gameConstant.getBoardLenght(); i++) { wizardsRange.add(false); }
		currentCharacterRange = FXCollections.observableArrayList();
		for(int i = 0; i < gameConstant.getBoardLenght(); i++) { currentCharacterRange.add(false); }
		
		monstersToSpawn = new LinkedBlockingQueue<>();
	}
	
	public Game(String name, Wizard[] wizards)
	{
		Preconditions.checkState(cardDaoListener != null, "cardDaoListener was not initialised (in static)");
		
		Preconditions.checkState(gameConstant != null, "gameConstant was not initialised (in static)");
		
		Preconditions.checkArgument(wizards.length == gameConstant.getNbWizard(), "wizards lenght was %s but expected %s",
				wizards.length, gameConstant.getNbWizard());
		
		
		this.name = name;
		nbWizards = gameConstant.getNbWizard();
		nbMonstersAndCorpses = 0;
		currentCharacter = null;
		board = FXCollections.observableArrayList();
		for(int i = 0; i < gameConstant.getBoardLenght(); i++) { board.add(null); }
		wizardsRange = FXCollections.observableArrayList();
		for(int i = 0; i < gameConstant.getBoardLenght(); i++) { wizardsRange.add(false); }
		currentCharacterRange = FXCollections.observableArrayList();
		for(int i = 0; i < gameConstant.getBoardLenght(); i++) { currentCharacterRange.add(false); }
		spawnWizards(wizards);
		wizardsTurn = false;
		castZone = new CastZone();
		monstersToSpawn = new LinkedBlockingQueue<>();
		levelDifficulty = 0;
		
	}


	
	public static void setCardDaoListener(ICardDaoListener cardDaoListener) { Game.cardDaoListener = cardDaoListener; }
	
	public static GameConstant getGameConstant() { return gameConstant; }
	public static void setGameConstant(GameConstant gameConstant) { Game.gameConstant = gameConstant; }

	
	
	//Info:
	public String getName() {
		return name;
	}

	@JsonIgnore
	public String[] getWizardsName() {
		List<String> stringList = new LinkedList<>();
		
		for(Wizard w : getWizards()) {
			stringList.add(w.getName());
		}
		
		return stringList.toArray(new String[0]);
	}
	
	
	
	//Card DAO listener:
	@JsonIgnore
	public Card getCard(String name)
	{
		return cardDaoListener.getCard(name);
	}

	@JsonIgnore
	public Card[] getCards(String[] names)
	{
		return cardDaoListener.getCards(names);
	}
	
	
	
	//finish and win condition
	@JsonIgnore
	public boolean isFinished()
	{
		return nbWizards == 0 || levelDifficulty > gameConstant.getLevelMaxDifficulty();
	}

	@JsonIgnore
	public boolean isVictory()
	{
		Preconditions.checkState(isFinished(), "To check if it's a victory, the game must be finished");
		
		return nbWizards > 0;
	}
	


	//Current character
	public Character getCurrentCharacter() {
		return currentCharacter;
	}

	public void setCurrentCharacter(Character character) {
		this.currentCharacter = character;
		refreshCurrentCharacterRange();
	}

	@JsonIgnore
	public void setCurrentCharacter(int currentCharacterIdx)
	{
		Preconditions.checkArgument(indexCorrespondToCharacter(currentCharacterIdx), "currentCharacterIdx don't correspond to a character");
		
		setCurrentCharacter((Character) board.get(currentCharacterIdx));
	}

	@JsonIgnore
	public void setFirstWizardAsCurrentCharacter()
	{
		int i = 0;
		while(i < board.size() && !(board.get(i) instanceof Wizard))
		{
			i++;
		}
		
		if(i >= board.size()) { setCurrentCharacter(null); }
		else { setCurrentCharacter((Character) board.get(i)); }
	}
	
	
	
	//Targets for current character
	private List<Character> getAllVisibleTargetForCurrentCharacter()
	{
		List<Character> lc = new LinkedList<>();
		
		for(int i = 0; i < board.size(); i++)
		{
			if(board.get(i) instanceof Character && currentCharacterRange.get(i))
			{
				lc.add((Character) board.get(i));
			}
		}
		
		return lc;
	}
	
	private Character[] filterTargetByConstraintForCurrentCharacter(List<Character> characters, TargetConstraint[] constraints)
	{
		List<TargetConstraint> constraintsList = Arrays.asList(constraints);
		
		if(	(constraintsList.contains(TargetConstraint.NOTALLY) && getCurrentCharacter() instanceof Wizard)
			|| (constraintsList.contains(TargetConstraint.NOTENEMY) && getCurrentCharacter() instanceof Monster) )
		{
			characters.removeIf(c -> c instanceof Wizard);
		}
		
		if(	(constraintsList.contains(TargetConstraint.NOTALLY) && getCurrentCharacter() instanceof Monster)
				|| (constraintsList.contains(TargetConstraint.NOTENEMY) && getCurrentCharacter() instanceof Wizard) )
		{
			characters.removeIf(c -> c instanceof Monster);
		}
		
		if(constraintsList.contains(TargetConstraint.NOTYOU))
		{
			characters.remove(getCurrentCharacter());
		}
		
		return characters.toArray(new Character[0]);
	}

	@JsonIgnore
	public Character getRandomAvailableTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		Character[] characters = getAllAvailableTargetForCurrentCharacter(constraints);
		
		Preconditions.checkState(characters.length > 0, "No available target for current Character,"
				+ " please verify using hasValidTargetForCurrentCharacter method before using method");
		
		return characters[Proba.nextInt(characters.length)];
	}

	@JsonIgnore
	public Character[] getAllAvailableTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		return filterTargetByConstraintForCurrentCharacter(getAllVisibleTargetForCurrentCharacter(), constraints);
	}

	@JsonIgnore
	public boolean isValidTargetForCurrentCharacter(Character character, TargetConstraint[] constraints)
	{
		return Arrays.asList(getAllAvailableTargetForCurrentCharacter(constraints)).contains(character);
	}

	@JsonIgnore
	public boolean hasValidTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		return getAllAvailableTargetForCurrentCharacter(constraints).length > 0;
	}
	
	//Targets for the AI of monsters
	@JsonIgnore
	public Character[] getAllPossibleTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		int currentCharacterIdx = getBoardElementIdx(getCurrentCharacter());
		
		List<Character> lc = new LinkedList<>();
		
		int possibleRange = getCurrentCharacter().getRange() + getCurrentCharacter().getMove();
		
		for(int r = -possibleRange; r < possibleRange+1; r++)
		{
			int i = currentCharacterIdx+r;
			
			if(indexInBoardBounds(i) && board.get(i) instanceof Character)
			{
				lc.add((Character) board.get(i));
			}
		}
		
		return filterTargetByConstraintForCurrentCharacter(lc, constraints);
	}
	
	
	
	//The range array of the current character
	private void refreshCurrentCharacterRange()
	{
		for(int i = 0; i < currentCharacterRange.size(); i++) { currentCharacterRange.set(i, false); }
		
		if(getCurrentCharacter() != null)
		{
			int range = getCurrentCharacter().getRange();
			int currentCharacterIdx = getBoardElementIdx(getCurrentCharacter());
		
			for(int i = currentCharacterIdx-range; i < currentCharacterIdx+range +1; i++)
			{
				if(indexInBoardBounds(i)) { currentCharacterRange.set(i, true); }
			}
		}
	}

    @JsonProperty("currentCharacterRange")
	public boolean[] getCurrentCharacterRange() {
		return Booleans.toArray(currentCharacterRange);
	}

	@JsonIgnore
	public void addCurrentCharacterRangeListener(ListChangeListener<Boolean> listener) {
		currentCharacterRange.addListener(listener);
	}

	@JsonIgnore
	public void removeCurrentCharacterRangeListener(ListChangeListener<Boolean> listener) {
		currentCharacterRange.removeListener(listener);
	}
	
	
	
	//The range array of all wizards
	private void refreshWizardsRange()
	{
		for(int i = 0; i < wizardsRange.size(); i++) { wizardsRange.set(i, false); }
		
		for(int i = 0; i < board.size(); i++)
		{
			if(board.get(i) instanceof Wizard)
			{
				Wizard w = (Wizard) board.get(i);
				
				int range = w.getRange();
				for(int r = i-range; r < i+range+1; r++)
				{
					if(indexInBoardBounds(r)) { wizardsRange.set(r, true); }
				}
			}
		}
		
	}

    @JsonProperty("wizardsRange")
	public boolean[] getWizardsRange() {
		return Booleans.toArray(wizardsRange);
	}

	@JsonIgnore
	public void addWizardsRangeListener(ListChangeListener<Boolean> listener) {
		wizardsRange.addListener(listener);
	}

	@JsonIgnore
	public void removeWizardsRangeListener(ListChangeListener<Boolean> listener) {
		wizardsRange.removeListener(listener);
	}



	//The board
    @JsonProperty("board")
	public IBoardElement[] getBoard() {
		return board.toArray(new IBoardElement[0]);
	}

	@JsonIgnore
	public void addBoardListener(ListChangeListener<IBoardElement> listener) {
		board.addListener(listener);
	}

	@JsonIgnore
	public void removeBoardListener(ListChangeListener<IBoardElement> listener) {
		board.removeListener(listener);
	}
	

	public void setBoard(IBoardElement[] board)
	{
		Preconditions.checkArgument(board != null, "board was null but expected not null");
		Preconditions.checkArgument(board.length == gameConstant.getBoardLenght(), 
				"board lenght was %s but expected %s", board.length, gameConstant.getBoardLenght());
		
		this.board.clear();
		this.board.addAll(board);
		
		nbMonstersAndCorpses = 0;
		nbWizards = 0;
		for(IBoardElement elem : board)
		{
			nbMonstersAndCorpses += elem instanceof Monster || elem instanceof Corpse ? 1 : 0;
			nbWizards += elem instanceof Wizard ? 1 : 0;
		}
		
		setFirstWizardAsCurrentCharacter();
		refreshCurrentCharacterRange();
		refreshWizardsRange();
	}

	@JsonIgnore
	public int nbBoardElements()
	{
		return nbWizards + nbMonstersAndCorpses;
	}
	
	
	
	//The movements
	@JsonIgnore
	public int elementaryMove(Character character, int delta) //return statement : the actual delta you have done
	{
		int characterIdx = getBoardElementIdx(character);
		
		int direction = (int)Math.signum(delta);
		
		//Tant que la position finale (characterIdx+delta) n'est pas dans le board, réduis le déplacement de 1
		//Par exemple, si characterIdx = 1 et delta = -2, position finale = -1 : en dehors du tableau
		//delta -= -1, delta += 1, delta = -1, position finale = 0 : OK
		while(!indexInBoardBounds(characterIdx + delta)) { delta -= direction; }
		
		if(delta == 0) { return 0; }
		
		
		
		int finalPosition = characterIdx + delta;
		//Si un wizard marche sur un corps...
		if(board.get(characterIdx) instanceof Wizard && board.get(finalPosition) instanceof Corpse)
		{
			board.set(finalPosition, null); //...il le détruit
			nbMonstersAndCorpses--; //et réduit le compteur
		}
		IBoardElement temporaryElement = board.get(finalPosition); //Si la place est occupée, on enregistre l'occupant
		board.set(finalPosition, board.get(characterIdx)); //On place le character sur la case ou il veut se déplacer
		board.set(characterIdx, null); //On enlève le character de son ancienne position
		
		//On décale l'occupant autant de fois que possible (si lorsqu'on décale l'occupant il y en a un autre à sa position décalée,
		//Il faut aussi décaler l'autre occupant)
		int i = -direction;
		while(board.get(finalPosition+i) != null && temporaryElement != null)
		{
			IBoardElement swapElement = board.get(finalPosition+i);
			board.set(finalPosition+i, temporaryElement);
			temporaryElement = swapElement;
			
			i -= direction;
		}
		
		//On place le dernier occupant décalé sur une case vide
		if(board.get(finalPosition+i) == null) { board.set(finalPosition+i, temporaryElement); }
		
		return delta;
	}

	@JsonIgnore
	public void rightWalk(Character character)
	{
		int actualDelta = elementaryMove(character, 1);
		actualDelta = Math.abs(actualDelta);
		character.loseMove(actualDelta);
		if(actualDelta > 0) { refreshRange(character); }
	}

	@JsonIgnore
	public void leftWalk(Character character)
	{
		int actualDelta = elementaryMove(character, -1);
		actualDelta = Math.abs(actualDelta);
		character.loseMove(actualDelta);
		if(actualDelta > 0) { refreshRange(character); }
	}

	@JsonIgnore
	public void rightDash(Character character)
	{
		int actualDelta = elementaryMove(character, character.getDash());
		actualDelta = Math.abs(actualDelta);
		character.loseDash(actualDelta);
		if(actualDelta > 0)
		{
			character.setHasDashed(true);
			refreshRange(character);
		}
	}

	@JsonIgnore
	public void leftDash(Character character)
	{
		int actualDelta = elementaryMove(character, -character.getDash());
		actualDelta = Math.abs(actualDelta);
		character.loseDash(actualDelta);
		if(actualDelta > 0)
		{
			character.setHasDashed(true);
			refreshRange(character);
		}
	}

	@JsonIgnore
	public void push(Character referenceCharacter, Character[] characters, int delta)
	{
		Preconditions.checkArgument(characters != null, "characters was null but expected not null");
		Preconditions.checkArgument(delta > 0, "delta was %s but expected strictly positive");
		
		List<Character> charactersList = Arrays.asList(characters);
		Preconditions.checkArgument(!charactersList.contains(referenceCharacter), "you can't push yourself");
		int referenceIdx = getBoardElementIdx(referenceCharacter);
		
		boolean currentCharacterDetected = false;
		boolean wizardDetected = false;
		
		//Vers la gauche
		for(int i = 0; i < referenceIdx; i++)
		{
			if(board.get(i) instanceof Character && charactersList.contains((Character) board.get(i)))
			{
				Character c = (Character) board.get(i);
				
				elementaryMove(c, -delta);

				currentCharacterDetected = currentCharacterDetected || c == getCurrentCharacter();
				wizardDetected = wizardDetected || c instanceof Wizard;
			}
		}
		
		//Vers la droite
		for(int i = board.size()-1; i > referenceIdx; i--)
		{
			if(board.get(i) instanceof Character && charactersList.contains((Character) board.get(i)))
			{
				Character c = (Character) board.get(i);
				
				elementaryMove(c, delta);

				currentCharacterDetected = currentCharacterDetected || c == getCurrentCharacter();
				wizardDetected = wizardDetected || c instanceof Wizard;
			}
		}

		if(currentCharacterDetected) {refreshCurrentCharacterRange();}
		if(wizardDetected) {refreshWizardsRange();}
	}

	@JsonIgnore
	public void pull(Character referenceCharacter, Character[] characters, int delta)
	{
		Preconditions.checkArgument(characters != null, "characters was null but expected not null");
		Preconditions.checkArgument(delta > 0, "delta was %s but expected strictly positive");
		
		List<Character> charactersList = Arrays.asList(characters);
		Preconditions.checkArgument(!charactersList.contains(referenceCharacter), "you can't pull yourself");
		int referenceIdx = getBoardElementIdx(referenceCharacter);
		
		boolean currentCharacterDetected = false;
		boolean wizardDetected = false;
		
		//Vers la gauche
		for(int i = referenceIdx-1; i >= 0; i--)
		{
			if(board.get(i) instanceof Character && charactersList.contains((Character) board.get(i)))
			{
				Character c = (Character) board.get(i);
				
				elementaryMove(c, Math.min(delta, referenceIdx-i-1));

				currentCharacterDetected = currentCharacterDetected || c == getCurrentCharacter();
				wizardDetected = wizardDetected || c instanceof Wizard;
			}
		}
		
		//Vers la droite
		for(int i = referenceIdx+1; i < board.size(); i++)
		{
			if(board.get(i) instanceof Character && charactersList.contains((Character) board.get(i)))
			{
				Character c = (Character) board.get(i);
				
				elementaryMove(c, -Math.min(delta, i-referenceIdx-1));
					
				currentCharacterDetected = currentCharacterDetected || c == getCurrentCharacter();
				wizardDetected = wizardDetected || c instanceof Wizard;
			}
		}

		if(currentCharacterDetected) {refreshCurrentCharacterRange();}
		if(wizardDetected) {refreshWizardsRange();}
	}
	

	
	//The turns
	public boolean isWizardsTurn() {
		return wizardsTurn;
	}

	@JsonIgnore
	public void beginWizardsTurn()
	{
		Preconditions.checkState(!isWizardsTurn(), "in order to begin wizard's turn, it has to not be wizard's turn");
		
		wizardsTurn = true;
		
		setFirstWizardAsCurrentCharacter();
		
		for(Wizard w : getWizards())
		{
			//Draw 1 card at the beginning of each turn
			w.getZoneGroup().transfer(ZoneType.DECK, ZonePick.TOP, ZoneType.HAND, ZonePick.DEFAULT, 1);
		}
	}

	@JsonIgnore
	public void endWizardsTurn()
	{
		Preconditions.checkState(isWizardsTurn(), "in order to end wizard's turn, it has to be wizard's turn");
		
		for(Wizard w : getWizards())
		{
			w.resetFreeze();
			w.resetMana();
			w.resetMove();
			w.resetRange();
			w.getZoneGroup().unvoid();
			w.getZoneGroup().unbanish();
			w.clearWords();
		}
		
		wizardsTurn = false;
		
		nextMonster();
	}

	@JsonIgnore
	public boolean currentCharacterInWizardsRange()
	{
		return getCurrentCharacter() != null && wizardsRange.get(getBoardElementIdx(getCurrentCharacter())); 
	}

	@JsonIgnore
	public void playMonstersTurnPart1()
	{
		//TODO
	}

	@JsonIgnore
	public void playMonstersTurnPart2()
	{
		//TODO
	}

	@JsonIgnore
	public void nextMonster()
	{
		Preconditions.checkState(!isWizardsTurn(), "in order to fetch next monster's index, it has to be monster's turn");

		boolean monsterFounded = false;
		int i = 0;
		while(i < board.size() && !monsterFounded)
		{
			if(board.get(i) instanceof Monster)
			{
				Monster m = (Monster) board.get(i);
				
				if(!m.hasPlayed())
				{
					monsterFounded = true;
					setCurrentCharacter((Character) board.get(i));
					m.setPlayed(true);
				}
			}
			
			i++;
		}
		
		if(!monsterFounded)
		{
			endMonstersTurn();
		}
	}
	
	private void endMonstersTurn()
	{
		setCurrentCharacter(null);
		
		for(int i = 0; i < board.size(); i++)
		{
			if(board.get(i) instanceof Monster)
			{
				Monster m = (Monster) board.get(i);
				
				m.resetFreeze();
				m.resetMove();
				m.resetRange();
				m.setPlayed(false);
				m.clearWords();
			}
			
			if(board.get(i) instanceof Corpse)
			{
				Corpse c = (Corpse) board.get(i);
				
				c.incrCounterToReborn();
				
				if(c.counterReachedReborn())
				{
					if(c.isWillReborn())
					{
						board.set(i, c.getMonster());
					}
					else
					{
						board.set(i, null);
						nbMonstersAndCorpses--;
					}
				}
			}
		}
	}

	@JsonIgnore
	public boolean monstersTurnEnded()
	{
		return getCurrentCharacter() == null && !isWizardsTurn();
	}



	//Cast zone
	public CastZone getCastZone() {
		return castZone;
	}

	
	
	//Wizard's spawn
	private Wizard[] getWizards()
	{
		List<Wizard> wizardsList = new LinkedList<>();
		
		for(IBoardElement elem : board)
		{
			if(elem instanceof Wizard)
			{
				wizardsList.add((Wizard) elem);
			}
		}
		
		return wizardsList.toArray(new Wizard[0]);
	}
	
	private void spawnWizards(Wizard[] wizards)
	{
		for(int i = 0; i < wizards.length; i++)
		{
			board.set(i, wizards[i]);
			
			wizards[i].addAliveListener((c, isAlive) -> { if(!isAlive) { clearBoard(c); } });
			
			wizards[i].addRangeListener(new IRangeListener()
					{
						@Override
						public void onChange(Character c, int previous, int actual) { refreshRange(c); }

						@Override
						public void onGain(Character c, int previous, int actual) {
							//Don't need to be implemented
						}

						@Override
						public void onLoss(Character c, int previous, int actual) {
							//Don't need to be implemented
						}
					});
		}
		refreshWizardsRange();
		if(getCurrentCharacter() instanceof Wizard) { refreshCurrentCharacterRange(); }
	}
	
	private void moveWizardsToTheirSpawns()
	{
		List<Wizard> lw = new LinkedList<>();
		
		for(int i = 0; i < board.size(); i++)
		{
			if(board.get(i) instanceof Wizard)
			{
				lw.add((Wizard) board.get(i));
				board.set(i, null);
			}
		}
		
		spawnWizards(lw.toArray(new Wizard[0]));
	}
	
	private void resetWizardsForNextLevel(WizardFactory[] wizardFactory, Card[] cards)
	//wizardFactory : all the wizardFactories from the JSON file
	//cards : all the cards from the JSON file
	{
		Map<String, WizardFactory> wizardFactoryMap = new HashMap<>();
		for(WizardFactory wf : wizardFactory) { wizardFactoryMap.put(wf.getName(), wf); }
		
		
		for(int i = 0; i < board.size(); i++)
		{
			if(board.get(i) instanceof Wizard)
			{
				Wizard w = (Wizard) board.get(i);
				
				//Reset the cards of the wizards
				if(!wizardFactoryMap.containsKey(w.getName())) { throw new IllegalArgumentException("One (or more) wizardFactory is missing from wizardFactory"); }
				w.resetCards(wizardFactoryMap.get(w.getName()), cards);
				
				//If a wizard is transformed : 
				if(w.isTransformed()) { w.untransform(); } //untransform it
				else { w.resetHealth(); } //If not : restore it to full health
				
				//Reset the armor of wizards
				w.resetArmor();
			}
		}
		
	}
	
	
	
	//Monster's spawn
	@JsonProperty("monstersToSpawn")
	public MonsterFactory[] getMonstersToSpawn()
	{
		return monstersToSpawn.toArray(new MonsterFactory[0]);
	}
	
	private void fillMonstersToSpawn(Level level, Horde[] hordes, MonsterFactory[] monsterFactory)
	//hordes : all hordes from the JSON file
	//monsterFactory : all monsterFactories from the JSON file
	{
		Horde[] myHordeArray = Arrays.asList(
				MapConverter.getObjectsFromMapNamesFrequencies(level.getMapHordesFrequencies(), hordes)
				).toArray(new Horde[0]); //Convert INamedObject[] to Horde[]
		
		float[] myHordeProbabilities = Proba.convertFrequencyToProbability(
				MapConverter.getFrequenciesFromMapNamesFrequencies(level.getMapHordesFrequencies(), myHordeArray));
		
		int cost = 0;
		
		while(cost < gameConstant.getLevelCost())
		{
			Horde horde = myHordeArray[Proba.getRandomIndexFrom(myHordeProbabilities)];
			
			cost += horde.getCost();
			
			MonsterFactory[] myMonsterFactoryArray = Arrays.asList(
					MapConverter.getObjectsFromMapNamesQuantities(horde.getMapMonstersQuantity(), monsterFactory)
					).toArray(new MonsterFactory[0]); //Convert INamedObject[] to MonsterFactory[]

			for(MonsterFactory mf : myMonsterFactoryArray)
			{
				monstersToSpawn.add(mf);
			}
		}
	}

	public void setMonsterToSpawn(MonsterFactory[] monsterFactory) //For the tests
	{
		monstersToSpawn.clear();
		for( MonsterFactory mf : monsterFactory) { monstersToSpawn.add(mf); }
	}

	@JsonIgnore
	public void spawnMonster(Monster monster) //In public for the tests
	{
		Preconditions.checkState(nbBoardElements() < board.size(), "No space for an additional monster");
		
		Preconditions.checkArgument(monster != null, "monster was null but expected not null");
		
		int spawnPosition = board.size() - 1;
		
		IBoardElement temporaryElement = board.get(spawnPosition); //Si la place est occupée, on enregistre l'occupant
		board.set(spawnPosition, monster); //On place le monstre sur la case ou il doit spawner
		
		//On décale l'occupant autant de fois que possible (si lorsqu'on décale l'occupant il y en a un autre à sa position décalée,
		//Il faut aussi décaler l'autre occupant)
		int i = -1;
		while(board.get(spawnPosition+i) != null && temporaryElement != null)
		{
			IBoardElement swapElement = board.get(spawnPosition+i);
			board.set(spawnPosition+i, temporaryElement);
			temporaryElement = swapElement;
			
			i--;
		}
		
		//On place le dernier occupant décalé sur une case vide
		if(board.get(spawnPosition+i) == null) { board.set(spawnPosition+i, temporaryElement); }
		
		
		//On incrémente le nombre de monstres ou de cadavres sur le board
		nbMonstersAndCorpses++;
	}

	@JsonIgnore
	public void nextMonsterWave(Incantation[] incantations)
	//incantations : all incantations from the JSON file
	{
		int nbMonstersToSpawnThisTurn = Proba.nextInt(gameConstant.getNbMonstersToSpawnEachTurnMin(),
														gameConstant.getNbMonstersToSpawnEachTurnMax());
		
		int i = 0;
		//Faire spawn un monstre si :
		while(  !monstersToSpawn.isEmpty() && //La file d'attente de monstre n'est pas vide ET
				(i < nbMonstersToSpawnThisTurn || nbMonstersAndCorpses < gameConstant.getNbMonstersMin()) && //Qu'il reste des monstres à faire spawn OU que le nb min de monstres n'est pas atteind
				nbMonstersAndCorpses < gameConstant.getNbMonstersMax() ) //ET que le nb max de monstres n'est pas dépassé
		{
			//New monster
			Monster monster = new Monster(monstersToSpawn.poll(), incantations);
			monster.addAliveListener((c, isAlive) -> { if(!isAlive) { clearBoard(c); } });
			monster.addRangeListener(new IRangeListener()
			{
				@Override
				public void onChange(Character c, int previous, int actual) { refreshRange(c); }

				@Override
				public void onGain(Character c, int previous, int actual) {
					//Don't need to be implemented
				}

				@Override
				public void onLoss(Character c, int previous, int actual) {
					//Don't need to be implemented
				}
			});
			
			spawnMonster(monster);
			
			i++;
		}
	}
	


	//Level Difficulty
	public int getLevelDifficulty() {
		return levelDifficulty;
	}

	@JsonIgnore
	public boolean levelFinished()
	{
		return nbMonstersAndCorpses == 0 && monstersToSpawn.isEmpty();
	}

	@JsonIgnore
	public void nextLevel(Level level, Horde[] hordes, MonsterFactory[] monsterFactory, WizardFactory[] wizardFactory, Card[] cards)
	//hordes : all hordes from the JSON file
	//monsterFactory : all monsterFactories from the JSON file
	//wizardFactory : all the wizardFactories from the JSON file
	//cards : all the cards from the JSON file
	{
		levelDifficulty++;
		
		if(levelDifficulty > gameConstant.getLevelMaxDifficulty()) { 
			return;
		}
		
		Preconditions.checkArgument(level.getDifficulty() == levelDifficulty, "difficulty of the input level was %s but expected %s",
				level.getDifficulty(), levelDifficulty);

		//Fill monstersToSpawn
		fillMonstersToSpawn(level, hordes, monsterFactory);
		
		//Move wizards to their spawn and reset them
		moveWizardsToTheirSpawns();
		resetWizardsForNextLevel(wizardFactory, cards);
	}

	@JsonIgnore
	public void nextEmptyLevel() //For the tests
	{
		levelDifficulty++;
	}



	//Triggered methods
	@JsonIgnore
	public void clearBoard(Character character)
	{
		int idx = getBoardElementIdx(character);
		
		if(character instanceof Monster)
		{
			board.set(idx, new Corpse((Monster) character));
				
			if(character == getCurrentCharacter()) { nextMonster(); }
		}
			
		if(character instanceof Wizard)
		{
			board.set(idx, null);
			
			if(character == getCurrentCharacter()) { setFirstWizardAsCurrentCharacter(); }
			
			refreshWizardsRange();
			
			nbWizards--;
		}
				
	}

	private void refreshRange(Character character)
	{
		if(character instanceof Wizard) { refreshWizardsRange(); }
		if(character == getCurrentCharacter()) { refreshCurrentCharacterRange(); }
	}
	
	
	
	//Utility fonctions
	private boolean indexInBoardBounds(int idx)
	{
		return idx >= 0 && idx < board.size();
	}
	
	private boolean indexCorrespondToCharacter(int idx)
	{
		return indexInBoardBounds(idx) && board.get(idx) instanceof Character;
	}
	
	private int getBoardElementIdx(IBoardElement boardElement)
	{
		Preconditions.checkArgument(boardElement != null, "boardElement was null but expected not null");
		
		int i = 0;
		while(i < board.size() && board.get(i) != boardElement)
		{
			i++;
		}
		
		Preconditions.checkArgument(i < board.size(), "boardElement was not found in the board");
		
		return i;
	}
	
}
