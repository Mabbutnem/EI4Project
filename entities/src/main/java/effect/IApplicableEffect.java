package effect;

import condition.ICondition;
import game.Game;
import spell.ISpell;

public interface IApplicableEffect extends IEffect
{
	public void apply(Game game, ISpell spell);
	public ICondition matchingCondition();
}
