package boardelement;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

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



	public MonsterFactory(String name, Map<String, Integer> incantations, float rebornProbability) {
		Preconditions.checkArgument(name.length() > 0, "name length was %s but expected strictly positive", name.length());
		Preconditions.checkArgument(rebornProbability >= 0 && rebornProbability <= 1, "rebornProbability was %s but expected between 0 and 1", rebornProbability);
		this.name = name;
		this.incantations = incantations;
		this.rebornProbability = rebornProbability;
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
