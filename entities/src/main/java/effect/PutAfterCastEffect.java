package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.ISpell;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("putAfterCastEffect")
public class PutAfterCastEffect implements IApplicableEffect {

	protected ZoneType zoneDest;
	protected ZonePick pickDest;
	
	
	
	public PutAfterCastEffect() { 
		super();
	}
	
	public PutAfterCastEffect(ZoneType zoneDest, ZonePick pickDest) {
		super();
		this.zoneDest = zoneDest;
		this.pickDest = pickDest;
	}

	
	
	@Override
	public ICondition matchingCondition() {
		return new TrueCondition();
	}

	@Override
	public String getDescription() {
		return "Return it " + pickDest.getDescriptionDest() + zoneDest.getDescription();
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
	public void apply(Game game, ISpell spell) {
		game.getCastZone().setCurrentZoneTypeDest(zoneDest);
		game.getCastZone().setCurrentZonePickDest(pickDest);
	}

}
