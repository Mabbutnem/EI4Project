package effect;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

public class GainRangeEffect extends OneValueEffect {

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
			return "give " + getValue() + " range to all targets " + getConstraintsDescription();
		case CHOICE:
			return "give " + getValue() + " range " + getConstraintsDescription();
		case MORE:
			return "give " + getValue() + " more range";
		case RANDOM:
			return "give " + getValue() + " range to a random target" + getConstraintsDescription();
		case YOU:
			return "gain " + getValue() + " range";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainRange(getValue());
	}

}
