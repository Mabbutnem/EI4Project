package spell;

import game.Game;
import utility.INamedObject;

public interface ISpell extends INamedObject
{
	public void cast(Game game);
	public String getName();
}
