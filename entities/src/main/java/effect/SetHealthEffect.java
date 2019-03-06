package effect;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;
import target.TargetType;

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
		String desc = "set ";
		
		switch(getTarget().getType())
		{
		case AREA:
			desc += "all targets' ";
			break;
		case CHOICE:
			desc += "";
			break;
		case MORE:
			desc += "";
			break;
		case RANDOM:
			desc += "a random target's ";
			break;
		case YOU:
			desc += "your ";
			break;
		default:
			break;
		}
		
		desc += "health to " + getValue() + getConstraintsDescription();
		
		return desc;
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setHealth(getValue());
	}

}
