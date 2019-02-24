package effect;

import spell.ISpell;
import target.Target;
import zone.ZonePick;
import zone.ZoneType;
import boardelement.Character;
import boardelement.Wizard;
import condition.CardNumberCondition;
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
		return new CardNumberCondition(getValue(), zoneSource);
	}

}
