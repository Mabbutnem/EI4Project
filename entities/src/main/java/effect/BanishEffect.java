package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("banishEffect")
public class BanishEffect extends CardEffect {

	public BanishEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource) {
		super(target, value, zoneSource, pickSource, ZoneType.BANISH, ZonePick.DEFAULT);
	}
	
	@Override
	public String getDescription()
	{
		return "banish " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

}
