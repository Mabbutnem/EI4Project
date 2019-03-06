package condition;

import java.util.function.Predicate;

import game.Game;

public class HigherDashCondition extends OneValueCondition {

	public HigherDashCondition(int value) {
		super(value);
	}

	@Override
	public String getDescription() {
		return super.getDescription() + value + " dash or more";
	}
	
	@Override
	public Predicate<Game> getPredicate() {
		return g -> g.getCurrentCharacter().getDash() >= value ;
	}

}
