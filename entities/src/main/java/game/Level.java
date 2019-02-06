package game;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Level
{
	private int difficulty;
	private Map<String, Integer> hordes; //String:name of the horde, Integer:frequence of appearance
	
	
	public Level() {
		//Empty constructor for jackson
	}
	
	
	
	public Level(int difficulty, Map<String, Integer> hordes) {
		Preconditions.checkState(difficulty > 0, "difficulty was %s but expected strictly positive", difficulty);
		this.difficulty = difficulty;
		this.hordes = hordes;
	}



	public int getDifficulty() {
		return difficulty;
	}
	public Map<String, Integer> getHordes() {
		return hordes;
	}
}
