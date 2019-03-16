package effect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("burnEffect")
@JsonIgnoreProperties({ "zoneDest", "pickDest" })
public class BurnEffect extends CardEffect
{

	public BurnEffect() { 
		super();
		zoneDest = ZoneType.BURN;
		pickDest = ZonePick.DEFAULT;
	}
	
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
