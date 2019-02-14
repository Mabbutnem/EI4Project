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
	
	private boolean wizardsTurn;
	private int currentCharacterIdx;
	private boolean[] wizardsRange;
	private boolean[] currentCharacterRange;
	private IBoardElement[] board;
	private List<Wizard> wizards;
	private CastZone castZone;
	private List<MonsterFactory> monsterToSpawn;
	private int levelDifficulty;
	
	
	
	public Game()
	{
		//TODO
		Preconditions.checkState(gameConstant != null, "gameConstant was not initialised (in static)");
	}

	
	
	public static GameConstant getGameConstant() { return gameConstant; }
	public static void setGameConstant(GameConstant gameConstant) { Game.gameConstant = gameConstant; }



	//Current character
	public Character getCurrentCharacter() {
		return (Character) board[currentCharacterIdx];
	}
	
	public void setCurrentCharacter(Character character) {
		int idx = getBoardElementIdx(character);
		
		Preconditions.checkArgument(indexInBoardBounds(idx), "character was not found in the board");
		
		this.currentCharacterIdx = idx;
	}

	public void setCurrentCharacterIdx(int currentCharacterIdx)
	{
		Preconditions.checkArgument(indexInBoardBounds(currentCharacterIdx), "currentCharacterIdx was not in board bounds");
		Preconditions.checkArgument(board[currentCharacterIdx] instanceof Character, "selected idx was not instanceof Character");
		
		this.currentCharacterIdx = currentCharacterIdx;
	}
	
	public boolean currentCharacterMoveLeft() //Return statement : can continue to go left or not
	{
		int finalPosition = currentCharacterIdx-1;
		
		if(!indexInBoardBounds(finalPosition)) { return false; }
		
		Character c = getCurrentCharacter();

		//If the character is a wizard and the final position contain a corpse, the wizard crushes the corpse
		if(board[finalPosition] instanceof Corpse && c instanceof Wizard) 
		{
			board[finalPosition] = null;
		}
		
		board[currentCharacterIdx] = board[finalPosition];
		board[finalPosition] = c;
		
		c.loseMove(1);
		
		//If the character is a wizard, refresh the wizard's range
		if(c instanceof Wizard) { refreshWizardsRange(); }
		
		refreshCurrentCharacterRange();
		
		return true;
	}
	
	public boolean currentCharacterMoveRight() //Return statement : can continue to go right or not
	{
		int finalPosition = currentCharacterIdx+1;
		
		if(!indexInBoardBounds(finalPosition)) { return false; }
		
		Character c = getCurrentCharacter();

		//If the character is a wizard and the final position contain a corpse, the wizard crushes the corpse
		if(board[finalPosition] instanceof Corpse && c instanceof Wizard) 
		{
			board[finalPosition] = null;
		}
		
		board[currentCharacterIdx] = board[finalPosition];
		board[finalPosition] = c;
		
		c.loseMove(1);
		
		//If the character is a wizard, refresh the wizard's range
		if(c instanceof Wizard) { refreshWizardsRange(); }
		
		refreshCurrentCharacterRange();
		
		return true;
	}
	
	
	
	//The range array of the current character
	private void resetCurrentCharacterRange()
	{
		for(int i = 0; i < currentCharacterRange.length; i++)
		{
			currentCharacterRange[i] = false;
		}
	}
	
	private void refreshCurrentCharacterRange()
	{
		resetCurrentCharacterRange();
		
		int range = getCurrentCharacter().getRange();
		
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
	
	private void refreshWizardsRange()
	{
		resetWizardsRange();
		
		for(Wizard w : wizards)
		{
			int idx = getBoardElementIdx(w);
			int range = w.getRange();
			
			for(int r = -range; r < range+1; r++)
			{
				if(indexInBoardBounds(idx+r)) { wizardsRange[idx+r] = true; }
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
		
		refreshCurrentCharacterRange();
		refreshWizardsRange();
	}

	

	//The turns
	public boolean isWizardsTurn() {
		return wizardsTurn;
	}
	
	public void endWizardsTurn()
	{
		Preconditions.checkState(isWizardsTurn(), "in order to end wizard's turn, it has to be wizard's turn");
		
		for(Wizard w : wizards)
		{
			w.resetFreeze();
			w.resetMana();
			w.resetMove();
			w.resetRange();
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
					setCurrentCharacterIdx(i);
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
				wizards.remove(boardElement);
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
	private void resetWizards()
	{
		int i = 0;
		int nbWizards = 0;
		while(i < board.length && nbWizards < gameConstant.getNbWizard())
		{
			if(board[i] instanceof Wizard)
			{
				wizards.add((Wizard) board[i]);
				
				nbWizards++;
			}
			
			i++;
		}
	}
	
	private boolean indexInBoardBounds(int idx)
	{
		return idx >= 0 && idx < board.length;
	}
	
	private int getBoardElementIdx(IBoardElement boardElement)
	{
		Preconditions.checkArgument(boardElement != null, "boardElement was null but expected not null");
		
		int i = 0;
		while(i < board.length && board[i] != boardElement)
		{
			i++;
		}
		
		if(i >= board.length) { return -1; }
		
		return i;
	}
	
}
