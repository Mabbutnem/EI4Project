package effect;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

public class TopDeckEffect extends CardEffect {

	public TopDeckEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource) {
		super(target, value, zoneSource, pickSource, ZoneType.DECK, ZonePick.TOP);
	}
	
	@Override
	public String getDescription()
	{
		return "topdeck " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

}
