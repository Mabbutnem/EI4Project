package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("setArmorEffect")
public class SetArmorEffect extends OneValueEffect {

	public SetArmorEffect() { 
		super();
	}
	
	public SetArmorEffect(Target target, int value) {
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
			return "Set all targets’ armor to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "Set armor to " + getValue() + getConstraintsDescription();
		case MORE:
			return "Set " + getValue() + " more armor";
		case RANDOM:
			return "Set a random target’s armor to " + getValue() + getConstraintsDescription();
		case YOU:
			return "Set your armor to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setArmor(getValue());
	}

}
