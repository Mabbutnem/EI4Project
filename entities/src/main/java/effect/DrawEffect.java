package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("drawEffect")
public class DrawEffect extends CardEffect
{

	public DrawEffect(Target target, int value)
	{
		super(target, value, ZoneType.DECK, ZonePick.TOP, ZoneType.HAND, ZonePick.DEFAULT);
	}
	
	
	
	@Override
	public String getDescription()
	{
		//TODO
		return null;
	}

}
