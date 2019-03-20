package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;

@JsonTypeName("convertArmorToHealthEffect")
public class ConvertArmorToHealthEffect extends TargetableEffect {

	public ConvertArmorToHealthEffect() {
		super();
	}
	
	public ConvertArmorToHealthEffect(Target target) {
		super(target);
	}
	
	@Override
	public ICondition matchingCondition() {
		return  new TrueCondition();
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "all targets convert their armor to health" + getConstraintsDescription();
		case CHOICE:
			return "convert the armor to health" + getConstraintsDescription();
		case MORE:
			return "";
		case RANDOM:
			return "a random target convert his armor to health" + getConstraintsDescription();
		case YOU:
			return "convert your armor to health";
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
		character.convertArmorToHealth();
	}



}
