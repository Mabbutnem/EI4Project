package game;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Horde
{
	private String name;
	private int cost;
	private Map<String, Integer> monsters; //String:name of the monster, Integer:number of monsters
	
	
	public Horde() {
		//Empty constructor for jackson
	}
	
	
	public String getName() {
		return name;
	}
	public int getCost() {
		return cost;
	}
	public Map<String, Integer> getMonsters() {
		return monsters;
	}

}
