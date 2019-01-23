package zone;

import spell.Card;

public class ZoneGroup
{
	
	public ZoneGroup(Card[] cards)
	{
		
	}
	
	public void transfer(ZoneType source, ZonePick sourcePick, ZoneType dest, ZonePick destPick, int nbCard) {}
	public void add(Card[] cards, ZoneType zoneType, ZonePick zonePick) {}
	public void add(Card[] cards, ZoneType zoneType) {}
	public Card[] remove(int nbCard, ZoneType zoneType, ZonePick zonePick) { return null;}
	public Card[] remove(int nbCard, ZoneType zoneType) { return null;}
	public Card[] getCards(ZoneType zoneType) { return null;}
	public void shuffle(ZoneType zoneType) {}
	public void moveCardToIndex(ZoneType zoneType, int sourceIndex, int destIndex) {}

	
}
