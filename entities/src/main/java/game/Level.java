package game;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Level
{
	private int difficulty;
	private Map<String, Integer> mapHordesFrequencies; //String:name of the horde, Integer:frequence of appearance
	
	
	public Level() {
		//Empty constructor for jackson
	}
	
	
	
	public Level(int difficulty, Map<String, Integer> mapHordesFrequencies) {
		Preconditions.checkArgument(difficulty > 0, "difficulty was %s but expected strictly positive", difficulty);
		for(Map.Entry<String, Integer> entry : mapHordesFrequencies.entrySet())
		{
			Preconditions.checkArgument(entry.getValue() > 0, "frquence of appearance was %s but expected strictly positive", entry.getValue());
		}
		this.difficulty = difficulty;
		this.mapHordesFrequencies = mapHordesFrequencies;
	}



	public int getDifficulty() {
		return difficulty;
	}
	
	public Map<String, Integer> getMapHordesFrequencies() {
		return mapHordesFrequencies;
	}



	@Override
	public String toString() {
		return "Level [difficulty=" + difficulty + ", mapHordesFrequencies=" + mapHordesFrequencies + "]";
	}
}
