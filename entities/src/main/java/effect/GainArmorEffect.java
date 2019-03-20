package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("gainArmorEffect")
public class GainArmorEffect extends OneValueEffect {

	public GainArmorEffect() { 
		super();
	}
	
	public GainArmorEffect(Target target, int value) {
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
			return "Give " + getValue() + " armor to all targets" + getConstraintsDescription();
		case CHOICE:
			return "Give " + getValue() + " armor" + getConstraintsDescription();
		case MORE:
			return "Give " + getValue() + " more armor";
		case RANDOM:
			return "Give " + getValue() + " armor to a random target" + getConstraintsDescription();
		case YOU:
			return "Gain " + getValue() + " armor";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainArmor(getValue());
	}

}
