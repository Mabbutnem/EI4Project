package dao;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import boardelement.MonsterFactory;
import boardelement.WizardFactory;
import constant.AllConstant;
import game.Horde;
import game.Level;
import spell.Card;
import spell.Incantation;

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
	
	
	
	public JSONDataGameReaderDao() {
		//Empty constructor
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





	@Override
	public Card[] getCards() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public AllConstant getConstant() throws IOException {
		File file = new File(getCompletePathFile(constantsFileName));				
		ObjectMapper om = new ObjectMapper();
		AllConstant[] array = om.readValue(file,AllConstant[].class);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Stream<AllConstant> stream=Stream.of(array);
		stream=stream.filter(o->true);		
		List<AllConstant> list=stream.collect(Collectors.toList());
		return list.get(0);
	}



	@Override
	public Horde[] getHordes() throws IOException {
		File file = new File(getCompletePathFile(hordesFileName));				
		ObjectMapper om = new ObjectMapper();
		Horde[] array = om.readValue(file,Horde[].class);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Stream<Horde> stream=Stream.of(array);
		stream=stream.filter(o->true);		
		List<Horde> list=stream.collect(Collectors.toList());
		return list.toArray(new Horde[0]);
	}



	@Override
	public Incantation[] getIncantations() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Level[] getLevels() throws IOException {
		File file = new File(getCompletePathFile(levelsFileName));				
		ObjectMapper om = new ObjectMapper();
		Level[] array = om.readValue(file,Level[].class);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Stream<Level> stream=Stream.of(array);
		stream=stream.filter(o->true);		
		List<Level> list=stream.collect(Collectors.toList());
		return list.toArray(new Level[0]);
	}
	
	
	
	@Override
	public MonsterFactory[] getMonsters() throws IOException
	{
		File file = new File(getCompletePathFile(monstersFileName));				
		ObjectMapper om = new ObjectMapper();
		MonsterFactory[] array = om.readValue(file,MonsterFactory[].class);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Stream<MonsterFactory> stream=Stream.of(array);
		stream=stream.filter(o->true);		
		List<MonsterFactory> list=stream.collect(Collectors.toList());
		return list.toArray(new MonsterFactory[0]);
	}



	@Override
	public WizardFactory[] getWizards() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
