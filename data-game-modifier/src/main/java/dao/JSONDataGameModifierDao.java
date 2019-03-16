package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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



	@Override
	public boolean cardExists(String name) throws IOException {
		return getCards(c -> name.compareTo(c.getName())==0).length > 0;
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
	public boolean hordeExists(String name) throws IOException {
		return getHordes(h -> name.compareTo(h.getName())==0).length > 0;
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
	public boolean incantationExists(String name) throws IOException {
		return getIncantations(i -> name.compareTo(i.getName())==0).length > 0;
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
	public boolean monsterExists(String name) throws IOException {
		return getMonsters(mf -> name.compareTo(mf.getName())==0).length > 0;
	}
	
	@Override
	public void addMonster(MonsterFactory mf) throws IOException {
		add(monstersFileName, mf, MonsterFactory[].class);
	}

	@Override
	public void clearMonsters() throws IOException {
		clear(monstersFileName);
	}



	@Override
	public boolean wizardExists(String name) throws IOException {
		return getWizards(wf -> name.compareTo(wf.getName())==0).length > 0;
	}
	
	@Override
	public void addWizard(WizardFactory wf) throws IOException {
		add(wizardsFileName, wf, WizardFactory[].class);
	}

	@Override
	public void clearWizards() throws IOException {
		clear(wizardsFileName);
	}
	
	
	
	

	private <T> void add(String fileName, T t, Class<T[]> tClass) throws IOException
	{
		File file = new File(getCompletePathFile(fileName));
		T[] tArray = mapper.readValue(file,tClass);
		ArrayList<T> tArrayList=new ArrayList<>(Arrays.asList(tArray));
		tArrayList.add(t);
		mapper.writeValue(file, tArrayList);
	}
	
	private <T> void clear(String fileName) throws IOException
	{
		File file = new File(getCompletePathFile(fileName));
		ArrayList<T> tArrayList=new ArrayList<>();
		mapper.writeValue(file, tArrayList);
	}

}
