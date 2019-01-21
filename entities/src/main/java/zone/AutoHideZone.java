package zone;

import spell.Card;

public class AutoHideZone extends Zone
{

	public AutoHideZone(Card[] cards, ZoneType zoneType, ZonePick defaultZonePick) {
		super(cards, zoneType, defaultZonePick);
	}
	
	
	
	@Override
	public void add(Card[] cards, ZonePick zonePick)
	{
		for(Card c : cards) { c.setRevealed(false);}
		super.add(cards, zonePick);
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
