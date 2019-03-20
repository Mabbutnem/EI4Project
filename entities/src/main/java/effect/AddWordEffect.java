package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("addWordEffect")
public class AddWordEffect extends TargetableEffect
{
	private Word word;
	
	
	
	public AddWordEffect() { 
		super();
	}
	
	public AddWordEffect(Target target, Word w) {
		super(target);
		word = w;
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
			return "Give " + word.name() + " to all targets" + getConstraintsDescription();
		case CHOICE:
			return "Give " + word.name() + getConstraintsDescription();
		case MORE:
			return "";
		case RANDOM:
			return "Give " + word.name() + " to a random target" + getConstraintsDescription();
		case YOU:
			return "Gain " + word.name();
		default:
			return "";
		}
	}

	@Override
	public void prepare(Game game, ISpell spell) {
		//Must empty
	}

	@Override
	public void clean() {
		//Must empty
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		character.addWord(word);
	}

}
