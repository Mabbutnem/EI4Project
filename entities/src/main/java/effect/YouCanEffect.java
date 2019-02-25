package effect;

import com.google.common.base.Preconditions;

import game.Game;
import listener.IYouCanEffectListener;
import spell.ISpell;

public class YouCanEffect extends ConditionalEffect
{
	private static IYouCanEffectListener youCanEffectListener;
	
	private IApplicableEffect effect;
	


	public YouCanEffect(IEffect[] effects, IApplicableEffect effect)
	{
		super(effects);
		
		Preconditions.checkState(youCanEffectListener != null, "youCanEffectListener was not initialised (in static)");
		
		Preconditions.checkArgument(effect != null, "effect was null but expected not null");
		
		this.effect = effect;
	}
	
	

	public static void setYouCanEffectListener(IYouCanEffectListener youCanEffectListener) {
		YouCanEffect.youCanEffectListener = youCanEffectListener;
	}
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepare(Game game, ISpell spell)
	{
		willApply = effect.matchingCondition().getPredicate().test(game)
					&& youCanEffectListener.wantToApply(effect);
		
		if(willApply)
		{
			effect.apply(game, spell);
		}
		
		super.prepare(game, spell);
	}

}
