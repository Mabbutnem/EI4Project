package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.HigherArmorCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("loseArmorEffect")
public class LoseArmorEffect extends OneValueEffect {

	public LoseArmorEffect() { 
		super();
	}
	
	public LoseArmorEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new HigherArmorCondition(getValue());
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "Remove " + getValue() + " armor from all targets" + getConstraintsDescription();
		case CHOICE:
			return "Remove " + getValue() + " armor" + getConstraintsDescription();
		case MORE:
			return "Remove " + getValue() + " more armor";
		case RANDOM:
			return "Remove " + getValue() + " armor from a random target" + getConstraintsDescription();
		case YOU:
			return "Lose " + getValue() + " armor";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.loseArmor(getValue());
	}

}
