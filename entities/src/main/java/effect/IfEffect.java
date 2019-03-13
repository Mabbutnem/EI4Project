package effect;

import com.google.common.base.Preconditions;

import condition.ICondition;
import game.Game;
import spell.ISpell;

public class IfEffect extends ConditionalEffect
{
	private ICondition condition;
	
	
	
	public IfEffect(IEffect[] effects, ICondition condition)
	{
		super(effects);

		Preconditions.checkArgument(effects.length > 0, "effects was empty but expected not empty");
		Preconditions.checkArgument(condition != null, "condition was null but expected not null");
		
		this.condition = condition;
	}
	
	

	@Override
	public String getDescription() {
		String str = condition.getDescription() + " :\n";
		for(IEffect effect: effects) {
			str += effect.getDescription() + ", ";
		}
		str = str.substring(0, str.length()-2);
		return str;
	}

	@Override
	public void prepare(Game game, ISpell spell)
	{
		willApply = condition.getPredicate().test(game);
		super.prepare(game, spell);
	}

}
