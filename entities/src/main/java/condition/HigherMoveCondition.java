package condition;

import java.util.function.Predicate;

import game.Game;

public class HigherMoveCondition extends OneValueCondition {

	public HigherMoveCondition(int value) {
		super(value);
	}

	@Override
	public String getDescription() {
		return super.getDescription() + value + " move" + (value > 1? "s " : " ") + "or more";
	}
	
	@Override
	public Predicate<Game> getPredicate() {
		return g -> g.getCurrentCharacter().getMove() >= value ;
	}

}
