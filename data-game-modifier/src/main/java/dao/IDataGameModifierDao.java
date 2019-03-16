package dao;

import java.io.IOException;

import boardelement.MonsterFactory;
import boardelement.WizardFactory;
import constant.AllConstant;
import game.Horde;
import game.Level;
import spell.Card;
import spell.Incantation;

public interface IDataGameModifierDao extends IDataGameReaderDao
{
	public boolean cardExists(String name) throws IOException;
	public void addCard(Card card) throws IOException;
	//public void modifyCard(String name, Card card) throws IOException;
	//public void removeCard(Card card) throws IOException;
	public void clearCards() throws IOException;
	
	public void setConstants(AllConstant constants) throws IOException;

	public boolean hordeExists(String name) throws IOException;
	public void addHorde(Horde horde) throws IOException;
	//public void modifyHorde(String name, Horde horde) throws IOException;
	//public void removeHorde(Horde horde) throws IOException;
	public void clearHordes() throws IOException;

	public boolean incantationExists(String name) throws IOException;
	public void addIncantation(Incantation inc) throws IOException;
	//public void modifyIncantation(String name, Incantation inc) throws IOException;
	//public void removeIncantation(Incantation inc) throws IOException;
	public void clearIncantations() throws IOException;
	
	public void addLevel(Level level) throws IOException;
	public void clearLevels() throws IOException;

	public boolean monsterExists(String name) throws IOException;
	public void addMonster(MonsterFactory mf) throws IOException;
	//public void modifyMonster(String name, MonsterFactory mf) throws IOException;
	//public void removeMonster(MonsterFactory mf) throws IOException;
	public void clearMonsters() throws IOException;

	public boolean wizardExists(String name) throws IOException;
	public void addWizard(WizardFactory wf) throws IOException;
	//public void modifyWizard(String name, WizardFactory wf) throws IOException;
	//public void removeWizard(WizardFactory wf) throws IOException;
	public void clearWizards() throws IOException;
}
