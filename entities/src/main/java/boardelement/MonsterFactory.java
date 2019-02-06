package boardelement;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MonsterFactory
{
	private String name;
	private int maxHealth;
	private int initArmor;
	private int baseMove;
	private int baseRange;
	private Map<String, Integer> incantations; //String:name of the incantation, Integer:frequence of cast
	private float rebornProbability;
	
	
	
	public MonsterFactory() {
		//Empty constructor for jackson
	}
	
	public MonsterFactory(String name, int maxHealth, int initArmor, int baseMove, int baseRange, 
			Map<String, Integer> incantations, float rebornProbability)
	{
		Preconditions.checkArgument(name.length() > 0, "name length was %s but expected strictly positive", name.length());
		Preconditions.checkArgument(maxHealth > 0, "maxHealth was %s but expected strictly positive", maxHealth);
		Preconditions.checkArgument(initArmor >= 0, "initArmor was %s but expected positive", initArmor);
		Preconditions.checkArgument(baseMove > 0, "baseMove was %s but expected strictly positive", baseMove);
		Preconditions.checkArgument(baseRange > 0, "baseRange was %s but expected strictly positive", baseRange);
		for(Map.Entry<String, Integer> entry : incantations.entrySet())
		{
			Preconditions.checkArgument(entry.getValue() > 0, "frequence of cast was %s but expected strictly positive", entry.getValue());
		}
		Preconditions.checkArgument(rebornProbability >= 0 && rebornProbability <= 1, "rebornProbability was %s but expected between 0 and 1", rebornProbability);
		this.name = name;
		this.maxHealth = maxHealth;
		this.initArmor = initArmor;
		this.baseMove = baseMove;
		this.baseRange = baseRange;
		this.incantations = incantations;
		this.rebornProbability = rebornProbability;
	}



	public String getName() {
		return name;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getInitArmor() {
		return initArmor;
	}

	public int getBaseMove() {
		return baseMove;
	}

	public int getBaseRange() {
		return baseRange;
	}

	public Map<String, Integer> getIncantations() {
		return incantations;
	}

	public float getRebornProbability() {
		return rebornProbability;
	}
}
