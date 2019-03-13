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
			return "remove " + getValue() + " dash from all targets" + getConstraintsDescription();
		case CHOICE:
			return "remove " + getValue() + " dash" + getConstraintsDescription();
		case MORE:
			return "remove " + getValue() + " more dash";
		case RANDOM:
			return "remove " + getValue() + " dash from a random target" + getConstraintsDescription();
		case YOU:
			return "lose " + getValue() + " dash";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseDash(getValue());
	}

}
