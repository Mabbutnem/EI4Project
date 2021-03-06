package effect;

import com.fasterxml.jackson.annotation.JsonIgnore;

import condition.FalseCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;

public abstract class ConditionalEffect implements IApplicableEffect
{
	@JsonIgnore
	protected boolean willApply;
	protected IEffect[] effects;
	
	
	
	public ConditionalEffect() { 
		super();
		willApply = false;
	}
	
	public ConditionalEffect(IEffect[] effects)
	{
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
		return new FalseCondition();
	}
	
}
