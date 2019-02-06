package boardelement;

import com.google.common.base.Preconditions;

import spell.Incantation;

public class Monster extends Character
{
	private String name;
	private int maxHealth;
	private int initArmor;
	private int baseMove;
	private int baseRange;
	private float rebornProbability;
	
	private Incantation[] incantations;
	private float[] incantationProb;
	
	
	
	public Monster(MonsterFactory monsterFactory, Incantation[] incantations)
	{
		super();
		
		Preconditions.checkArgument(monsterFactory != null, "monsterFactory was null but expected not null");
		
		maxHealth = monsterFactory.getMaxHealth();
		initArmor = monsterFactory.getInitArmor();
		baseMove = monsterFactory.getBaseMove();
		baseRange = monsterFactory.getBaseRange();
		
		setHealth(getMaxHealth());
		setArmor(getInitArmor());
		resetMove();
		resetRange();
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

	public float getRebornProbability() {
		return rebornProbability;
	}



	@Override
	public void resetMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetRange() {
		// TODO Auto-generated method stub
		
	}

}