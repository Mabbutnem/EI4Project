package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("setHealthEffect")
public class SetHealthEffect extends OneValueEffect {

	public SetHealthEffect() { 
		super();
	}
	
	public SetHealthEffect(Target target, int value) {
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
			return "Set all targets’ health to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "Set health to " + getValue() + getConstraintsDescription();
		case MORE:
			return "Set " + getValue() + " more health";
		case RANDOM:
			return "Set a random target’s health to " + getValue() + getConstraintsDescription();
		case YOU:
			return "Set your health to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setHealth(getValue());
	}

}
