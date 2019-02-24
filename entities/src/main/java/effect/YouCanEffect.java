package effect;

import game.Game;
import listener.IYouCanEffectListener;
import spell.ISpell;

public class YouCanEffect extends ConditionalEffect
{
	private static IYouCanEffectListener youCanEffectListener;
	
	private IApplicableEffect effect;
	

	
	

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
		willApply = effect.matchingCondition().test(game);
		
		if(willApply)
		{
			willApply = youCanEffectListener.wantToApply(effect);
			effect.apply(game, spell);
		}
		
		super.prepare(game, spell);
	}

}
