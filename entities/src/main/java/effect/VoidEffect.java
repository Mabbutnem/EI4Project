package effect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("voidEffect")
@JsonIgnoreProperties({ "zoneDest", "pickDest" })
public class VoidEffect extends CardEffect {

	public VoidEffect() { 
		super();
		zoneDest = ZoneType.VOID;
		pickDest = ZonePick.DEFAULT;
	}
	
	public VoidEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource) {
		super(target, value, zoneSource, pickSource, ZoneType.VOID, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription()
	{
		return "Void " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

}
