package effect;

import game.Game;

public interface IApplicableEffect extends IEffect
{
	public void apply(Game game);
}
