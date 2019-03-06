package effect;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

public class SetHealthEffect extends OneValueEffect {

	public SetHealthEffect(Target target, int value) {
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
			return "set all targets’ health to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "set health to " + getValue() + getConstraintsDescription();
		case MORE:
			return "set " + getValue() + " more health";
		case RANDOM:
			return "set a random target’s health to " + getValue() + getConstraintsDescription();
		case YOU:
			return "set your health to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setHealth(getValue());
	}

}
