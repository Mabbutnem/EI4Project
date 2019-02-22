package effect;

import target.TargetType;

public abstract class OneValueEffect extends TargetableEffect
{
	private int value;
	private int moreValue;
	
	

	public OneValueEffect(TargetType targetType, int value)
	{
		super(targetType);
		this.value = value;
		moreValue = 0;
	}



	public int getValue() {
		return value + moreValue;
	}

	public void addValue(int addedValue) {
		moreValue += addedValue;
	}
	
	@Override
	public void clean()
	{
		//TODO
		moreValue = 0;
	}

}
