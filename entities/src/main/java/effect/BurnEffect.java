package effect;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

public class BurnEffect extends CardEffect
{

	public BurnEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource)
	{
		super(target, value, zoneSource, pickSource, ZoneType.BURN, ZonePick.DEFAULT);
	}
	
	
	
	@Override
	public String getDescription()
	{
		return "burn " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

}
