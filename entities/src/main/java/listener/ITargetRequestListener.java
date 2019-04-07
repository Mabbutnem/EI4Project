package listener;

import game.Game;
import target.TargetConstraint;
import boardelement.Character;

public interface ITargetRequestListener
{
	public Character chooseTarget(Game game, TargetConstraint[] targetConstraints);
}
