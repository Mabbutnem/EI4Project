package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import game.Game;
import listener.IYouCanEffectListener;
import spell.ISpell;

@JsonTypeName("youCanEffect")
public class YouCanEffect extends ConditionalEffect
{
	private static IYouCanEffectListener youCanEffectListener;
	
	private IApplicableEffect conditionalEffect;
	
	public YouCanEffect() {
		super();
	}

	public YouCanEffect(IEffect[] effects, IApplicableEffect conditionalEffect)
	{
		super(effects);
		
		Preconditions.checkState(youCanEffectListener != null, "youCanEffectListener was not initialised (in static)");
		
		Preconditions.checkArgument(conditionalEffect != null, "conditionalEffect was null but expected not null");
		
		this.conditionalEffect = conditionalEffect;
	}
	
	

	public static void setYouCanEffectListener(IYouCanEffectListener youCanEffectListener) {
		YouCanEffect.youCanEffectListener = youCanEffectListener;
	}
	
	@Override
	public String getDescription() {
		StringBuilder strBld = new StringBuilder();
		
		strBld.append("you can ").append(conditionalEffect.getDescription());
		if(effects.length > 0) { strBld.append("if you do :\n"); }
		
		for(IEffect e : effects) {
			strBld.append(e.getDescription() + ", ");
		}
		
		String str = strBld.toString();
		if(effects.length > 0) { str = str.substring(0, strBld.length()-2); }
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
		willApply = conditionalEffect.matchingCondition().getPredicate().test(game)
					&& youCanEffectListener.wantToApply(conditionalEffect);
		
		if(willApply)
		{
			conditionalEffect.apply(game, spell);
		}
		
		super.prepare(game, spell);
	}

}
