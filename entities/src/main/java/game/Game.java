package game;

import java.util.List;

import boardelement.Character;
import boardelement.IBoardElement;
import boardelement.MonsterFactory;
import constant.GameConstant;
import listener.IGameListener;
import zone.CastZone;

public class Game implements IGameListener
{
	private static GameConstant gameConstant;
	
	private IBoardElement[] board;
	private CastZone castZone;
	private List<MonsterFactory> monsterToSpawn;
	private int levelDifficulty;
	
	
	
	public Game()
	{
		//TODO
	}

	
	
	public static GameConstant getGameConstant() { return gameConstant; }
	public static void setGameConstant(GameConstant gameConstant) { Game.gameConstant = gameConstant; }



	@Override
	public void clearBoard(IBoardElement boardElement) {
		// TODO Auto-generated method stub
	}
	
	
}
