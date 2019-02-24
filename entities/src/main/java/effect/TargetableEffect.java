package effect;

import game.Game;
import listener.ITargetRequestListener;
import spell.ISpell;
import target.Target;

import com.google.common.base.Preconditions;

import boardelement.Character;

public abstract class TargetableEffect implements IApplicableEffect
{
	private static ITargetRequestListener targetRequestListener;
	
	private Target target;
	
	
	
	public TargetableEffect(Target target)
	{
		Preconditions.checkState(targetRequestListener != null, "targetRequestListener"
				+ " was not initialised (in static)");
		
		this.target = target;
	}



	public static void setTargetRequestListener(ITargetRequestListener targetRequestListener) {
		TargetableEffect.targetRequestListener = targetRequestListener;
	}

	

	public Target getTarget() {
		return target;
	}
	
	@Override
	public String getDescription() {
		// TODO
		return null;
	}
	
	protected abstract void applyOn(Character character, Game game, ISpell spell);
	protected void applyOn(Character[] characters, Game game, ISpell spell)
	{
		for(Character c : characters)
		{
			applyOn(c, game, spell);
		}
	}
	
	@Override
	public void apply(Game game, ISpell spell)
	{
		switch(target.getType())
		{
		case AREA:
			applyByArea(game, spell);
			break;
			
		case CHOICE:
			applyByChoice(game, spell);
			break;
			
		case MORE:
			break;
			
		case RANDOM:
			applyByRandom(game, spell);
			break;
			
		case YOU:
			applyByYou(game, spell);
			break;
			
		default:
			break;
		}
	}
	
	private void applyByArea(Game game, ISpell spell)
	{
		applyOn(game.getAllAvailableTargetForCurrentCharacter(target.getConstraints()), game, spell);
	}
	
	private void applyByChoice(Game game, ISpell spell)
	{
		if(spell.getChoosenTarget() == null && game.hasValidTargetForCurrentCharacter(target.getConstraints()))
		{
			do
			{
				spell.setChoosenTarget(targetRequestListener.chooseTarget(game));
			}
			while(!game.isValidTargetForCurrentCharacter(spell.getChoosenTarget(), target.getConstraints()));
		}
		if(spell.getChoosenTarget() != null)
		{
			applyOn(new Character[] {spell.getChoosenTarget()}, game, spell);
		}
	}
	
	private void applyByRandom(Game game, ISpell spell)
	{
		if(game.hasValidTargetForCurrentCharacter(target.getConstraints()))
		{
			applyOn(new Character[] {game.getRandomAvailableTargetForCurrentCharacter(target.getConstraints())}, game, spell);
		}
	}
	
	private void applyByYou(Game game, ISpell spell)
	{
		if(game.getCurrentCharacter() != null)
		{
			applyOn(new Character[] {game.getCurrentCharacter()}, game, spell);
		}
	}
	

}
