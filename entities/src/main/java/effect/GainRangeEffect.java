package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("gainRangeEffect")
public class GainRangeEffect extends OneValueEffect {

	public GainRangeEffect() { 
		super();
	}
	
	public GainRangeEffect(Target target, int value) {
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
			return "Give " + getValue() + " range to all targets" + getConstraintsDescription();
		case CHOICE:
			return "Give " + getValue() + " range" + getConstraintsDescription();
		case MORE:
			return "Give " + getValue() + " more range";
		case RANDOM:
			return "Give " + getValue() + " range to a random target" + getConstraintsDescription();
		case YOU:
			return "Gain " + getValue() + " range";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainRange(getValue());
	}

}
