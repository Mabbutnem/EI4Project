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
import javafx.collections.ListChangeListener;
import spell.Card;
import spell.ISpell;
import spell.Power;
import zone.ZoneType;

public class Business implements IBusiness
{
	@Autowired
	private IDao dao;
	
	private Game game;
	
	private Character selectedCharacter;
	private ZoneType selectedZone;
	private Card selectedCard;
	
	
	
	
	
	public Character getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(Character selectedCharacter)
	{
		if(selectedCharacter == null) {
			return;
		}
		
		if(this.selectedCharacter != selectedCharacter)
		{
			selectedCard = null;
			selectedZone = null;
			
			if(game.isWizardsTurn() && selectedCharacter instanceof Wizard) {
				game.setCurrentCharacter(selectedCharacter);
				selectedZone = ZoneType.HAND;
			}
		}
		
		this.selectedCharacter = selectedCharacter;
	}

	public ZoneType getSelectedZone() {
		return selectedZone;
	}

	public void setSelectedZone(ZoneType selectedZone)
	{
		if(this.selectedZone != selectedZone)
		{
			selectedCard = null;
		}
		
		this.selectedZone = selectedZone;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Card selectedCard) {
		this.selectedCard = selectedCard;
	}
	
	
	
	

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
		if(game != null && game.getName().compareTo(name)==0) { this.game = null; }
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

	public boolean isWizardsTurn() {
		return game.isWizardsTurn();
	}
	
	public void beginWizardsTurn() {
		game.beginWizardsTurn();
	}
	
	public boolean canMove() {
		return game.getCurrentCharacter().canMove();
	}
	
	public void rightWalk() {
		game.rightWalk(game.getCurrentCharacter());
	}
	
	public void leftWalk() {
		game.leftWalk(game.getCurrentCharacter());
	}
	
	public boolean canDash() {
		return game.getCurrentCharacter().canDash();
	}
	
	public void rightDash() {
		game.rightDash(game.getCurrentCharacter());
	}

	public void leftDash() {
		game.leftDash(game.getCurrentCharacter());
	}
	
	public boolean isPowerUsed() {
		return ((Wizard) game.getCurrentCharacter()).isPowerUsed();
	}
	
	public void usePower() {
		Power power = ((Wizard) game.getCurrentCharacter()).getPower();
		
		Wizard currentWizard = (Wizard) game.getCurrentCharacter();
		
		Preconditions.checkState(currentWizard.getMana() >= power.getCost(), "Not enought mana to cast your power");
		
		currentWizard.loseMana(power.getCost());
		currentWizard.setPowerUsed(true);
		
		game.getCastZone().add(power);
	}
	
	public void addSelectedCardToCast() {
		Preconditions.checkState(selectedCard != null, "A card must be selected");
		Preconditions.checkState(selectedZone == ZoneType.HAND, "Selected card must be in the hand");
		
		Wizard currentWizard = (Wizard) game.getCurrentCharacter();
		
		Preconditions.checkState(currentWizard.getMana() >= selectedCard.getCost(),
				"Not enought mana to cast this card");

		currentWizard.loseMana(selectedCard.getCost());
		currentWizard.getZoneGroup().remove(selectedCard, selectedZone);
		
		game.getCastZone().add(selectedCard, currentWizard);
		
		selectedCard = null;
	}
	
	public void castNextSpell() {
		game.getCastZone().cast(game);
	}
	
	public boolean castZoneIsEmpty() {
		return game.getCastZone().isEmpty();
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

	
	
	

	public Character getCurrentCharacter() {
		return game.getCurrentCharacter();
	}

	public boolean[] getCurrentCharacterRange() {
		return game.getCurrentCharacterRange();
	}
	
	public void addCurrentCharacterRangeListener(ListChangeListener<Boolean> listener) {
		game.addCurrentCharacterRangeListener(listener);
	}
	
	public void removeCurrentCharacterRangeListener(ListChangeListener<Boolean> listener) {
		game.removeCurrentCharacterRangeListener(listener);
	}

	public boolean[] getWizardsRange() {
		return game.getWizardsRange();
	}
	
	public void addWizardsRangeListener(ListChangeListener<Boolean> listener) {
		game.addWizardsRangeListener(listener);
	}
	
	public void removeWizardsRangeListener(ListChangeListener<Boolean> listener) {
		game.removeWizardsRangeListener(listener);
	}

	public IBoardElement[] getBoard() {
		return game.getBoard();
	}
	
	public void addBoardListener(ListChangeListener<IBoardElement> listener) {
		game.addBoardListener(listener);
	}
	
	public void removeBoardListener(ListChangeListener<IBoardElement> listener) {
		game.removeBoardListener(listener);
	}

	public ISpell getNextSpellToCast() {
		return game.getCastZone().getCurrentSpell();
	}

	public IDao getDao() {
		return dao;
	}
}
