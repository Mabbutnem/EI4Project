package effect;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;
import target.TargetType;

public class GainHealthEffect extends OneValueEffect {

	public GainHealthEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new TrueCondition();
	}

	@Override
	public String getDescription() {
		String desc = getTarget().getType() == TargetType.YOU ? "Gain " : "Give ";
		desc += getValue() + "health";
		switch(getTarget().getType())
		{
		case AREA:
			desc += " to all targets";
			break;
		case CHOICE:
			break;
		case MORE:
			break;
		case RANDOM:
			desc += " to a random target";
			break;
		case YOU:
			break;			
		}
		desc += getConstraintsDescription();
		return desc;
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainHealth(getValue());
	}

}
