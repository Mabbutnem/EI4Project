package effect;

import boardelement.Character;
import condition.HigherArmorCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;

public class LoseArmorEffect extends OneValueEffect {

	public LoseArmorEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new HigherArmorCondition(getValue());
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "remove " + getValue() + " armor from all targets" + getConstraintsDescription();
		case CHOICE:
			return "remove " + getValue() + " armor" + getConstraintsDescription();
		case MORE:
			return "remove " + getValue() + " more armor";
		case RANDOM:
			return "remove " + getValue() + " armor from a random target" + getConstraintsDescription();
		case YOU:
			return "lose " + getValue() + " armor";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseArmor(getValue());
	}

}
