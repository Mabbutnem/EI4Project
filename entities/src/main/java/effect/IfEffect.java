package effect;

import condition.ICondition;
import game.Game;
import spell.ISpell;

public class IfEffect extends ConditionalEffect
{
	private ICondition condition;
	
	

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepare(Game game, ISpell spell)
	{
		willApply = condition.test(game);
		super.prepare(game, spell);
	}

}
