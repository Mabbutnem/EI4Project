package dao;

import java.io.IOException;

import game.Game;

public interface IDao extends IDataGameReaderDao
{
	public Game[] getGames() throws IOException;
	public boolean gameExists(String name) throws IOException;
	
	public void newGame(Game game) throws IOException;
	public Game loadGame(String name) throws IOException;
	public void saveGame(Game game) throws IOException;
	public void deleteGame(Game game) throws IOException;
}
