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
			return "give " + getValue() + " armor to all targets" + getConstraintsDescription();
		case CHOICE:
			return "give " + getValue() + " armor" + getConstraintsDescription();
		case MORE:
			return "give " + getValue() + " more armor";
		case RANDOM:
			return "give " + getValue() + " armor to a random target" + getConstraintsDescription();
		case YOU:
			return "gain " + getValue() + " armor";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.gainArmor(getValue());
	}

}
