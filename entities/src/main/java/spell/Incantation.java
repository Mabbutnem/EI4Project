package spell;

import target.Target;
import utility.INamedObject;

public class Incantation extends Spell
{

	//Copy constructor
	public Incantation(Incantation i)
	{
		this.name = i.getName();
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
