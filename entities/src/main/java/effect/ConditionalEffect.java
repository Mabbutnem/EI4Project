package effect;

import com.google.common.base.Preconditions;

import condition.ICondition;
import game.Game;
import spell.ISpell;

public abstract class ConditionalEffect implements IApplicableEffect
{
	protected boolean willApply;
	protected IEffect[] effects;
	
	
	
	public ConditionalEffect(IEffect[] effects)
	{
		Preconditions.checkArgument(effects.length > 0, "effects was empty but expected not empty");
		
		willApply = false;
		this.effects = effects;
	}



	public IEffect[] getEffects()
	{
		return effects;
	}
	
	@Override
	public void prepare(Game game, ISpell spell)
	{
		if(willApply)
		{
			for(IEffect e : effects)
			{
				e.prepare(game, spell);
			}
		}
	}
	
	@Override
	public void clean()
	{
		if(willApply)
		{
			for(IEffect e : effects)
			{
				e.clean();
			}
		}
		
		willApply = false;
	}
	
	@Override
	public void apply(Game game, ISpell spell)
	{
		if(willApply)
		{
			for(IEffect e : effects)
			{
				if(e instanceof IApplicableEffect)
				{
					((IApplicableEffect) e).apply(game, spell);
				}
			}
		}
	}
	
	@Override
	public ICondition matchingCondition()
	{
		//TODO
		return null;
	}
	
}
