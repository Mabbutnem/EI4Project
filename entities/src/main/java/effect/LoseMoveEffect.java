package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.HigherMoveCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("loseMoveEffect")
public class LoseMoveEffect extends OneValueEffect {

	public LoseMoveEffect() { 
		super();
	}
	
	public LoseMoveEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new HigherMoveCondition(getValue());
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "Remove " + getValue() + " move" + (getValue() > 1 ? "s " : " ") + "from all targets" + getConstraintsDescription();
		case CHOICE:
			return "Remove " + getValue() + " move" + (getValue() > 1 ? "s " : " ") + getConstraintsDescription();
		case MORE:
			return "Remove " + getValue() + " more move" + (getValue() > 1 ? "s " : " ");
		case RANDOM:
			return "Remove " + getValue() + " move" + (getValue() > 1 ? "s " : " ") + "from a random target" + getConstraintsDescription();
		case YOU:
			return "Lose " + getValue() + " move" + (getValue() > 1 ? "s " : " ");
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseMove(getValue());
	}

}
