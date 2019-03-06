package effect;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

public class SetDashEffect extends OneValueEffect {

	public SetDashEffect(Target target, int value) {
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
			return "set all targets’ dash to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "set dash to " + getValue() + getConstraintsDescription();
		case MORE:
			return "set " + getValue() + " more dash";
		case RANDOM:
			return "set a random target’s dash to " + getValue() + getConstraintsDescription();
		case YOU:
			return "set your dash to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setDash(getValue());
	}

}
