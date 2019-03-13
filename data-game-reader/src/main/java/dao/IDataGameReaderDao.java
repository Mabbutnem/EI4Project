package dao;

import java.io.IOException;

import boardelement.MonsterFactory;
import boardelement.WizardFactory;
import constant.AllConstant;
import game.Horde;
import game.Level;
import spell.Card;
import spell.Incantation;

public interface IDataGameReaderDao
{
	Card[] getCards() throws IOException;
	AllConstant getConstant() throws IOException;
	Horde[] getHordes() throws IOException;
	Incantation[] getIncantations() throws IOException;
	Level[] getLevels() throws IOException;
	MonsterFactory[] getMonsters() throws IOException;
	WizardFactory[] getWizards() throws IOException;
}
