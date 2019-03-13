package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("freezeEffect")
public class FreezeEffect extends TargetableEffect {

	public FreezeEffect(Target target) {
		super(target);
	}

	@Override
	public ICondition matchingCondition() {
		return new TrueCondition();
	}

	@Override
	public String getDescription() {
		String str = "Freeze ";
		
		switch(getTarget().getType()) {
		case AREA:
			str += "all targets";
			break;
		case CHOICE:
			break;
		case MORE:
			break;
		case RANDOM:
			str += "random target";
			break;
		case YOU:
			str += "yourself";
			break;
		default:
			break;
		
		}
		return str;
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
		character.setFreeze(true);
	}

}
