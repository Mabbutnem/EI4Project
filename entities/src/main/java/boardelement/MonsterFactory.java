package boardelement;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MonsterFactory
{
	private String name;
	/*
	 * all the attributes
	 */
	private Map<String, Integer> incantations; //String:name of the incantation, Integer:frequence of cast
	private float rebornProbability;
	
	
	
	public MonsterFactory() {
		//Empty constructor for jackson
	}



	public String getName() {
		return name;
	}

	public Map<String, Integer> getIncantations() {
		return incantations;
	}

	public float getRebornProbability() {
		return rebornProbability;
	}
}
