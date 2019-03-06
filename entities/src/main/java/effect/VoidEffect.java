package effect;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

public class VoidEffect extends CardEffect {

	public VoidEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource) {
		super(target, value, zoneSource, pickSource, ZoneType.VOID, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription()
	{
		return "void " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

}
