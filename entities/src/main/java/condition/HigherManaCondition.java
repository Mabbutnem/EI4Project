package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Wizard;
import game.Game;

@JsonTypeName("higherManaCondition")
public class HigherManaCondition extends OneValueCondition {

	public HigherManaCondition() {
		super();
	}
	
	public HigherManaCondition(int value) {
		super(value);
	}
	
	@Override
	public String getDescription() {
		return super.getDescription() + value + " mana or more";
	}

	@Override
	public Predicate<Game> getPredicate() {
		return g -> g.getCurrentCharacter() instanceof Wizard && ((Wizard) g.getCurrentCharacter()).getMana() >= value ;
	}

}
