package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("setMoveEffect")
public class SetMoveEffect extends OneValueEffect {

	public SetMoveEffect(Target target, int value) {
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
			return "set all targets’ move to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "set move to " + getValue() + getConstraintsDescription();
		case MORE:
			return "set " + getValue() + " move more";
		case RANDOM:
			return "set a random target’s move to " + getValue() + getConstraintsDescription();
		case YOU:
			return "set your move to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setMove(getValue());
	}

}
