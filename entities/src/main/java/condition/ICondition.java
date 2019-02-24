package condition;

import game.Game;

public interface ICondition
{
	public String getDescription();
	public boolean test(Game game);
}
