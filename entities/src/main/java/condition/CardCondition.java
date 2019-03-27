package condition;

import com.google.common.base.Preconditions;

import zone.ZoneType;

public abstract class CardCondition extends OneValueCondition
{
	protected ZoneType zoneType;

	public CardCondition() {
		super();
	}
	
	public CardCondition(int value, ZoneType zoneType)
	{
		super(value);
		
		Preconditions.checkArgument(zoneType != null, "zoneType was null but expected not null");
		
		this.zoneType = zoneType;
	}

	

}
