package effect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("topDeckEffect")
@JsonIgnoreProperties({ "zoneDest", "pickDest" })
public class TopDeckEffect extends CardEffect {

	public TopDeckEffect() {
		super();
		zoneDest = ZoneType.DECK;
		pickDest = ZonePick.TOP;
	}
	
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
