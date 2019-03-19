package business;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import boardelement.IBoardElement;
import boardelement.Wizard;
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
	
	
	
	
	
	public void initAllConstant() throws IOException {
		dao.getConstant().initAllConstant();
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
	
	public Wizard[] createWizards(WizardFactory[] alreadyChoosens) throws IOException
	{
		Wizard[] wizards = new Wizard[alreadyChoosens.length];
		
		for(int i = 0; i < alreadyChoosens.length; i++)
		{
			wizards[i] = new Wizard(alreadyChoosens[i], dao.getCards());
		}
		
		return wizards;
	}

	public void newGame(Game game) throws IOException {
		dao.newGame(game);
		loadGame(game.getName());
	}

	public void loadGame(String name) throws IOException {
		this.game = dao.loadGame(name);
	}

	public void saveGame() throws IOException {
		Preconditions.checkState(game != null, "game was null be expected not null");
		
		dao.saveGame(game);
	}

	public void deleteGame(String name) throws IOException {
		dao.deleteGame(name);
		if(game.getName().compareTo(name)==0) { this.game = null; }
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
	
	public void rightWalk() {
		game.rightWalk(getCurrentCharacter());
	}
	
	public void leftWalk() {
		game.leftWalk(getCurrentCharacter());
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
