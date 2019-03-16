package effect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("burnItselfEffect")
@JsonIgnoreProperties({ "zoneDest", "pickDest" })
public class BurnItselfEffect extends PutAfterCastEffect {
	
	public BurnItselfEffect() {
		super(ZoneType.BURN, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription() {
		return "burn itself";
	}

}
