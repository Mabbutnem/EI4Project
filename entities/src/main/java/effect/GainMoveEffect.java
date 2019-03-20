package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("gainMoveEffect")
public class GainMoveEffect extends OneValueEffect {

	public GainMoveEffect() { 
		super();
	}
	
	public GainMoveEffect(Target target, int value) {
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
			return "Give " + getValue() + " move" + (getValue() > 1 ? "s " : " ") +  "to all targets" + getConstraintsDescription();
		case CHOICE:
			return "Give " + getValue() + " move" + (getValue() > 1 ? "s" : "") + getConstraintsDescription();
		case MORE:
			return "Give " + getValue() + " more move" + (getValue() > 1 ? "s" : "") ;
		case RANDOM:
			return "Give " + getValue() + " move" + (getValue() > 1 ? "s " : " ") + "to a random target" + getConstraintsDescription();
		case YOU:
			return "Gain " + getValue() + " move" + (getValue() > 1 ? "s " : " ");
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainMove(getValue());
	}

}
