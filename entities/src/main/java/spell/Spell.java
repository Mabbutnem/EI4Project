package spell;

import game.Game;

public abstract class Spell implements ISpell
{
	protected void prepare()
	{
		
	}
	
	protected void clean()
	{
		
	}
	
	public void cast(Game game)
	{
		prepare();
		//TODO CAST
		clean();
	}
}
