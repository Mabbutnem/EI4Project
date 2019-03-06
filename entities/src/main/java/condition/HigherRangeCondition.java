package condition;

import java.util.function.Predicate;

import game.Game;

public class HigherRangeCondition extends OneValueCondition {

	public HigherRangeCondition(int value) {
		super(value);
	}
	
	@Override
	public String getDescription() {
		return super.getDescription() + value + " range" + " or more";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> g.getCurrentCharacter().getRange() >= value ;
	}

}
