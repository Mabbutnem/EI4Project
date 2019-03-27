package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import game.Game;

@JsonTypeName("falseCondition")
public class FalseCondition implements ICondition {

	public FalseCondition() {
		
	}
	
	@Override
	public String getDescription() {
		return "false";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> false;
	}

}
