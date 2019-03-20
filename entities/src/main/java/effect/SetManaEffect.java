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

	public SetManaEffect() { 
		super();
	}
	
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
			return "Set all targets’ mana to " + getValue() + getConstraintsDescription();
		case CHOICE:
			return "Set mana to " + getValue() + getConstraintsDescription();
		case MORE:
			return "Set " + getValue() + " more mana";
		case RANDOM:
			return "Set a random target’s mana to " + getValue() + getConstraintsDescription();
		case YOU:
			return "Set your mana to " + getValue();
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
