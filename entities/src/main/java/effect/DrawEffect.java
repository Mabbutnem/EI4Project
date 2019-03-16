package effect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("drawEffect")
@JsonIgnoreProperties({ "zoneSource", "pickSource", "zoneDest", "pickDest" })
public class DrawEffect extends CardEffect
{

	public DrawEffect() { 
		super();
		zoneSource = ZoneType.DECK;
		pickSource = ZonePick.TOP;
		zoneDest = ZoneType.HAND;
		pickDest = ZonePick.DEFAULT;
	}
	
	public DrawEffect(Target target, int value)
	{
		super(target, value, ZoneType.DECK, ZonePick.TOP, ZoneType.HAND, ZonePick.DEFAULT);
	}
	
	
	
	@Override
	public String getDescription()
	{
		return "draw " + getValue() + " card" + (getValue()>1? "s":"");
	}

}
