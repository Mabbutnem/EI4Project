package game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.base.Preconditions;

import boardelement.Character;
import boardelement.Corpse;
import boardelement.IBoardElement;
import boardelement.Monster;
import boardelement.MonsterFactory;
import boardelement.Wizard;
import boardelement.WizardFactory;
import characterlistener.CharacterIntValueEvent;
import characterlistener.IRangeListener;
import constant.GameConstant;
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
	private static GameConstant gameConstant;

	private int nbWizards;
	private int nbMonstersAndCorpses;
	private Character currentCharacter;
	private IBoardElement[] board;
	private boolean[] wizardsRange;
	private boolean[] currentCharacterRange;
	private boolean wizardsTurn;
	private CastZone castZone;
	private Queue<MonsterFactory> monstersToSpawn;
	private int levelDifficulty;
	
	
	
	public Game(Wizard[] wizards)
	{
		Preconditions.checkState(gameConstant != null, "gameConstant was not initialised (in static)");
		
		Preconditions.checkArgument(wizards.length == gameConstant.getNbWizard(), "wizards lenght was %s but expected %s",
				wizards.length, gameConstant.getNbWizard());
		
		
		nbWizards = gameConstant.getNbWizard();
		nbMonstersAndCorpses = 0;
		currentCharacter = null;
		board = new IBoardElement[gameConstant.getBoardLenght()];
		wizardsRange = new boolean[gameConstant.getBoardLenght()];
		currentCharacterRange = new boolean[gameConstant.getBoardLenght()];
		spawnWizards(wizards);
		wizardsTurn = false;
		castZone = new CastZone();
		monstersToSpawn = new LinkedBlockingQueue<>();
		levelDifficulty = 0;
		
	}

	
	
	public static GameConstant getGameConstant() { return gameConstant; }
	public static void setGameConstant(GameConstant gameConstant) { Game.gameConstant = gameConstant; }

	
	
	//finish and win condition
	public boolean isFinished()
	{
		return nbWizards == 0 || levelDifficulty > gameConstant.getLevelMaxDifficulty();
	}
	
	public boolean isVictory()
	{
		return nbWizards > 0 && levelDifficulty > gameConstant.getLevelMaxDifficulty();
	}
	


	//Current character
	public Character getCurrentCharacter() {
		return currentCharacter;
	}
	
	public void setCurrentCharacter(Character character) {
		this.currentCharacter = character;
		refreshCurrentCharacterRange();
	}

	public void setCurrentCharacter(int currentCharacterIdx)
	{
		Preconditions.checkArgument(indexCorrespondToCharacter(currentCharacterIdx), "currentCharacterIdx don't correspond to a character");
		
		setCurrentCharacter((Character) board[currentCharacterIdx]);
	}
	
	public void setFirstWizardAsCurrentCharacter()
	{
		int i = 0;
		while(i < board.length && !(board[i] instanceof Wizard))
		{
			i++;
		}
		
		if(board[i] instanceof Wizard) { setCurrentCharacter((Character) board[i]); }
		else { setCurrentCharacter(null); }
	}
	
	
	
	//Targets for current character
	private List<Character> getAllVisibleTargetForCurrentCharacter()
	{
		List<Character> lc = new LinkedList<>();
		
		for(int i = 0; i < board.length; i++)
		{
			if(board[i] instanceof Character && currentCharacterRange[i])
			{
				lc.add((Character) board[i]);
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
	
	public Character getRandomAvailableTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		Character[] characters = getAllAvailableTargetForCurrentCharacter(constraints);
		
		Preconditions.checkState(characters.length > 0, "No available target for current Character,"
				+ " please verify using hasValidTargetForCurrentCharacter method before using method");
		
		return characters[Proba.nextInt(characters.length)];
	}
	
	public Character[] getAllAvailableTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		return filterTargetByConstraintForCurrentCharacter(getAllVisibleTargetForCurrentCharacter(), constraints);
	}
	
	public boolean isValidTargetForCurrentCharacter(Character character, TargetConstraint[] constraints)
	{
		return Arrays.asList(getAllAvailableTargetForCurrentCharacter(constraints)).contains(character);
	}
	
	public boolean hasValidTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		return getAllAvailableTargetForCurrentCharacter(constraints).length > 0;
	}
	
	//Targets for the AI of monsters
	public Character[] getAllPossibleTargetForCurrentCharacter(TargetConstraint[] constraints)
	{
		int currentCharacterIdx = getBoardElementIdx(getCurrentCharacter());
		
		List<Character> lc = new LinkedList<>();
		
		int possibleRange = getCurrentCharacter().getRange() + getCurrentCharacter().getMove();
		
		for(int r = -possibleRange; r < possibleRange+1; r++)
		{
			int i = currentCharacterIdx+r;
			
			if(indexInBoardBounds(i) && board[i] instanceof Character)
			{
				lc.add((Character) board[i]);
			}
		}
		
		return filterTargetByConstraintForCurrentCharacter(lc, constraints);
	}
	
	
	
	//The range array of the current character
	private void refreshCurrentCharacterRange()
	{
		for(int i = 0; i < currentCharacterRange.length; i++) { currentCharacterRange[i] = false; }
		
		if(getCurrentCharacter() != null)
		{
			int range = getCurrentCharacter().getRange();
			int currentCharacterIdx = getBoardElementIdx(getCurrentCharacter());
		
			for(int r = -range; r < range+1; r++)
			{
				if(indexInBoardBounds(currentCharacterIdx+r)) { currentCharacterRange[currentCharacterIdx+r] = true; }
			}
		}
	}
	
	public boolean[] getCurrentCharacterRange() {
		return currentCharacterRange;
	}
	
	
	
	//The range array of all wizards
	private void refreshWizardsRange()
	{
		for(int i = 0; i < wizardsRange.length; i++) { wizardsRange[i] = false; }
		
		for(int i = 0; i < board.length; i++)
		{
			if(board[i] instanceof Wizard)
			{
				Wizard w = (Wizard) board[i];
				
				int range = w.getRange();
				for(int r = -range; r < range+1; r++)
				{
					if(indexInBoardBounds(i+r)) { wizardsRange[i+r] = true; }
				}
			}
		}
		
	}
	
	public boolean[] getWizardsRange() {
		return wizardsRange;
	}



	//The board
	public IBoardElement[] getBoard() {
		return board;
	}

	public void setBoard(IBoardElement[] board)
	{
		Preconditions.checkArgument(board != null, "board was null but expected not null");
		Preconditions.checkArgument(board.length == gameConstant.getBoardLenght(), 
				"board lenght was %s but expected %s", board.length, gameConstant.getBoardLenght());
		
		this.board = board;
		
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
	
	public int nbBoardElements()
	{
		return nbWizards + nbMonstersAndCorpses;
	}
	
	
	
	//The movements
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
		if(board[characterIdx] instanceof Wizard && board[finalPosition] instanceof Corpse)
		{
			board[finalPosition] = null; //...il le détruit
			nbMonstersAndCorpses--; //et réduit le compteur
		}
		IBoardElement temporaryElement = board[finalPosition]; //Si la place est occupée, on enregistre l'occupant
		board[finalPosition] = board[characterIdx]; //On place le character sur la case ou il veut se déplacer
		board[characterIdx] = null; //On enlève le character de son ancienne position
		
		//On décale l'occupant autant de fois que possible (si lorsqu'on décale l'occupant il y en a un autre à sa position décalée,
		//Il faut aussi décaler l'autre occupant)
		int i = -direction;
		while(board[finalPosition+i] != null && temporaryElement != null)
		{
			IBoardElement swapElement = board[finalPosition+i];
			board[finalPosition+i] = temporaryElement;
			temporaryElement = swapElement;
			
			i -= direction;
		}
		
		//On place le dernier occupant décalé sur une case vide
		if(board[finalPosition+i] == null) { board[finalPosition+i] = temporaryElement; }
		
		return delta;
	}
	
	public void rightWalk(Character character)
	{
		int actualDelta = elementaryMove(character, 1);
		character.loseMove(Math.abs(actualDelta));
		if(actualDelta > 0) { refreshRange(character); }
	}
	
	public void leftWalk(Character character)
	{
		int actualDelta = elementaryMove(character, -1);
		character.loseMove(Math.abs(actualDelta));
		if(actualDelta > 0) { refreshRange(character); }
	}
	
	public void rightDash(Character character)
	{
		int actualDelta = elementaryMove(character, character.getDash());
		character.loseDash(Math.abs(actualDelta));
		if(actualDelta > 0) { refreshRange(character); }
	}
	
	public void leftDash(Character character)
	{
		int actualDelta = elementaryMove(character, -character.getDash());
		character.loseDash(Math.abs(actualDelta));
		if(actualDelta > 0) { refreshRange(character); }
	}
	
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
			if(board[i] instanceof Character && charactersList.contains((Character) board[i]))
			{
				Character c = (Character) board[i];
				
				elementaryMove(c, -delta);

				currentCharacterDetected = currentCharacterDetected || c == getCurrentCharacter();
				wizardDetected = wizardDetected || c instanceof Wizard;
			}
		}
		
		//Vers la droite
		for(int i = board.length-1; i > referenceIdx; i--)
		{
			if(board[i] instanceof Character && charactersList.contains((Character) board[i]))
			{
				Character c = (Character) board[i];
				
				elementaryMove(c, delta);

				currentCharacterDetected = currentCharacterDetected || c == getCurrentCharacter();
				wizardDetected = wizardDetected || c instanceof Wizard;
			}
		}

		if(currentCharacterDetected) {refreshCurrentCharacterRange();}
		if(wizardDetected) {refreshWizardsRange();}
	}
	
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
			if(board[i] instanceof Character && charactersList.contains((Character) board[i]))
			{
				Character c = (Character) board[i];
				
				elementaryMove(c, Math.min(delta, referenceIdx-i-1));

				currentCharacterDetected = currentCharacterDetected || c == getCurrentCharacter();
				wizardDetected = wizardDetected || c instanceof Wizard;
			}
		}
		
		//Vers la droite
		for(int i = referenceIdx+1; i < board.length; i++)
		{
			if(board[i] instanceof Character && charactersList.contains((Character) board[i]))
			{
				Character c = (Character) board[i];
				
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
	
	public void beginWizardsTurn()
	{
		Preconditions.checkState(!isWizardsTurn(), "in order to begin wizard's turn, it has to not be wizard's turn");
		
		wizardsTurn = true;
		
		setFirstWizardAsCurrentCharacter();
		
		for(IBoardElement elem : board)
		{
			if(elem instanceof Wizard)
			{
				Wizard w = (Wizard) elem;

				//Draw 1 card
				w.getZoneGroup().transfer(ZoneType.DECK, ZonePick.TOP, ZoneType.HAND, ZonePick.DEFAULT, 1);
			}
		}
	}
	
	public void endWizardsTurn()
	{
		Preconditions.checkState(isWizardsTurn(), "in order to end wizard's turn, it has to be wizard's turn");
		
		for(IBoardElement elem : board)
		{
			if(elem instanceof Wizard)
			{
				Wizard w = (Wizard) elem;
				
				w.resetFreeze();
				w.resetMana();
				w.resetMove();
				w.resetRange();
				w.getZoneGroup().unvoid();
				w.getZoneGroup().unbanish();
			}
		}
		
		wizardsTurn = false;
		
		nextMonster();
	}
	
	public boolean currentCharacterInWizardsRange()
	{
		return wizardsRange[getBoardElementIdx(getCurrentCharacter())]; 
	}
	
	public void playMonstersTurnPart1()
	{
		//TODO
	}
	
	public void playMonstersTurnPart2()
	{
		//TODO
	}
	
	public void nextMonster()
	{
		Preconditions.checkState(!isWizardsTurn(), "in order to fetch next monster's index, it has to be monster's turn");

		boolean monsterFounded = false;
		int i = 0;
		while(i < board.length && !monsterFounded)
		{
			if(board[i] instanceof Monster)
			{
				Monster m = (Monster) board[i];
				
				if(!m.hasPlayed())
				{
					monsterFounded = true;
					setCurrentCharacter((Character) board[i]);
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
		
		for(int i = 0; i < board.length; i++)
		{
			if(board[i] instanceof Monster)
			{
				Monster m = (Monster) board[i];
				
				m.resetFreeze();
				m.resetMove();
				m.resetRange();
				m.setPlayed(false);
			}
			
			if(board[i] instanceof Corpse)
			{
				Corpse c = (Corpse) board[i];
				
				c.incrCounterToReborn();
				
				if(c.counterReachedReborn())
				{
					if(c.isWillReborn())
					{
						board[i] = c.getMonster();
					}
					else
					{
						board[i] = null;
						nbMonstersAndCorpses--;
					}
				}
			}
		}
	}
	
	public boolean monstersTurnEnded()
	{
		return getCurrentCharacter() == null && !isWizardsTurn();
	}



	//Cast zone
	public CastZone getCastZone() {
		return castZone;
	}

	
	
	//Wizard's spawn
	private void spawnWizards(Wizard[] wizards)
	{
		for(int i = 0; i < wizards.length; i++)
		{
			board[i] = wizards[i];
			
			wizards[i].addAliveListener(this::clearBoard);
			
			wizards[i].addRangeListener(new IRangeListener()
					{
						@Override
						public void onChange(CharacterIntValueEvent e){
							refreshRange(e.getCharacter());
						}

						@Override
						public void onGain(CharacterIntValueEvent e) {
							//Don't need to be implemented
						}

						@Override
						public void onLoss(CharacterIntValueEvent e) {
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
		
		for(int i = 0; i < board.length; i++)
		{
			if(board[i] instanceof Wizard)
			{
				lw.add((Wizard) board[i]);
				board[i] = null;
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
		
		
		for(int i = 0; i < board.length; i++)
		{
			if(board[i] instanceof Wizard)
			{
				Wizard w = (Wizard) board[i];
				
				//Reset the cards of the wizards
				if(!wizardFactoryMap.containsKey(w.getName())) { throw new IllegalArgumentException("One (or more) wizardFactory is missing from wizardFactory"); }
				w.resetCards(wizardFactoryMap.get(w.getName()), cards);
				
				//If a wizard is transformed : untransform it
				//If a wizard is not transformed : restore it to full health
				if(w.isTransformed()) { w.untransform(); }
				else { w.setHealth(Wizard.getWizardConstant().getMaxHealth()); }
			}
		}
		
	}
	
	
	
	//Monster's spawn
	public MonsterFactory[] getMonstersToSpawn()
	{
		return monstersToSpawn.toArray(new MonsterFactory[0]);
	}
	
	private void fillMonstersToSpawn(Level level, Horde[] hordes, MonsterFactory[] monsterFactory)
	//hordes : all hordes from the JSON file
	//monsterFactory : all monsterFactories from the JSON file
	{
		Horde[] myHordeArray = Arrays.asList(
				MapConverter.getObjectsFromMapNamesFrequencies(level.getMapHordesProbabilities(), hordes)
				).toArray(new Horde[0]); //Convert INamedObject[] to Horde[]
		
		float[] myHordeProbabilities = Proba.convertFrequencyToProbability(
				MapConverter.getFrequenciesFromMapNamesFrequencies(level.getMapHordesProbabilities(), myHordeArray));
		
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
	
	private void spawnMonster(Monster monster)
	{
		Preconditions.checkState(nbBoardElements() < board.length, "No space for an additional monster");
		
		Preconditions.checkArgument(monster != null, "monster was null but expected not null");
		
		int spawnPosition = board.length - 1;
		
		IBoardElement temporaryElement = board[spawnPosition]; //Si la place est occupée, on enregistre l'occupant
		board[spawnPosition] = monster; //On place le monstre sur la case ou il doit spawner
		
		//On décale l'occupant autant de fois que possible (si lorsqu'on décale l'occupant il y en a un autre à sa position décalée,
		//Il faut aussi décaler l'autre occupant)
		int i = -1;
		while(board[spawnPosition+i] != null && temporaryElement != null)
		{
			IBoardElement swapElement = board[spawnPosition+i];
			board[spawnPosition+i] = temporaryElement;
			temporaryElement = swapElement;
			
			i--;
		}
		
		//On place le dernier occupant décalé sur une case vide
		if(board[spawnPosition+i] == null) { board[spawnPosition+i] = temporaryElement; }
		
		
		//On incrémente le nombre de monstres ou de cadavres sur le board
		nbMonstersAndCorpses++;
	}
	
	public void nextMonsterWave(Incantation[] incantations)
	//incantations : all incantations from the JSON file
	{
		int nbMonstersToSpawnThisTurn = Proba.nextInt(gameConstant.getNbMonstersToSpawnEachTurnMin(),
														gameConstant.getNbMonstersToSpawnEachTurnMax());

		float futureDensity = ((float)(nbWizards + nbMonstersAndCorpses + 1))/((float)board.length);
		
		int i = 0;
		while(!monstersToSpawn.isEmpty() && i < nbMonstersToSpawnThisTurn && futureDensity <= gameConstant.getBoardDensityLimit())
																			  //The future density must remain lower than the boardDensityLimit
		{
			//New monster
			Monster monster = new Monster(monstersToSpawn.poll(), incantations);
			monster.addAliveListener(this::clearBoard);
			monster.addRangeListener(new IRangeListener()
			{
				@Override
				public void onChange(CharacterIntValueEvent e){
					refreshRange(e.getCharacter());
				}

				@Override
				public void onGain(CharacterIntValueEvent e) {
					//Don't need to be implemented
				}

				@Override
				public void onLoss(CharacterIntValueEvent e) {
					//Don't need to be implemented
				}
			});
			
			spawnMonster(monster);
			
			futureDensity += 1/((float)board.length); //The future density with one additional monster
			
			i++;
		}
	}
	


	//Level Difficulty
	public int getLevelDifficulty() {
		return levelDifficulty;
	}
	
	public boolean levelFinished()
	{
		return nbMonstersAndCorpses == 0 && monstersToSpawn.isEmpty();
	}
	
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



	private void clearBoard(Character character)
	{
		int idx = getBoardElementIdx(character);
		
		if(character instanceof Monster)
		{
			board[idx] = new Corpse((Monster) character);
				
			refreshRange(character);
				
			if(character == getCurrentCharacter()) { nextMonster(); }
		}
			
		if(character instanceof Wizard)
		{
			board[idx] = null;
			
			refreshRange(character);
			
			if(character == getCurrentCharacter()) { setFirstWizardAsCurrentCharacter(); }
			
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
		return idx >= 0 && idx < board.length;
	}
	
	private boolean indexCorrespondToCharacter(int idx)
	{
		return indexInBoardBounds(idx) && board[idx] instanceof Character;
	}
	
	private int getBoardElementIdx(IBoardElement boardElement)
	{
		Preconditions.checkArgument(boardElement != null, "boardElement was null but expected not null");
		
		int i = 0;
		while(i < board.length && board[i] != boardElement)
		{
			i++;
		}
		
		Preconditions.checkArgument(i < board.length, "boardElement was not found in the board");
		
		return i;
	}
	
}
