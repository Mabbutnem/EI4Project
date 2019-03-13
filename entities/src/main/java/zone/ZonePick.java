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

	public String getDescriptionSource()
	{
		switch(this)
		{
		case BOTTOM:
			return "from the bottom of ";
		case CHOICE:
			return "from a chosen place in ";
		case DEFAULT:
			return " ";
		case RANDOM:
			return "from a random place in ";
		case TOP:
			return "from the top of ";
		default:
			return " ";
		}
	}
	
	public String getDescriptionDest()
	{
		switch(this)
		{
		case BOTTOM:
			return "to the bottom of ";
		case CHOICE:
			return " ";
		case DEFAULT:
			return "in ";
		case RANDOM:
			return "to a random place in ";
		case TOP:
			return "to the top of "; 
		default:
			return "";
		}
	}
}
