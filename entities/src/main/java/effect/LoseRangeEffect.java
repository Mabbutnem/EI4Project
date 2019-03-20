package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.HigherRangeCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("loseRangeEffect")
public class LoseRangeEffect extends OneValueEffect {

	public LoseRangeEffect() { 
		super();
	}
	
	public LoseRangeEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new HigherRangeCondition(getValue());
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "Remove " + getValue() + " range from all targets" + getConstraintsDescription();
		case CHOICE:
			return "Remove " + getValue() + " range" + getConstraintsDescription();
		case MORE:
			return "Remove " + getValue() + " more range";
		case RANDOM:
			return "Remove " + getValue() + " range from a random target" + getConstraintsDescription();
		case YOU:
			return "Lose " + getValue() + " range";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseRange(getValue());
	}

}
