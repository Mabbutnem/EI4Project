package zone;

import spell.Card;

public class AutoRevealZone extends Zone
{

	public AutoRevealZone(Card[] cards, ZoneType zoneType, ZonePick defaultZonePick) {
		super(cards, zoneType, defaultZonePick);
	}
	
	
	
	@Override
	public void add(Card[] cards, ZonePick zonePick)
	{
		for(Card c : cards) { c.setRevealed(true);}
		super.add(cards, zonePick);
	}
	
	@Override
	public void shuffle()
	{
		super.shuffle();
		revealCards();
	}

}
