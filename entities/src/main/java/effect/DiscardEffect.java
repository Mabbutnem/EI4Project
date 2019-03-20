package effect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("discardEffect")
@JsonIgnoreProperties({ "zoneDest", "pickDest" })
public class DiscardEffect extends CardEffect
{

	public DiscardEffect() { 
		super();
		zoneDest = ZoneType.DISCARD;
		pickDest = ZonePick.DEFAULT;
	}
	
	public DiscardEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource)
	{
		super(target, value, zoneSource, pickSource, ZoneType.DISCARD, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription()
	{
		return "Discard " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

}
