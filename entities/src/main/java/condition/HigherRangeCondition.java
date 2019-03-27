package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import game.Game;

@JsonTypeName("higherRangeCondition")
public class HigherRangeCondition extends OneValueCondition {

	public HigherRangeCondition() {
		super();
	}
	
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
