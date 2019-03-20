package boardelement;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import spell.Incantation;
import utility.MapConverter;
import utility.Proba;

@JsonTypeName("monster")
public class Monster extends Character
{
	private boolean played;
	private int maxHealth;
	private int initArmor;
	private int baseMove;
	private int baseRange;
	private String name;
	private float rebornProbability;
	
	private Incantation[] incantations;
	private float[] incantationProbabilities;
	
	
	
	public Monster()
	{
		super();
	}
	
	//incantations: all the incantations from the JSON file
	public Monster(MonsterFactory monsterFactory, Incantation[] incantations)
	{
		super();
		
		Preconditions.checkArgument(monsterFactory != null, "monsterFactory was null but expected not null");
		Preconditions.checkArgument(incantations != null, "incantations was null but expected not null");
		
		maxHealth = monsterFactory.getMaxHealth();
		initArmor = monsterFactory.getInitArmor();
		baseMove = monsterFactory.getBaseMove();
		baseRange = monsterFactory.getBaseRange();
		reset();
		setPlayed(false);
		
		name = monsterFactory.getName();
		rebornProbability = monsterFactory.getRebornProbability();
		
		this.incantations = Arrays.asList(
				MapConverter.getObjectsFromMapNamesFrequencies(monsterFactory.getMapIncantationsFrequencies(), incantations)
				).toArray(new Incantation[0]); //Convert INamedObject[] to Incantation[]
		
		incantationProbabilities = Proba.convertFrequencyToProbability(
				MapConverter.getFrequenciesFromMapNamesFrequencies(monsterFactory.getMapIncantationsFrequencies(), this.incantations));
		
	}
	
	
	
	public String getName() {
		return name;
	}

	
	
	public boolean hasPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
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

	public float getRebornProbability() {
		return rebornProbability;
	}
	
	
	
	public float[] getIncantationProbabilities()
	{
		return incantationProbabilities;
	}
	
	public Incantation[] getIncantations()
	{
		return incantations;
	}
	
	public Incantation getRandomIncantation()
	{
		return incantations[Proba.getRandomIndexFrom(incantationProbabilities)];
	}
	
	
	
	@Override
	public void setHealth(int health)
	{
		super.setHealth(Math.min(health, maxHealth));
	}

	@Override
	public void convertArmorToHealth() {
		if(getHealth() + getArmor() <= maxHealth)
		{
			gainHealth(getArmor());
			setArmor(0);
		}else {
			loseArmor(maxHealth - getHealth());
			setHealth(maxHealth);
		}
	}

	public void reset()
	{
		setAlive(true);
		resetHealth();
		resetArmor();
		resetMove();
		resetRange();
		resetFreeze();
	}
	
	@Override
	public void resetHealth() {
		setHealth(getMaxHealth());
	}
	
	@Override
	public void resetArmor() {
		setArmor(getInitArmor());
	}
	
	@Override
	public void resetMove() {
		setMove(baseMove);
	}

	@Override
	public void resetRange() {
		setRange(baseRange);
	}

}