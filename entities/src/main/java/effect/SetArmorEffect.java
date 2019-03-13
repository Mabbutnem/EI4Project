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
			return "set all targets’ armor to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "set armor to " + getValue() + getConstraintsDescription();
		case MORE:
			return "set " + getValue() + " more armor";
		case RANDOM:
			return "set a random target’s armor to " + getValue() + getConstraintsDescription();
		case YOU:
			return "set your armor to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.setArmor(getValue());
	}

}
