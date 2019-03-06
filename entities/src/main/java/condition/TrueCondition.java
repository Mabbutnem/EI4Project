package condition;

import java.util.function.Predicate;

import game.Game;

public class TrueCondition implements ICondition {

	@Override
	public String getDescription() {
		return "true";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> true;
	}

}
