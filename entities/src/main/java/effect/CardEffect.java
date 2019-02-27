package effect;

import spell.ISpell;
import target.Target;
import zone.ZonePick;
import zone.ZoneType;

import com.google.common.base.Preconditions;

import boardelement.Character;
import boardelement.Wizard;
import condition.HigherOrEqualCardCondition;
import condition.ICondition;
import game.Game;

public class CardEffect extends OneValueEffect
{
	private ZoneType zoneSource;
	private ZonePick pickSource;
	private ZoneType zoneDest;
	private ZonePick pickDest;

	
	
	public CardEffect(Target target, int value,
			ZoneType zoneSource, ZonePick pickSource, ZoneType zoneDest, ZonePick pickDest)
	{
		super(target, value);
		
		Preconditions.checkArgument(zoneSource != null, "zoneSource was null but expected not null");
		Preconditions.checkArgument(pickSource != null, "pickSource was null but expected not null");
		Preconditions.checkArgument(zoneDest != null, "zoneDest was null but expected not null");
		Preconditions.checkArgument(pickDest != null, "pickDest was null but expected not null");
		Preconditions.checkArgument(pickDest != ZonePick.CHOICE, "pickDest was choice but expected not choice");
		
		this.zoneSource = zoneSource;
		this.pickSource = pickSource;
		this.zoneDest = zoneDest;
		this.pickDest = pickDest;
	}



	@Override 
	public String getDescription()
	{
		//TODO
		return null;
	}
	
	@Override
	protected void applyOn(Character character, Game game, ISpell spell)
	{
		if(character instanceof Wizard)
		{
			((Wizard) character).getZoneGroup().transfer(zoneSource, pickSource, zoneDest, pickDest, getValue());
		}
	}
	
	@Override
	public ICondition matchingCondition()
	{
		return new HigherOrEqualCardCondition(getValue(), zoneSource);
	}

}
