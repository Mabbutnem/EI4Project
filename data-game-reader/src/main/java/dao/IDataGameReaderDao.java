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
	Card getCard(String name);
	Card[] getCards(String[] names);
	Card[] getCards() throws IOException;
	
	AllConstant getConstant() throws IOException;
	
	Horde[] getHordes() throws IOException;
	
	Incantation[] getIncantations() throws IOException;
	
	Level getRandomLevel(int levelDifficulty) throws IOException;
	Level[] getLevels() throws IOException;
	
	MonsterFactory[] getMonsters() throws IOException;
	
	WizardFactory[] getRandomWizards(int number, WizardFactory[] alreadyChoosens) throws IOException;
	WizardFactory[] getWizards() throws IOException;
}
