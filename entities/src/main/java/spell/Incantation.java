package spell;

import effect.IEffect;
import target.Target;
import utility.INamedObject;

public class Incantation extends Spell
{

	public Incantation(String name, IEffect[] effects)
	{
		super(name, effects);
	}
	
	//Copy constructor
	public Incantation(Incantation i)
	{
		super(i.getName(), i.getEffects());
	}
	
	
	
	public Target getFirstTarget()
	{
		//TODO
		return null;
	}

	
	
	@Override
	public INamedObject cloneObject() {
		return new Incantation(this);
	}
}
