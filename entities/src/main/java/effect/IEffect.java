package effect;

import game.Game;
import spell.ISpell;

public interface IEffect
{
	public String getDescription();
	public void prepare(Game game, ISpell spell);
	public void clean();
}
