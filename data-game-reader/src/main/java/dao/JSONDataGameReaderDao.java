package dao;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;

import boardelement.MonsterFactory;
import boardelement.WizardFactory;
import constant.AllConstant;
import game.Horde;
import game.Level;
import spell.Card;
import spell.Incantation;
import utility.Proba;

public class JSONDataGameReaderDao implements IDataGameReaderDao
{
	@Autowired
	private String directoryName;
	@Autowired
	private String extentionName;
	
	@Autowired
	protected String cardsFileName;
	@Autowired
	protected String constantsFileName;
	@Autowired
	protected String hordesFileName;
	@Autowired
	protected String incantationsFileName;
	@Autowired
	protected String levelsFileName;
	@Autowired
	protected String monstersFileName;
	@Autowired
	protected String wizardsFileName;
	
	protected ObjectMapper mapper;
	
	
	
	public JSONDataGameReaderDao() {
		mapper = new ObjectMapper();
		//The object mapper can only see the field and can't see methods
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}



	protected Card[] getCards(Predicate<Card> predicate) throws IOException
	{
		File file = new File(getCompletePathFile(cardsFileName));
		Card[] array = mapper.readValue(file,Card[].class);
		Stream<Card> stream=Stream.of(array);
		stream=stream.filter(predicate);
		List<Card> list=stream.collect(Collectors.toList());
		list.forEach(Card::setDescription);
		return list.toArray(new Card[0]);
	}

	@Override
	public Card getCard(String name){
		try
		{
			return getCards(c -> name.compareTo(c.getName())==0)[0];
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("No card named \"" + name + "\"");
		}
	}

	@Override
	public Card[] getCards(String[] names){
		try
		{
			return getCards(c -> {
				for(String name : names)
				{
					if(name.compareTo(c.getName())==0) { return true; }
				}
				return false;
			});
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("No card named : " + Arrays.toString(names));
		}
	}

	@Override
	public Card[] getCards() throws IOException {
		return getCards(c->true);
	}


	
	public AllConstant getConstant() throws IOException
	{
		File file = new File(getCompletePathFile(constantsFileName));
		AllConstant[] array = mapper.readValue(file,AllConstant[].class);
		Stream<AllConstant> stream=Stream.of(array);
		stream=stream.filter(o->true);
		List<AllConstant> list=stream.collect(Collectors.toList());
		return list.get(0);
	}



	protected Horde[] getHordes(Predicate<Horde> predicate) throws IOException
	{
		File file = new File(getCompletePathFile(hordesFileName));
		Horde[] array = mapper.readValue(file,Horde[].class);
		Stream<Horde> stream=Stream.of(array);
		stream=stream.filter(predicate);
		List<Horde> list=stream.collect(Collectors.toList());
		return list.toArray(new Horde[0]);
	}

	@Override
	public Horde[] getHordes() throws IOException {
		return getHordes(h->true);
	}



	protected Incantation[] getIncantations(Predicate<Incantation> predicate) throws IOException
	{
		File file = new File(getCompletePathFile(incantationsFileName));
		Incantation[] array = mapper.readValue(file,Incantation[].class);
		Stream<Incantation> stream=Stream.of(array);
		stream=stream.filter(predicate);
		List<Incantation> list=stream.collect(Collectors.toList());
		list.forEach(Incantation::setDescription);
		return list.toArray(new Incantation[0]);
	}

	@Override
	public Incantation[] getIncantations() throws IOException {
		return getIncantations(h->true);
	}



	protected Level[] getLevels(Predicate<Level> predicate) throws IOException
	{
		File file = new File(getCompletePathFile(levelsFileName));
		Level[] array = mapper.readValue(file,Level[].class);
		Stream<Level> stream=Stream.of(array);
		stream=stream.filter(predicate);
		List<Level> list=stream.collect(Collectors.toList());
		return list.toArray(new Level[0]);
	}

	@Override
	public Level getRandomLevel(int levelDifficulty) throws IOException {
		Level[] levels = getLevels(l->l.getDifficulty()==levelDifficulty);

		Preconditions.checkArgument(levels.length > 0, "Not enought levels");
		
		return levels[Proba.nextInt(levels.length)];
	}

	@Override
	public Level[] getLevels() throws IOException {
		return getLevels(l->true);
	}
	
	
	
	protected MonsterFactory[] getMonsters(Predicate<MonsterFactory> predicate) throws IOException
	{
		File file = new File(getCompletePathFile(monstersFileName));
		MonsterFactory[] array = mapper.readValue(file,MonsterFactory[].class);
		Stream<MonsterFactory> stream=Stream.of(array);
		stream=stream.filter(predicate);
		List<MonsterFactory> list=stream.collect(Collectors.toList());
		return list.toArray(new MonsterFactory[0]);
	}

	@Override
	public MonsterFactory[] getMonsters() throws IOException {
		return getMonsters(m->true);
	}



	protected WizardFactory[] getWizards(Predicate<WizardFactory> predicate) throws IOException
	{
		File file = new File(getCompletePathFile(wizardsFileName));
		WizardFactory[] array = mapper.readValue(file,WizardFactory[].class);
		Stream<WizardFactory> stream=Stream.of(array);
		stream=stream.filter(predicate);
		List<WizardFactory> list=stream.collect(Collectors.toList());
		list.forEach(w->w.getBasePower().setDescription());
		list.forEach(w->w.getTransformedPower().setDescription());
		return list.toArray(new WizardFactory[0]);
	}

	@Override
	public WizardFactory[] getRandomWizards(int number, WizardFactory[] alreadyChoosens) throws IOException
	{
		WizardFactory[] wizards = getWizards(w -> {
			for(WizardFactory alreadyChoosen : alreadyChoosens)
			{
				if(alreadyChoosen.getName().compareTo(w.getName()) == 0) { return false; }
			}
			return true;
		});
		
		Preconditions.checkArgument(wizards.length >= number, "Not enought wizards");
		
		List<WizardFactory> candidateList = new LinkedList<>(Arrays.asList(wizards));
		List<WizardFactory> choosenList = new LinkedList<>();
		
		for(int i = 0; i < number; i++)
		{
			WizardFactory w = candidateList.get(Proba.nextInt(candidateList.size()));
			candidateList.remove(w);
			choosenList.add(w);
		}
		
		return choosenList.toArray(new WizardFactory[0]);
	}

	@Override
	public WizardFactory[] getWizards() throws IOException {
		return getWizards(w->true);
	}
	
	
	
	private String getProjectPath() throws IOException
	{
		String projectPath = new File(".").getCanonicalPath();
		projectPath = projectPath.substring(0, projectPath.lastIndexOf('\\'));
		
		return projectPath;
	}
	
	protected String getCompletePathFile(String fileName) throws IOException
	{
		return getProjectPath()+directoryName+fileName+extentionName;
	}
}
