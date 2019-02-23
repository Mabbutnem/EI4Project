package listener;

import game.Game;
import boardelement.Character;

public interface ITargetRequestListener
{
	public Character chooseTarget(Game game);
}
