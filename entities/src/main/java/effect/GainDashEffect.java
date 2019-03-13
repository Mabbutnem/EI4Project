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
			return "give " + getValue() + " dash to all targets " + getConstraintsDescription();
		case CHOICE:
			return "give " + getValue() + " dash " + getConstraintsDescription();
		case MORE:
			return "give " + getValue() + " more dash";
		case RANDOM:
			return "give " + getValue() + " dash to a random target" + getConstraintsDescription();
		case YOU:
			return "gain " + getValue() + " dash";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainDash(getValue());
	}

}
