package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import boardelement.MonsterFactory;

public class JSONDataGameReaderDao implements IDataGameReaderDao
{
	@Autowired
	private String directoryName;
	@Autowired
	private String extentionName;
	@Autowired
	private String fileName;
	
	
	
	public JSONDataGameReaderDao() {
		//Empty constructor
	}
	
	
	public void addMonsterFactory(MonsterFactory mf) { add(fileName, mf, MonsterFactory[].class); }
	public void deleteMonsterFactories() { delete(fileName); }
	
	
	private <T> void add(String fileName, T t, Class<T[]> tClass)
	{
		try{
			String currentPath = new File(".").getCanonicalPath();
			
			File file = new File(currentPath+directoryName+fileName+extentionName);
			ObjectMapper om = new ObjectMapper();
			T[] tArray = om.readValue(file,tClass);
			om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			ArrayList<T> tArrayList=new ArrayList<>(Arrays.asList(tArray));
			tArrayList.add(t);
			om.writeValue(file, tArrayList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private <T> void delete(String fileName)
	{
		try{
			String currentPath = new File(".").getCanonicalPath();
			
			File file = new File(currentPath+directoryName+fileName+extentionName);
			ObjectMapper om = new ObjectMapper();
			ArrayList<T> tArrayList=new ArrayList<>();
			om.writeValue(file, tArrayList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
