package effect;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

public class DiscardEffect extends CardEffect {

	public DiscardEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource) {
		super(target, value, zoneSource, pickSource, ZoneType.DISCARD, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription()
	{
		return "discard " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

}
