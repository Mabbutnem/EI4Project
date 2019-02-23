package spell;

import game.Game;
import utility.INamedObject;
import boardelement.Character;

public interface ISpell extends INamedObject
{
	public String getDescription();
	public Character getChoosenTarget();
	public void setChoosenTarget(Character choosenTarget);
	public void cast(Game game);
}
