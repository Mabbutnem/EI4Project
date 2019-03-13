package effect;

import boardelement.Character;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import target.Target;
import zone.ZonePick;
import zone.ZoneType;

public class PutAfterCastEffect extends TargetableEffect {

	private ZoneType zoneDest;
	private ZonePick pickDest;
	
	public PutAfterCastEffect(Target target, ZoneType zoneDest, ZonePick pickDest) {
		super(target);
		this.zoneDest = zoneDest;
		this.pickDest = pickDest;
	}

	@Override
	public ICondition matchingCondition() {
		return new TrueCondition();
	}

	@Override
	public String getDescription() {
		return "return it " + pickDest.getDescriptionDest() + zoneDest.getDescription();
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
		game.getCastZone().setCurrentZoneTypeDest(zoneDest);
		game.getCastZone().setCurrentZonePickDest(pickDest);
	}

}
