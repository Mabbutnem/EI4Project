package game;

import java.util.List;

import com.google.common.base.Preconditions;

import boardelement.Character;
import boardelement.Corpse;
import boardelement.IBoardElement;
import boardelement.Monster;
import boardelement.MonsterFactory;
import boardelement.Wizard;
import constant.GameConstant;
import listener.IGameListener;
import zone.CastZone;

public class Game implements IGameListener
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
	private List<MonsterFactory> monsterToSpawn;
	private int levelDifficulty;
	
	
	
	public Game(Wizard[] wizards)
	{
		//TODO
		Preconditions.checkState(gameConstant != null, "gameConstant was not initialised (in static)");
		
		Preconditions.checkArgument(wizards.length == gameConstant.getNbWizard(), "wizards lenght was %s but expected %s", wizards.length, gameConstant.getNbWizard());
		
		
	}

	
	
	public static GameConstant getGameConstant() { return gameConstant; }
	public static void setGameConstant(GameConstant gameConstant) { Game.gameConstant = gameConstant; }

	
	
	//finish and win condition
	public boolean isFinished()
	{
		return nbWizards == 0 || levelDifficulty == gameConstant.getLevelMaxDifficulty();
	}
	
	public boolean isVictory()
	{
		return nbWizards > 0 && levelDifficulty == gameConstant.getLevelMaxDifficulty();
	}
	


	//Current character
	public Character getCurrentCharacter() {
		return currentCharacter;
	}
	
	public void setCurrentCharacter(Character character) {
		this.currentCharacter = character;
	}

	public void setCurrentCharacter(int currentCharacterIdx)
	{
		Preconditions.checkArgument(indexCorrespondToCharacter(currentCharacterIdx), "currentCharacterIdx don't correspond to a character");
		
		this.currentCharacter = (Character) board[currentCharacterIdx];
	}
	
	
	
	//The range array of the current character
	private void resetCurrentCharacterRange()
	{
		for(int i = 0; i < currentCharacterRange.length; i++)
		{
			currentCharacterRange[i] = false;
		}
	}
	
	public void refreshCurrentCharacterRange()
	{
		resetCurrentCharacterRange();
		
		int range = getCurrentCharacter().getRange();
		int currentCharacterIdx = this.getBoardElementIdx(getCurrentCharacter());
		
		for(int r = -range; r < range+1; r++)
		{
			if(indexInBoardBounds(currentCharacterIdx+r)) { currentCharacterRange[currentCharacterIdx+r] = true; }
		}
	}
	
	public boolean[] getCurrentCharacterRange() {
		return currentCharacterRange;
	}
	
	
	
	//The range array of all wizards
	private void resetWizardsRange()
	{
		for(int i = 0; i < wizardsRange.length; i++)
		{
			wizardsRange[i] = false;
		}
	}
	
	public void refreshWizardsRange()
	{
		resetWizardsRange();
		
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
		
		//TODO
		//refreshCurrentCharacterRange();
		//refreshWizardsRange();
	}
	
	
	
	//The movements
	public int elementaryMove(Character character, int delta) //return statement : the actual delta you have done
	{
		int characterIdx = getBoardElementIdx(character);
		
		Preconditions.checkArgument(indexCorrespondToCharacter(characterIdx), "characterIdx don't correspond to a character");
		
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
	

	
	//The turns
	public boolean isWizardsTurn() {
		return wizardsTurn;
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
			}
		}
		
		wizardsTurn = !wizardsTurn;
		
		nextMonster();
	}
	
	public void playMonstersTurnPart1()
	{
		
	}
	
	public void playMonstersTurnPart2()
	{
		
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
		}
		
		wizardsTurn = !wizardsTurn;
	}



	//Cast zone
	public CastZone getCastZone() {
		return castZone;
	}



	//Level Difficulty
	public int getLevelDifficulty() {
		return levelDifficulty;
	}
	
	public boolean levelFinished()
	{
		return nbMonstersAndCorpses == 0;
	}
	
	public void nextLevel(Level level)
	{
		//TODO
	}



	//IGameListener Methods
	@Override
	public void clearBoard(IBoardElement boardElement)
	{
		int idx = getBoardElementIdx(boardElement);
		
		if(boardElement instanceof Corpse)
		{
			board[idx] = null;
		}
		
		if(boardElement instanceof Character)
		{
			if(boardElement instanceof Monster)
			{
				board[idx] = new Corpse((Monster) boardElement);
			}
			
			if(boardElement instanceof Wizard)
			{
				board[idx] = null;
			}
			
			refreshRange((Character) boardElement);
		}
		
	}

	@Override
	public void refreshRange(Character character)
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
		
		Preconditions.checkState(i < board.length, "boardElement was not found in the board");
		
		return i;
	}
	
}
