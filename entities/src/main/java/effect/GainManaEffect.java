package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import boardelement.Wizard;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("gainManaEffect")
public class GainManaEffect extends OneValueEffect {

	public GainManaEffect() { 
		super();
	}
	
	public GainManaEffect(Target target, int value) {
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
			return "Give " + getValue() + " mana to all targets" + getConstraintsDescription();
		case CHOICE:
			return "Give " + getValue() + " mana" + getConstraintsDescription();
		case MORE:
			return "Give " + getValue() + " more mana";
		case RANDOM:
			return "Give " + getValue() + " mana to a random target" + getConstraintsDescription();
		case YOU:
			return "Gain " + getValue() + " mana";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		if(character instanceof Wizard) {
			((Wizard) character).gainMana(getValue());
		}
	}

}
