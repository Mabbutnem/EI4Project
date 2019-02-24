package spell;

import game.Game;

import java.util.LinkedList;
import java.util.List;

import boardelement.Character;
import effect.IApplicableEffect;
import effect.IEffect;
import effect.Word;

public abstract class Spell implements ISpell
{
	protected String name;
	protected IEffect[] effects;
	protected String description;
	protected Character choosenTarget;
	protected List<Word> words;
	
	
	
	public Spell(String name, IEffect[] effects)
	{
		this.name = name;
		this.effects = effects;
		setDescription();
		choosenTarget = null;
		words = new LinkedList<>();
	}
	


	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public IEffect[] getEffects() {
		return effects;
	}
	
	private void setDescription()
	{
		StringBuilder bld = new StringBuilder();
		
		for(IEffect e : effects)
		{
			bld.append(e.getDescription()).append(" \n");
		}
		
		description = bld.toString();
	}
	
	@Override
	public String getDescription()
	{
		return description;
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
	
	@Override
	public boolean containsWord(Word word)
	{
		return words.contains(word);
	}

	@Override
	public void addWord(Word word)
	{
		if(!words.contains(word))
		{
			words.add(word);
		}
	}
	
	protected void prepare(Game game)
	{
		for(IEffect e : effects)
		{
			e.prepare(game, this);
		}
	}
	
	protected void clean()
	{
		for(IEffect e : effects)
		{
			e.clean();
		}
		choosenTarget = null;
		words.clear();
	}
	
	@Override
	public void cast(Game game)
	{
		prepare(game);

		for(IEffect e : effects)
		{
			if(e instanceof IApplicableEffect)
			{
				((IApplicableEffect) e).apply(game, this);
			}
		}
		
		clean();
	}
}
