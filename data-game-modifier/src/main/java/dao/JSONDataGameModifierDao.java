package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import boardelement.MonsterFactory;
import boardelement.WizardFactory;
import constant.AllConstant;
import game.Horde;
import game.Level;
import spell.Card;
import spell.Incantation;

public class JSONDataGameModifierDao extends JSONDataGameReaderDao implements IDataGameModifierDao
{
	
	public JSONDataGameModifierDao() {
		//Empty constructor
	}
	
	
	
	public void addMonster(MonsterFactory mf) throws IOException {
		add(monstersFileName, mf, MonsterFactory[].class);
	}
	
	public void clearMonsters() throws IOException {
		clear(monstersFileName);
	}



	@Override
	public void addCard(Card card) throws IOException {
		add(cardsFileName, card, Card[].class);
	}

	@Override
	public void clearCards() throws IOException {
		clear(cardsFileName);
	}



	@Override
	public void setConstants(AllConstant constants) throws IOException {
		clear(constantsFileName);
		add(constantsFileName, constants, AllConstant[].class);
	}



	@Override
	public void addHorde(Horde horde) throws IOException {
		add(hordesFileName, horde, Horde[].class);
	}

	@Override
	public void clearHordes() throws IOException {
		clear(hordesFileName);
	}



	@Override
	public void addIncantation(Incantation inc) throws IOException {
		add(incantationsFileName, inc, Incantation[].class);
	}
	
	@Override
	public void clearIncantations() throws IOException {
		clear(incantationsFileName);
	}



	@Override
	public void addLevel(Level level) throws IOException {
		add(levelsFileName, level, Level[].class);
	}

	@Override
	public void clearLevels() throws IOException {
		clear(levelsFileName);
	}



	@Override
	public void addWizards(WizardFactory wf) throws IOException {
		add(wizardsFileName, wf, WizardFactory[].class);
	}

	@Override
	public void clearWizards() throws IOException {
		clear(wizardsFileName);
	}
	
	
	
	
	
	private <T> void add(String fileName, T t, Class<T[]> tClass) throws IOException
	{
		File file = new File(getCompletePathFile(fileName));
		ObjectMapper om = new ObjectMapper();
		T[] tArray = om.readValue(file,tClass);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		ArrayList<T> tArrayList=new ArrayList<>(Arrays.asList(tArray));
		tArrayList.add(t);
		om.writeValue(file, tArrayList);
	}
	
	private <T> void clear(String fileName) throws IOException
	{
		File file = new File(getCompletePathFile(fileName));
		ObjectMapper om = new ObjectMapper();
		ArrayList<T> tArrayList=new ArrayList<>();
		om.writeValue(file, tArrayList);
	}

}
