package condition;

import java.util.function.Predicate;

import game.Game;

public interface ICondition
{
	public String getDescription();
	public Predicate<Game> getPredicate();
}
