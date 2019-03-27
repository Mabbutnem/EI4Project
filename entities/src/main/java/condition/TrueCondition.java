package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import game.Game;

@JsonTypeName("trueCondition")
public class TrueCondition implements ICondition {

	public TrueCondition() {
		super();
	}
	
	@Override
	public String getDescription() {
		return "true";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> true;
	}

}
