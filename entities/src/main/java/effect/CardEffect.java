package effect;

import spell.ISpell;
import target.Target;
import target.TargetType;
import zone.ZonePick;
import zone.ZoneType;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import boardelement.Character;
import boardelement.Wizard;
import condition.HigherCardCondition;
import condition.ICondition;
import game.Game;

@JsonTypeName("cardEffect")
public class CardEffect extends OneValueEffect
{
	protected ZoneType zoneSource;
	protected ZonePick pickSource;
	protected ZoneType zoneDest;
	protected ZonePick pickDest;

	
	
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
	public ICondition matchingCondition()
	{
		return new HigherCardCondition(getValue(), zoneSource);
	}

	@Override 
	public String getDescription()
	{
		String desc = "";
		if(getTarget().getType() == TargetType.AREA) {
			desc += "all targets ";
		}
		desc += "put " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription() 
				+ pickDest.getDescriptionDest()  + zoneDest.getDescription();
		return desc;
	}
	
	@Override
	protected void applyOn(Character character, Game game, ISpell spell)
	{
		if(character instanceof Wizard)
		{
			((Wizard) character).getZoneGroup().transfer(zoneSource, pickSource, zoneDest, pickDest, getValue());
		}
	}

}
