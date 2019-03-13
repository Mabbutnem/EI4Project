package effect;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;
import target.TargetType;

@JsonTypeName("pushEffect")
public class PushEffect extends OneValueEffect {

	public PushEffect() { 
		super();
	}
	
	public PushEffect(Target target, int value) {
		super(target, value);
		Preconditions.checkArgument(target.getType() != TargetType.YOU, "TargetType can't be YOU");
	}

	@Override
	public ICondition matchingCondition() {
		return new TrueCondition();
	}

	@Override
	public String getDescription() {
		String str = "Push " + getValue();
		switch(getTarget().getType()){
		case CHOICE:
			break;
		case RANDOM:
			str += " randomly";
			break;
		case AREA:
			str += " all targets";
			break;
		case MORE:
			str += "more";
			break;
		case YOU:
			break;
		default:
			break;
		}
		return str;
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		//Must empty
	}

	@Override
	protected void applyOn(Character[] characters, Game game, ISpell spell) {
		List<Character> charactersList = Arrays.asList(characters);
		charactersList.removeIf(c -> !c.isAlive());
		
		game.push(game.getCurrentCharacter(), 
				charactersList.toArray(new Character[0]), 
				getValue());
	}

}
