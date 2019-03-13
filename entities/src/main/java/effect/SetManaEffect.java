package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import boardelement.Wizard;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("setManaEffect")
public class SetManaEffect extends OneValueEffect {

	public SetManaEffect(Target target, int value) {
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
			return "set all targets’ mana to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "set mana to " + getValue() + getConstraintsDescription();
		case MORE:
			return "set " + getValue() + " more mana";
		case RANDOM:
			return "set a random target’s mana to " + getValue() + getConstraintsDescription();
		case YOU:
			return "set your mana to " + getValue();
		default:
			return "";
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		if(character instanceof Wizard) {
			((Wizard) character).setMana(getValue());
		}
	}

}
