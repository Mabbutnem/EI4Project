package spell;

import game.Game;

public abstract class Spell implements ISpell
{
	protected String name;
	


	public String getName()
	{
		return this.name;
	}
	
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
