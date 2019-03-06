package condition;

import java.util.function.Predicate;

import game.Game;

public class FalseCondition implements ICondition {

	@Override
	public String getDescription() {
		return "false";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> false;
	}

}
