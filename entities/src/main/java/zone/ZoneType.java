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
	
}
