package game;

import java.util.LinkedList;
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



	public Character getCurrentCharacter() {
		return (Character) board[currentCharacterIdx];
	}

	public void setCurrentCharacterIdx(int currentCharacterIdx)
	{
		Preconditions.checkArgument(indexInBoardBounds(currentCharacterIdx), "currentCharacterIdx was not in board bounds");
		Preconditions.checkArgument(board[currentCharacterIdx] instanceof Character, "selected idx was not instanceof Character");
		
		this.currentCharacterIdx = currentCharacterIdx;
	}
	
	private boolean indexInBoardBounds(int idx)
	{
		return idx >= 0 && idx < board.length;
	}
	
	
	
	public void resetCurrentCharacterRange()
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
		
		for(int r = -range; r < range+1; r++)
		{
			if(indexInBoardBounds(currentCharacterIdx+r)) { currentCharacterRange[currentCharacterIdx+r] = true; }
		}
	}
	
	public boolean[] getCurrentCharacterRange() {
		return currentCharacterRange;
	}
	
	
	
	public void resetWizardsRange()
	{
		for(int i = 0; i < wizardsRange.length; i++)
		{
			wizardsRange[i] = false;
		}
	}
	
	public void refreshWizardsRange()
	{
		resetWizardsRange();
		
		int i = 0;
		int nbWizards = 0;
		while(i < board.length && nbWizards < gameConstant.getNbWizard())
		{
			if(board[i] instanceof Wizard)
			{
				int range = ((Wizard) board[i]).getRange();
				
				for(int r = -range; r < range+1; r++)
				{
					if(indexInBoardBounds(i+r)) { wizardsRange[i+r] = true; }
				}
				
				nbWizards++;
			}
			
			i++;
		}
	}
	
	public boolean[] getWizardsRange() {
		return wizardsRange;
	}



	public IBoardElement[] getBoard() {
		return board;
	}

	public void setBoard(IBoardElement[] board)
	{
		Preconditions.checkArgument(board != null, "board was null but expected not null");
		Preconditions.checkArgument(board.length == gameConstant.getBoardLenght(), 
				"board lenght was %s but expected %s", board.length, gameConstant.getBoardLenght());
		
		this.board = board;
	}



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
	}



	public CastZone getCastZone() {
		return castZone;
	}



	public int getLevelDifficulty() {
		return levelDifficulty;
	}



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
