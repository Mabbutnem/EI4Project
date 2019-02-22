package spell;

import target.TargetType;
import utility.INamedObject;

public class Incantation extends Spell
{

	//Copy constructor
	public Incantation(Incantation i)
	{
		this.name = i.getName();
	}
	
	
	
	public TargetType getFirstTargetType()
	{
		//TODO
		return null;
	}

	
	
	@Override
	public INamedObject cloneObject() {
		return new Incantation(this);
	}
}
