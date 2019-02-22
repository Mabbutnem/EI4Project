package spell;

import game.Game;

public abstract class Spell implements ISpell
{
	protected String name;
	protected Character choosenTarget;
	


	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getDescription()
	{
		//TODO
		return null;
	}
	
	@Override
	public void setChoosenTarget(Character choosenTarget)
	{
		this.choosenTarget = choosenTarget;
	}
	
	protected void prepare()
	{
		
	}
	
	protected void clean()
	{
		
	}
	
	@Override
	public void cast(Game game)
	{
		prepare();
		//TODO
		clean();
	}
}
