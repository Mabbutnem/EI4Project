package spell;

import effect.IEffect;
import utility.INamedObject;

public class Power extends ManaCostSpell
{

	public Power(String name, IEffect[] effects, int cost)
	{
		super(name, effects, cost);
	}
	
	//Copy constructor
	public Power(Power p)
	{
		super(p.getName(), p.getEffects(), p.getCost());
	}
	
	

	@Override
	public INamedObject cloneObject() {
		return new Power(this);
	}

}
