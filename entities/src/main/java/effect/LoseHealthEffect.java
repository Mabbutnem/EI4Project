package effect;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

public class LoseHealthEffect extends OneValueEffect {

	public LoseHealthEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new TrueCondition();
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "remove " + getValue() + " health from all targets" + getConstraintsDescription();
		case CHOICE:
			return "remove " + getValue() + " health" + getConstraintsDescription();
		case MORE:
			return "remove " + getValue() + " more health";
		case RANDOM:
			return "remove " + getValue() + " health from a random target" + getConstraintsDescription();
		case YOU:
			return "lose " + getValue() + " health";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseHealth(getValue());
	}

}
