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

	public SetRangeEffect() { 
		super();
	}
	
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
			return "Set all targets’ range to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "Set range to " + getValue() + getConstraintsDescription();
		case MORE:
			return "Set " + getValue() + " more range";
		case RANDOM:
			return "Set a random target’s range to " + getValue() + getConstraintsDescription();
		case YOU:
			return "Set your range to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainRange(getValue());
	}

}
