package spell;

import game.Game;

public interface ISpell
{
	public void cast(Game game);
	public String getName();
}
