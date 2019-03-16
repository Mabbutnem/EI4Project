package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import boardelement.Wizard;
import game.Game;
import spell.Card;
import spell.ISpell;
import target.Target;
import target.TargetType;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("castEffect")
public class CastEffect extends CardEffect
{

	public CastEffect() { 
		super();
	}
	
	public CastEffect(Target target, int value,
			ZoneType zoneSource, ZonePick pickSource, ZoneType zoneDest, ZonePick pickDest) {
		super(target, value, zoneSource, pickSource, zoneDest, pickDest);
	}
	
	
	
	@Override
	public String getDescription() {
		String desc = "";
		if(getTarget().getType() == TargetType.AREA) {
			desc += "all targets ";
		}
		desc += "play " + getValue() + " card" + (getValue() > 1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription() + "and return " + (getValue() >1? "them ":"it ")
				+ pickDest.getDescriptionDest()  + zoneDest.getDescription();
		return desc;
	}
	
	@Override
	protected void applyOn(Character character, Game game, ISpell spell)
	{
		if(character instanceof Wizard)
		{
			Card[] cards = ((Wizard) character).getZoneGroup().remove(getValue(), zoneSource, pickSource);
			
			for(Card c : cards)
			{
				game.getCastZone().add(c, (Wizard)character, zoneDest, pickDest);
			}
		}
	}

}
