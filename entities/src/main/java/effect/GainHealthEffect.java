package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("gainHealthEffect")
public class GainHealthEffect extends OneValueEffect {

	public GainHealthEffect() { 
		super();
	}
	
	public GainHealthEffect(Target target, int value) {
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
			return "Give " + getValue() + " health to all targets" + getConstraintsDescription();
		case CHOICE:
			return "Give " + getValue() + " health" + getConstraintsDescription();
		case MORE:
			return "Give " + getValue() + " more health";
		case RANDOM:
			return "Give " + getValue() + " health to a random target" + getConstraintsDescription();
		case YOU:
			return "Gain " + getValue() + " health";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainHealth(getValue());
	}

}
