package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import boardelement.Wizard;
import condition.HigherManaCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("loseManaEffect")
public class LoseManaEffect extends OneValueEffect {

	public LoseManaEffect(Target target, int value) {
		super(target, value);
	}

	@Override
	public ICondition matchingCondition() {
		return new HigherManaCondition(getValue());
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "remove " + getValue() + " mana from all targets" + getConstraintsDescription();
		case CHOICE:
			return "remove " + getValue() + " mana" + getConstraintsDescription();
		case MORE:
			return "remove " + getValue() + " more mana";
		case RANDOM:
			return "remove " + getValue() + " mana from a random target" + getConstraintsDescription();
		case YOU:
			return "lose " + getValue() + " mana";
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		if(character instanceof Wizard) {
			((Wizard) character).loseMana(getValue());
		}
	}

}
