package condition;

import game.Game;

public class AlwaysFalseCondition implements ICondition
{

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean test(Game game)
	{
		return false;
	}

}
