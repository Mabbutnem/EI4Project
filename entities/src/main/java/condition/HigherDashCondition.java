package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import game.Game;

@JsonTypeName("higherDashCondition")
public class HigherDashCondition extends OneValueCondition {

	public HigherDashCondition() {
		super();
	}
	
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
