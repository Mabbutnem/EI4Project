package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.HigherDashCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("loseDashEffect")
public class LoseDashEffect extends OneValueEffect {

	public LoseDashEffect() { 
		super();
	}
	
	public LoseDashEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new HigherDashCondition(getValue());
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "Remove " + getValue() + " dash from all targets" + getConstraintsDescription();
		case CHOICE:
			return "Remove " + getValue() + " dash" + getConstraintsDescription();
		case MORE:
			return "Remove " + getValue() + " more dash";
		case RANDOM:
			return "Remove " + getValue() + " dash from a random target" + getConstraintsDescription();
		case YOU:
			return "Lose " + getValue() + " dash";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseDash(getValue());
	}

}
