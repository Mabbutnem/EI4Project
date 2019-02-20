package spell;

import utility.INamedObject;

public class Incantation extends Spell
{

	//Copy constructor
	public Incantation(Incantation i)
	{
		this.name = i.getName();
	}

	
	
	@Override
	public INamedObject cloneObject() {
		return new Incantation(this);
	}
}
