package boardelement;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import utility.INamedObject;

public class MonsterFactory implements INamedObject
{
	private String name;
	private int maxHealth;
	private int initArmor;
	private int baseMove;
	private int baseRange;
	private Map<String, Integer> mapIncantationsFrequencies; //String:name of the incantation, Integer:frequence of cast
	private float rebornProbability;
	
	
	
	public MonsterFactory() {
		//Empty constructor for jackson
	}
	
	public MonsterFactory(String name, int maxHealth, int initArmor, int baseMove, int baseRange, 
			Map<String, Integer> mapIncantationsFrequencies, float rebornProbability)
	{
		Preconditions.checkArgument(name.length() > 0, "name length was %s but expected strictly positive", name.length());
		Preconditions.checkArgument(maxHealth > 0, "maxHealth was %s but expected strictly positive", maxHealth);
		Preconditions.checkArgument(initArmor >= 0, "initArmor was %s but expected positive", initArmor);
		Preconditions.checkArgument(baseMove > 0, "baseMove was %s but expected strictly positive", baseMove);
		Preconditions.checkArgument(baseRange > 0, "baseRange was %s but expected strictly positive", baseRange);
		for(Map.Entry<String, Integer> entry : mapIncantationsFrequencies.entrySet())
		{
			Preconditions.checkArgument(entry.getValue() > 0, "frequence of cast was %s but expected strictly positive", entry.getValue());
		}
		Preconditions.checkArgument(rebornProbability >= 0 && rebornProbability <= 1, "rebornProbability was %s but expected between 0 and 1", rebornProbability);
		this.name = name;
		this.maxHealth = maxHealth;
		this.initArmor = initArmor;
		this.baseMove = baseMove;
		this.baseRange = baseRange;
		this.mapIncantationsFrequencies = mapIncantationsFrequencies;
		this.rebornProbability = rebornProbability;
	}

	public MonsterFactory(MonsterFactory monsterFactory)
	{
		this.name = monsterFactory.getName();
		this.maxHealth = monsterFactory.getMaxHealth();
		this.initArmor = monsterFactory.getInitArmor();
		this.baseMove = monsterFactory.getBaseMove();
		this.baseRange = monsterFactory.getBaseRange();
		this.mapIncantationsFrequencies = monsterFactory.getMapIncantationsFrequencies()
				.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); //Copy of the Map
		this.rebornProbability = monsterFactory.getRebornProbability();
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

	public Map<String, Integer> getMapIncantationsFrequencies() {
		return mapIncantationsFrequencies;
	}

	public float getRebornProbability() {
		return rebornProbability;
	}

	
	
	@Override
	public INamedObject cloneObject() {
		return new MonsterFactory(this);
	}

	
	
	@Override
	public String toString() {
		return "MonsterFactory [name=" + name + ", maxHealth=" + maxHealth + ", initArmor=" + initArmor + ", baseMove="
				+ baseMove + ", baseRange=" + baseRange + ", mapIncantationsFrequencies=" + mapIncantationsFrequencies
				+ ", rebornProbability=" + rebornProbability + "]";
	}
}
