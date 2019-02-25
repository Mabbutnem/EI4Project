package condition;

import com.google.common.base.Preconditions;

public abstract class OneValueCondition implements ICondition
{
	protected int value;
	
	
	
	public OneValueCondition(int value)
	{
		Preconditions.checkArgument(value >= 0, "value was %s but expected positive", value);
		
		this.value = value;
	}
	
	

}
