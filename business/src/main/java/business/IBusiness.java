package business;

import java.io.IOException;

import boardelement.IBoardElement;
import boardelement.Wizard;
import boardelement.WizardFactory;
import boardelement.Character;
import game.Game;
import javafx.collections.ListChangeListener;
import spell.Card;
import spell.ISpell;
import zone.ZoneType;

public interface IBusiness
{
	//Getters:
	public Character getSelectedCharacter();
	public void setSelectedCharacter(Character selectedCharacter);
	public ZoneType getSelectedZone();
	public void setSelectedZone(ZoneType selectedZone);
	public Card getSelectedCard();
	public void setSelectedCard(Card selectedCard);
	
	//Gestion des games:
	public void initAllConstant() throws IOException;
	public Game[] getGames() throws IOException;
	public boolean gameExists(String name) throws IOException;
	public WizardFactory[] getRandomWizards(int number, WizardFactory[] alreadyChoosens) throws IOException;
	public Wizard[] createWizards(WizardFactory[] alreadyChoosens) throws IOException;
	public void newGame(Game game) throws IOException;
	public void loadGame(String name) throws IOException;
	public void saveGame() throws IOException;
	public void deleteGame(String name) throws IOException;
	
	/*
	 * Ordre d'appel en jeu:
	 */
	public boolean isFinished();
	public boolean isVictory();
	
	public boolean levelFinished();
	public void nextLevel() throws IOException;
	
	public boolean isWizardsTurn();
	public void beginWizardsTurn();
	public boolean canMove();
	public void rightWalk();
	public void leftWalk();
	public boolean canDash();
	public void rightDash();
	public void leftDash();
	public boolean isPowerUsed();
	public void usePower();
	public void addSelectedCardToCast();
	public void castNextSpell();
	public boolean castZoneIsEmpty();
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
	
	//D'autres getters et listeners utiles
	public Character getCurrentCharacter();
	public boolean[] getCurrentCharacterRange();
	public void addCurrentCharacterRangeListener(ListChangeListener<Boolean> listener);
	public void removeCurrentCharacterRangeListener(ListChangeListener<Boolean> listener);
	public boolean[] getWizardsRange();
	public void addWizardsRangeListener(ListChangeListener<Boolean> listener);
	public void removeWizardsRangeListener(ListChangeListener<Boolean> listener);
	public IBoardElement[] getBoard();
	public void addBoardListener(ListChangeListener<IBoardElement> listener);
	public void removeBoardListener(ListChangeListener<IBoardElement> listener);
	public ISpell getNextSpellToCast();
}
