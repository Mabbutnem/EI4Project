package spell;

import game.Game;
import utility.INamedObject;

public interface ISpell extends INamedObject
{
	public String getDescription();
	public void setChoosenTarget(Character choosenTarget);
	public void cast(Game game);
}
