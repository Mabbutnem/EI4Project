package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import game.Game;

@JsonTypeName("higherArmorCondition")
public class HigherArmorCondition extends OneValueCondition {

	public HigherArmorCondition() {
		super();
	}
	
	public HigherArmorCondition(int value) {
		super(value);
	}

	@Override
	public String getDescription() {
		return super.getDescription() + value + " armor or more";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> g.getCurrentCharacter().getArmor() >= value ;
	}

}
