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
			return "give " + getValue() + " move" + (getValue() > 1 ? "s " : " ") +  "to all targets " + getConstraintsDescription();
		case CHOICE:
			return "give " + getValue() + " move" + (getValue() > 1 ? "s " : " ") + getConstraintsDescription();
		case MORE:
			return "give " + getValue() + " more move" + (getValue() > 1 ? "s " : " ") ;
		case RANDOM:
			return "give " + getValue() + " move" + (getValue() > 1 ? "s " : " ") + "to a random target" + getConstraintsDescription();
		case YOU:
			return "gain " + getValue() + " move" + (getValue() > 1 ? "s " : " ");
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainMove(getValue());
	}

}
