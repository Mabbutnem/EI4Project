package zone;

import spell.Card;

public class AutoRevealZone extends Zone
{

	public AutoRevealZone(Card[] cards, ZoneType zoneType, ZonePick defaultZonePick) {
		super(cards, zoneType, defaultZonePick);
		revealCards();
	}

}
