package spell;

import game.Game;
import boardelement.Character;

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
	public Character getChoosenTarget()
	{
		return choosenTarget;
	}
	
	@Override
	public void setChoosenTarget(Character choosenTarget)
	{
		this.choosenTarget = choosenTarget;
	}
	
	protected void prepare()
	{
		//TODO
	}
	
	protected void clean()
	{
		//TODO
		choosenTarget = null;
	}
	
	@Override
	public void cast(Game game)
	{
		prepare();
		//TODO
		clean();
	}
}
