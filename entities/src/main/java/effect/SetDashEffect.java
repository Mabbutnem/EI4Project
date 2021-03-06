package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("setDashEffect")
public class SetDashEffect extends OneValueEffect {

	public SetDashEffect() { 
		super();
	}
	
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
			return "Set all targets� dash to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "Set dash to " + getValue() + getConstraintsDescription();
		case MORE:
			return "Set " + getValue() + " more dash";
		case RANDOM:
			return "Set a random target�s dash to " + getValue() + getConstraintsDescription();
		case YOU:
			return "Set your dash to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setDash(getValue());
	}

}
