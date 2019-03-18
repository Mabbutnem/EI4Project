package dao;

import java.io.IOException;

import boardelement.MonsterFactory;
import boardelement.WizardFactory;
import constant.AllConstant;
import game.Horde;
import game.Level;
import listener.ICardDaoListener;
import spell.Card;
import spell.Incantation;

public interface IDataGameReaderDao extends ICardDaoListener
{
	public Card getCard(String name);
	public Card[] getCards(String[] names);
	public Card[] getCards() throws IOException;
	
	public AllConstant getConstant() throws IOException;
	
	public Horde[] getHordes() throws IOException;
	
	public Incantation[] getIncantations() throws IOException;
	
	public Level getRandomLevel(int levelDifficulty) throws IOException;
	public Level[] getLevels() throws IOException;
	
	public MonsterFactory[] getMonsters() throws IOException;
	
	public WizardFactory[] getRandomWizards(int number, WizardFactory[] alreadyChoosens) throws IOException;
	public WizardFactory[] getWizards() throws IOException;
}
