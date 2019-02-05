package game;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Level
{
	private int difficulty;
	private Map<String, Integer> hordes; //String:name of the horde, Integer:frequence of appearance
	
	
	public Level() {
		//Empty constructor for jackson
	}
	
	
	public int getDifficulty() {
		return difficulty;
	}
	public Map<String, Integer> getHordes() {
		return hordes;
	}
}
