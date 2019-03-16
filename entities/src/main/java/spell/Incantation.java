package spell;

import java.util.Arrays;

import effect.ConditionalEffect;
import effect.IEffect;
import effect.TargetableEffect;
import target.Target;
import target.TargetType;
import utility.INamedObject;

public class Incantation extends Spell
{

	public Incantation() {
		super();
	}
	
	public Incantation(String name, IEffect[] effects)
	{
		super(name, effects);
		
		//The first effect of an incantation has to be an effect with a target...
		//...and, If you want to add a CHOICE target type effects in your incantations effects,
		//				you have to set the first effect has a CHOICE target type effect
		verifyChoiceTargetableEffectIsFirstOrNotAfter(effects, true);
	}
	
	
	
	// !!! recursive function !!!
	private void verifyChoiceTargetableEffectIsFirstOrNotAfter(IEffect[] effects, boolean isRoot)
	{
		if(effects.length == 0) { return; }
		
		if(isRoot &&
		   !(effects[0] instanceof TargetableEffect))
		{
			throw new IllegalArgumentException("The first effect of an incantation has to be an effect with a target");
		}
		
		if(isRoot &&
		   ((TargetableEffect) effects[0]).getTarget().getType() == TargetType.CHOICE)
		{ return; }
		
		for(IEffect e : effects)
		{
			if(e instanceof ConditionalEffect)
			{
				verifyChoiceTargetableEffectIsFirstOrNotAfter(((ConditionalEffect) e).getEffects(), false);
			}
			
			if(e instanceof TargetableEffect &&
			   ((TargetableEffect) e).getTarget().getType() == TargetType.CHOICE)
			{
				throw new IllegalArgumentException("If you want to add a CHOICE target type effects in your incantations effects,"
						+ " you have to set the first effect has a CHOICE target type effect");
			}
		}
	}

	//Copy constructor
	public Incantation(Incantation i)
	{
		super(i.getName(), i.getEffects());
	}
	
	
	
	public Target getFirstTarget()
	{
		if(effects.length == 0) { return null; }
		return ((TargetableEffect) effects[0]).getTarget();
	}

	
	
	@Override
	public INamedObject cloneObject() {
		return new Incantation(this);
	}

	
	
	@Override
	public String toString() {
		return "Incantation [name=" + name + ", effects=" + Arrays.toString(effects) + ", description=" + description
				+ ", choosenTarget=" + choosenTarget + ", words=" + words + "]";
	}
}
