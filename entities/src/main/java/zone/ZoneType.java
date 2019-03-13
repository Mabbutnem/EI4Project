package zone;

import java.util.Locale;

public enum ZoneType
{
	DECK,
	HAND,
	DISCARD,
	BURN,
	VOID,
	BANISH;
	
	
	
	@Override
	public String toString()
	{
		return super.toString().toLowerCase(Locale.ROOT);
	}
	
	public String getDescription()
	{
		switch(this) 
		{
		case BANISH:
			return "the banish zone ";
		case BURN:
			return "the burn zone ";
		case DECK:
			return "the deck ";
		case DISCARD:
			return "the discard zone ";
		case HAND:
			return "the hand ";
		case VOID:
			return "the void zone ";
		default:
			return " ";
		}
	}
	
}
