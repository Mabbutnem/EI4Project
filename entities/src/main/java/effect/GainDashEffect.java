package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("gainDashEffect")
public class GainDashEffect extends OneValueEffect {

	public GainDashEffect() { 
		super();
	}
	
	public GainDashEffect(Target target, int value) {
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
			return "Give " + getValue() + " dash to all targets" + getConstraintsDescription();
		case CHOICE:
			return "Give " + getValue() + " dash" + getConstraintsDescription();
		case MORE:
			return "Give " + getValue() + " more dash";
		case RANDOM:
			return "Give " + getValue() + " dash to a random target" + getConstraintsDescription();
		case YOU:
			return "Gain " + getValue() + " dash";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainDash(getValue());
	}

}
