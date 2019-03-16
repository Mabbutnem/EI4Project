package spell;

import com.google.common.base.Preconditions;

import effect.IEffect;

public abstract class ManaCostSpell extends Spell
{
	private int cost;
	
	
	
	public ManaCostSpell() {
		super();
	}
	
	public ManaCostSpell(String name, IEffect[] effects, int cost)
	{
		super(name, effects);
		
		Preconditions.checkArgument(cost >= 0, "cost was %s but expected positive", cost);
		
		this.cost = cost;
	}



	public int getCost() {
		return cost;
	}

}
