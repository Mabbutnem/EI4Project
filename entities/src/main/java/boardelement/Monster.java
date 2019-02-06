package boardelement;

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
	}

	public Monster(int health, int armor, int move, int dash, int range)
	{
		super(health, armor, move, dash, range);
		// TODO Auto-generated constructor stub
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
