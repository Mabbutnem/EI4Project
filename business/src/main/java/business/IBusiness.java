package business;

import java.io.IOException;

import boardelement.IBoardElement;
import boardelement.WizardFactory;
import boardelement.Character;
import game.Game;
import zone.CastZone;

public interface IBusiness
{
	//Gestion des games
	public Game[] getGames() throws IOException;
	public boolean gameExists(String name) throws IOException;
	public WizardFactory[] getRandomWizards(int number, WizardFactory[] alreadyChoosens) throws IOException;
	public void newGame(Game game) throws IOException;
	public Game loadGame(String name) throws IOException;
	public void saveGame(Game game) throws IOException;
	public void deleteGame(Game game) throws IOException;
	
	/*
	 * Ordre d'appel en jeu:
	 */
	public boolean isFinished();
	public boolean isVictory();
	
	public boolean levelFinished();
	public void nextLevel() throws IOException;
	
	public void beginWizardsTurn();
	public void rightDash();
	public void leftDash();
	public void endWizardsTurn();
	
	public void nextMonsterWave() throws IOException;

	public boolean monstersTurnEnded();
	public void nextMonster();
	public boolean currentCharacterInWizardsRange();
	public void playMonstersTurnPart1();
	public void playMonstersTurnPart2();
	/*
	 * 
	 */
	
	//D'autres getters utiles:
	public Game getGame();
	public Character getCurrentCharacter();
	public boolean[] getCurrentCharacterRange();
	public boolean[] getWizardsRange();
	public IBoardElement[] getBoard();
	public CastZone getCastZone();
}
