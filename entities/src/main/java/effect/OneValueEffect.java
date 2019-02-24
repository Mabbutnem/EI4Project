package effect;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import game.Game;
import spell.ISpell;
import target.Target;
import target.TargetType;

public abstract class OneValueEffect extends TargetableEffect
{
	private int value;
	private int moreValue;
	
	

	public OneValueEffect(Target target, int value) {
		super(target);
		this.value = value;
		moreValue = 0;
	}



	protected int getValue() {
		return value + moreValue;
	}

	public void addValue(int addedValue) {
		moreValue += addedValue;
	}
	
	
	
	// !!! Recursive function !!!
	private List<OneValueEffect> findAllEffectsBeforeThis(IEffect[] effects)
	{
		List<OneValueEffect> myList = new LinkedList<>();
		
		for(IEffect e : effects)
		{
			if(e instanceof OneValueEffect &&
				e.getClass() == this.getClass() &&
				((OneValueEffect) e).getTarget().getType() != TargetType.MORE)
			{
				myList.add((OneValueEffect) e);
			}
			
			if(e instanceof ConditionalEffect)
			{
				myList.addAll(findAllEffectsBeforeThis(((ConditionalEffect) e).getEffects()));
			}
			
			if(myList.contains(this))
			{
				return myList;
			}
		}
		
		myList.clear();
		
		return myList;
	}
	
	@Override
	public void prepare(Game game, ISpell spell)
	{
		List<OneValueEffect> effects = findAllEffectsBeforeThis(spell.getEffects());
		if(effects.isEmpty()) { throw new IllegalStateException("No effect of the same type found before this MORE target type effect"); }
		Collections.reverse(effects);
		
		effects.get(0).addValue(value);
	}
	
	@Override
	public void clean()
	{
		moreValue = 0;
	}
	

}
