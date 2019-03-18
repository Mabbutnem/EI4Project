package business;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import boardelement.IBoardElement;
import boardelement.WizardFactory;
import boardelement.Character;
import dao.IDao;
import game.Game;
import zone.CastZone;

public class Business implements IBusiness
{
	@Autowired
	private IDao dao;
	
	private Game game;
	
	
	
	
	public Business()
	{
		try { dao.getConstant().initAllConstant(); }
		catch (IOException e) {
			throw new IllegalStateException("Constants were not well initialised");
		}
	}

	
	
	
	public Game[] getGames() throws IOException {
		return dao.getGames();
	}

	public boolean gameExists(String name) throws IOException {
		return dao.gameExists(name);
	}

	public WizardFactory[] getRandomWizards(int number, WizardFactory[] alreadyChoosens) throws IOException {
		return dao.getRandomWizards(number, alreadyChoosens);
	}

	public void newGame(Game game) throws IOException {
		dao.newGame(game);
	}

	public Game loadGame(String name) throws IOException {
		return dao.loadGame(name);
	}

	public void saveGame(Game game) throws IOException {
		dao.saveGame(game);
	}

	public void deleteGame(Game game) throws IOException {
		dao.deleteGame(game);
	}

	
	
	
	
	public boolean isFinished() {
		return game.isFinished();
	}

	public boolean isVictory() {
		return game.isVictory();
	}

	public boolean levelFinished() {
		return game.levelFinished();
	}

	public void nextLevel() throws IOException {
		game.nextLevel(dao.getRandomLevel(game.getLevelDifficulty()+1),
				dao.getHordes(), dao.getMonsters(), dao.getWizards(), dao.getCards());
	}

	public void beginWizardsTurn() {
		game.beginWizardsTurn();
	}

	public void rightDash() {
		game.rightDash(game.getCurrentCharacter());
	}

	public void leftDash() {
		game.leftDash(game.getCurrentCharacter());
	}

	public void endWizardsTurn() {
		game.endWizardsTurn();
	}

	public void nextMonsterWave() throws IOException {
		game.nextMonsterWave(dao.getIncantations());
	}

	public boolean monstersTurnEnded() {
		return game.monstersTurnEnded();
	}

	public void nextMonster() {
		game.nextMonster();
	}

	public boolean currentCharacterInWizardsRange() {
		return game.currentCharacterInWizardsRange();
	}

	public void playMonstersTurnPart1() {
		game.playMonstersTurnPart1();
	}

	public void playMonstersTurnPart2() {
		game.playMonstersTurnPart2();
	}

	
	
	
	
	public Game getGame() {
		return game;
	}

	public Character getCurrentCharacter() {
		return game.getCurrentCharacter();
	}

	public boolean[] getCurrentCharacterRange() {
		return game.getCurrentCharacterRange();
	}

	public boolean[] getWizardsRange() {
		return game.getWizardsRange();
	}

	public IBoardElement[] getBoard() {
		return game.getBoard();
	}

	public CastZone getCastZone() {
		return game.getCastZone();
	}
}
