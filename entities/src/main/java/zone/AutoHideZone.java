package zone;

import spell.Card;

public class AutoHideZone extends Zone
{

	public AutoHideZone(Card[] cards, ZoneType zoneType, ZonePick defaultZonePick) {
		super(cards, zoneType, defaultZonePick);
		hideCards();
	}

}
