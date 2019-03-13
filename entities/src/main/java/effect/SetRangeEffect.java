package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("setRangeEffect")
public class SetRangeEffect extends OneValueEffect {

	public SetRangeEffect(Target target, int value) {
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
			return "set all targets’ range to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "set range to " + getValue() + getConstraintsDescription();
		case MORE:
			return "set " + getValue() + " more range";
		case RANDOM:
			return "set a random target’s range to " + getValue() + getConstraintsDescription();
		case YOU:
			return "set your range to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainRange(getValue());
	}

}
