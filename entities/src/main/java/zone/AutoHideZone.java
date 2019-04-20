package zone;

import spell.Card;

public class AutoHideZone extends Zone
{

	public AutoHideZone()
	{
		super();
	}
	
	public AutoHideZone(Card[] cards, ZoneType zoneType, ZonePick defaultZonePick) {
		super(cards, zoneType, defaultZonePick);
	}
	
	
	
	@Override
	protected Card[] removeByChoice(int nbCard)
	{
		Card[] removedCards = super.removeByChoice(nbCard);
		shuffle();
		return removedCards;
	}

	@Override
	public void shuffle()
	{
		super.shuffle();
		hideCards();
	}
	
}
