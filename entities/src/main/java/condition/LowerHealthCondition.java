package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import game.Game;

@JsonTypeName("lowerHealthCondition")
public class LowerHealthCondition extends OneValueCondition {

	public LowerHealthCondition() {
		super();
	}
	
	public LowerHealthCondition(int value) {
		super(value);
	}

	@Override
	public String getDescription() {
		return super.getDescription() + value + " health or less";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> g.getCurrentCharacter().getHealth() <= value ;
	}

}
