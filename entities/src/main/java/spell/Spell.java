package spell;

import game.Game;
import target.TargetType;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import boardelement.Character;
import effect.ConditionalEffect;
import effect.IApplicableEffect;
import effect.IEffect;
import effect.OneValueEffect;
import effect.Word;

public abstract class Spell implements ISpell
{
	protected String name;
	protected IEffect[] effects;
	@JsonIgnore
	protected String description;
	@JsonIgnore
	protected Character choosenTarget;
	@JsonIgnore
	protected List<Word> words;
	
	
	
	public Spell() {
		description = "";
		choosenTarget = null;
		words = new LinkedList<>();
	}
	
	public Spell(String name, IEffect[] effects)
	{
		Preconditions.checkArgument(name.length() > 0, "name was empty but expected not empty");
		
		verifyMoreTargetableEffectIsNotAlone(effects, null);
		
		this.name = name;
		this.effects = effects;
		setDescription();
		choosenTarget = null;
		words = new LinkedList<>();
	}
	
	// !!! recursive function !!!
	private void verifyMoreTargetableEffectIsNotAlone(IEffect[] effects, List<OneValueEffect> prevEffects)
	{
		List<OneValueEffect> myList = new LinkedList<>();
		if(prevEffects != null) { myList.addAll(prevEffects); }
		
		for(IEffect e : effects)
		{
			if(e instanceof ConditionalEffect)
			{
				verifyMoreTargetableEffectIsNotAlone(((ConditionalEffect) e).getEffects(), myList);
			}
			
			if(e instanceof OneValueEffect &&
				((OneValueEffect) e).getTarget().getType() != TargetType.MORE)
			{
				myList.add((OneValueEffect) e);
			}
			
			if(e instanceof OneValueEffect &&
				((OneValueEffect) e).getTarget().getType() == TargetType.MORE &&
				myList.stream().noneMatch(ove -> ove.getClass() == e.getClass()))
			{
				throw new IllegalArgumentException("No effect of the same type found before one (ore more) MORE target type effect");
			}
		}
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
	
	@JsonProperty("effects")
	public void setEffects(IEffect[] effects) {
		this.effects = effects;
		setDescription();
	}
	
	public void setDescription()
	{
		StringBuilder bld = new StringBuilder();
		
		for(IEffect e : effects)
		{
			bld.append(e.getDescription()).append(".\n");
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
