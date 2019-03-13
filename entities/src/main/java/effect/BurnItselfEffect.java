package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("burnItselfEffect")
public class BurnItselfEffect extends PutAfterCastEffect {

	public BurnItselfEffect() { 
		super();
	}
	
	public BurnItselfEffect(Target target) {
		super(target, ZoneType.BURN, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription() {
		return "burn itself";
	}

}
