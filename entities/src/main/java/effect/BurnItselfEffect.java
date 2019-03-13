package effect;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

public class BurnItselfEffect extends PutAfterCastEffect {

	public BurnItselfEffect(Target target) {
		super(target, ZoneType.BURN, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription() {
		return "burn itself";
	}

}
