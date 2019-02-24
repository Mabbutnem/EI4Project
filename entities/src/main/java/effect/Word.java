package effect;

import java.util.Locale;

public enum Word
{
	LIFELINK,
	PIERCE,
	ACID,
	WATER;
	
	
	
	@Override
	public String toString()
	{
		return super.toString().toLowerCase(Locale.ROOT);
	}
}
