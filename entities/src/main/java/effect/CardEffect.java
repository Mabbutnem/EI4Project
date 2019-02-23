package effect;

import game.Game;
import spell.ISpell;
import target.Target;
import zone.ZonePick;
import zone.ZoneType;
import boardelement.Character;

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
	public void prepare(ISpell spell) {
		// TODO Auto-generated method stub
	}



	@Override
	protected void applyOn(Character character) {
		// TODO Auto-generated method stub
		
	}

}
