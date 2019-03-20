package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("loseHealthEffect")
public class LoseHealthEffect extends OneValueEffect {

	public LoseHealthEffect() { 
		super();
	}
	
	public LoseHealthEffect(Target target, int value) {
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
			return "Remove " + getValue() + " health from all targets" + getConstraintsDescription();
		case CHOICE:
			return "Remove " + getValue() + " health" + getConstraintsDescription();
		case MORE:
			return "Remove " + getValue() + " more health";
		case RANDOM:
			return "Remove " + getValue() + " health from a random target" + getConstraintsDescription();
		case YOU:
			return "Lose " + getValue() + " health";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseHealth(getValue());
	}

}
