package spell;

import effect.IEffect;
import utility.INamedObject;

public class Power extends ManaCostSpell
{

	public Power(String name, IEffect[] effects, int cost)
	{
		super(name, effects, cost);
	}

	@Override
	public INamedObject cloneObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
