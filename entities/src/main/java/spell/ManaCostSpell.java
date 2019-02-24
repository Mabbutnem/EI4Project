package spell;

import effect.IEffect;

public abstract class ManaCostSpell extends Spell
{
	private int cost;
	
	

	public ManaCostSpell(String name, IEffect[] effects, int cost)
	{
		super(name, effects);
		this.cost = cost;
	}



	public int getCost() {
		return cost;
	}

}
