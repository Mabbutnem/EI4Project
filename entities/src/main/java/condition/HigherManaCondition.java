package condition;

import java.util.function.Predicate;

import boardelement.Wizard;
import game.Game;

public class HigherManaCondition extends OneValueCondition {

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
