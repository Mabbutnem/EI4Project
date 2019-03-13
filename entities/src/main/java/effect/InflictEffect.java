package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import boardelement.Monster;
import boardelement.Wizard;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("inflictEffect")
public class InflictEffect extends OneValueEffect {

	public InflictEffect(Target target, int value) {
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
			return "inflict " + getValue() + " damage" + (getValue() > 1 ? "s " : " ") +  "to all targets " + getConstraintsDescription();
		case CHOICE:
			return "inflict " + getValue() + " damage" + (getValue() > 1 ? "s " : " ") + getConstraintsDescription();
		case MORE:
			return "inflict " + getValue() + " more damage" + (getValue() > 1 ? "s " : " ") ;
		case RANDOM:
			return "inflict " + getValue() + " damage" + (getValue() > 1 ? "s " : " ") + "to a random target" + getConstraintsDescription();
		case YOU:
			return "inflict " + getValue() + " damage" + (getValue() > 1 ? "s " : " ") + "to you";
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell)
	{
		int actualDamage = 0;
		
		if(containsWord(Word.FLOW, character, spell) &&
			sameTeam(game.getCurrentCharacter(), character))
		{
			character.gainHealth(getValue());
		}
		else if(containsWord(Word.PIERCE, character, spell)) {
			actualDamage = character.inflictDirectDamage(getValue());
		}
		else if(containsWord(Word.ACID, character, spell)) {
			actualDamage = character.inflictAcidDamage(getValue());
		}
		else {
			actualDamage = character.inflictDamage(getValue());
		}
		
		if(containsWord(Word.LIFELINK, character, spell)) {
			game.getCurrentCharacter().gainHealth(actualDamage);
		}
		
		if(containsWord(Word.POISON, character, spell)) {
			character.loseHealth(actualDamage);
		}
	}
	
	private boolean containsWord(Word word, Character character, ISpell spell)
	{
		return character.containsWord(word) || spell.containsWord(word);
	}
	
	private boolean sameTeam(Character character1, Character character2)
	{
		return (character1 instanceof Wizard && character2 instanceof Wizard) ||
			(character1 instanceof Monster && character2 instanceof Monster);
	}

}
