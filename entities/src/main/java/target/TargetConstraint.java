package target;

import java.util.Locale;

public enum TargetConstraint
{
	NOTYOU,
	NOTALLY,
	NOTENEMY;
	
	
	
	@Override
	public String toString()
	{
		return super.toString().toLowerCase(Locale.ROOT).replaceFirst("^not", "not ");
	}
}
