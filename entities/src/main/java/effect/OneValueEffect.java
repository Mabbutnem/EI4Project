package effect;

import target.Target;

public abstract class OneValueEffect extends TargetableEffect
{
	private int value;
	private int moreValue;
	
	

	public OneValueEffect(Target target, int value) {
		super(target);
		this.value = value;
		moreValue = 0;
	}



	protected int getValue() {
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
