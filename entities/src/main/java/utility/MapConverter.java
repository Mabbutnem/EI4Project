package utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapConverter
{
	private MapConverter()
	{
		throw new IllegalStateException("Utility class");
	}
	
	
	
	public static INamedObject[] getObjectsFromMapNamesFrequencies(Map<String, Integer> mapNamesFrequencies, INamedObject[] namedObjects)
	//namedObjects : all namedObjects from the JSON file
	{
		Map<String, INamedObject> namedObjectsMap = new HashMap<>();
		for(INamedObject no : namedObjects) { namedObjectsMap.put(no.getName(), no); }
		
		List<INamedObject> list = new LinkedList<>();
		
		//On ajoute les namedObjects dans list
		for(String namedObjectName : mapNamesFrequencies.keySet())
		{
			if(!namedObjectsMap.containsKey(namedObjectName)) { throw new IllegalArgumentException("one (or more) namedObject is missing from namedObjects"); }

			//Ajoute une COPIE du namedObject
			list.add(namedObjectsMap.get(namedObjectName).cloneObject());
		}
		
		return list.toArray(new INamedObject[0]);
	}
	
	public static int[] getFrequenciesFromMapNamesFrequencies(Map<String, Integer> mapNamesFrequencies, INamedObject[] actualNamedObjects)
	//actualNamedObjects : namedObjects from the map conversion
	{
		int[] frequencies = new int[actualNamedObjects.length];
		
		for(int i = 0; i < actualNamedObjects.length; i++)
		{
			if(!mapNamesFrequencies.containsKey(actualNamedObjects[i].getName())) { throw new IllegalArgumentException("one (or more) key is missing from mapNamesFrequencies"); }
			
			frequencies[i] = mapNamesFrequencies.get(actualNamedObjects[i].getName());
		}
		
		return frequencies;
	}
	
	
	
	public static INamedObject[] getObjectsFromMapNamesQuantities(Map<String, Integer> mapNamesQuantities, INamedObject[] namedObjects)
	//namedObjects : all namedObjects from the JSON file
	{
		Map<String, INamedObject> namedObjectsMap = new HashMap<>();
		for(INamedObject no : namedObjects) { namedObjectsMap.put(no.getName(), no); }
		
		List<INamedObject> list = new LinkedList<>();
		
		//On ajoute les namedObjects dans list
		for(Entry<String, Integer> entry : mapNamesQuantities.entrySet())
		{
			if(!namedObjectsMap.containsKey(entry.getKey())) { throw new IllegalArgumentException("one (or more) namedObject is missing from namedObjects"); }

			//Ajoute une COPIE du namedObject
			for(int i = 0; i < entry.getValue(); i++)
			{
				list.add(namedObjectsMap.get(entry.getKey()).cloneObject());
			}
		}
		
		return list.toArray(new INamedObject[0]);
	}
}
