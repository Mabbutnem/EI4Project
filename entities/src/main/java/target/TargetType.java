package target;

import java.util.Locale;

public enum TargetType
{
	CHOICE,
	RANDOM,
	AREA,
	YOU,
	MORE;
	
	
	
	@Override
	public String toString()
	{
		return super.toString().toLowerCase(Locale.ROOT);
	}
}
