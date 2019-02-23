package effect;

import game.Game;
import spell.ISpell;

public interface IApplicableEffect extends IEffect
{
	public void apply(Game game, ISpell spell);
}
