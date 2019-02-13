package game;

import java.util.List;

import com.google.common.base.Preconditions;

import boardelement.Character;
import boardelement.IBoardElement;
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
	//private List<Wizard> wizards;
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
		
		for(int i = 0; i < board.length; i++)
		{
			if(board[i] instanceof Wizard)
			{
				int range = ((Wizard) board[i]).getRange();
				
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



	public CastZone getCastZone() {
		return castZone;
	}



	public int getLevelDifficulty() {
		return levelDifficulty;
	}



	@Override
	public void clearBoard(IBoardElement boardElement) {
		// TODO Auto-generated method stub
	}



	@Override
	public void refreshRange(Character character) {
		// TODO Auto-generated method stub
		
	}
	
	
}
