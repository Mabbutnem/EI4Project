package zone;

import java.util.Locale;

public enum ZonePick
{
	TOP,
	BOTTOM,
	CHOICE,
	RANDOM,
	DEFAULT;
	
	
	
	@Override
	public String toString()
	{
		return super.toString().toLowerCase(Locale.ROOT);
	}

}
