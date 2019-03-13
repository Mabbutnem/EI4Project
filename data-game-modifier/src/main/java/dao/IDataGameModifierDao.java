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
	public void addCard(Card card) throws IOException;
	public void clearCards() throws IOException;
	
	public void setConstants(AllConstant constants) throws IOException;
	
	public void addHorde(Horde horde) throws IOException;
	public void clearHordes() throws IOException;
	
	public void addIncantation(Incantation inc) throws IOException;
	public void clearIncantations() throws IOException;
	
	public void addLevel(Level level) throws IOException;
	public void clearLevels() throws IOException;
	
	public void addMonster(MonsterFactory mf) throws IOException;
	public void clearMonsters() throws IOException;
	
	public void addWizards(WizardFactory wf) throws IOException;
	public void clearWizards() throws IOException;
}
