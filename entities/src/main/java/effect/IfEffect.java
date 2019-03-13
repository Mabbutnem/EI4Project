package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import condition.ICondition;
import game.Game;
import spell.ISpell;

@JsonTypeName("ifEffect")
public class IfEffect extends ConditionalEffect
{
	private ICondition condition;
	
	public IfEffect() { 
		super();
	}
	
	public IfEffect(IEffect[] effects, ICondition condition)
	{
		super(effects);

		Preconditions.checkArgument(effects.length > 0, "effects was empty but expected not empty");
		Preconditions.checkArgument(condition != null, "condition was null but expected not null");
		
		this.condition = condition;
	}
	
	

	@Override
	public String getDescription() {
		StringBuilder strBld = new StringBuilder();
		strBld.append(condition.getDescription()).append(" :\n");
		for(IEffect effect: effects) {
			strBld.append(effect.getDescription()).append(", ");
		}
		String str = strBld.toString();
		str = str.substring(0, str.length()-2);
		if(str.lastIndexOf(',') != -1)
		{
			str = str.substring(0, str.lastIndexOf(',')) + " and" 
					+ str.substring(str.lastIndexOf(',')+1, str.length());
		}
		return str;
	}

	@Override
	public void prepare(Game game, ISpell spell)
	{
		willApply = condition.getPredicate().test(game);
		super.prepare(game, spell);
	}

}
