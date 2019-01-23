package zone;

import spell.Card;

public class ZoneGroup
{
	private AutoHideZone deck;
	private AutoRevealZone hand;
	private AutoRevealZone discard;
	private AutoRevealZone burn;
	private Zone banish;
	private Zone voidZ; 
	
	public ZoneGroup(Card[] cards)
	{
		deck = new AutoHideZone(cards, ZoneType.DECK, ZonePick.TOP);
		hand = new AutoRevealZone(null, ZoneType.HAND, ZonePick.TOP);
		discard = new AutoRevealZone(null, ZoneType.DISCARD, ZonePick.TOP);
		burn = new AutoRevealZone(null, ZoneType.BURN, ZonePick.TOP);
		banish = new Zone(null, ZoneType.BANISH, ZonePick.TOP);
		voidZ = new Zone(null, ZoneType.VOID, ZonePick.TOP);
	}
	
	public void transfer(ZoneType source, ZonePick sourcePick, ZoneType dest, ZonePick destPick, int nbCard) 
	{
		Card[] movingCards = getZone(source).remove(nbCard, sourcePick);
		
		add(movingCards, dest, destPick);
	}
	
	public void add(Card[] cards, ZoneType zoneType, ZonePick zonePick) 
	{
		getZone(zoneType).add(cards, zonePick);
	}
	
	public void add(Card[] cards, ZoneType zoneType)
	{
		getZone(zoneType).add(cards);
	}
	
	public Card[] remove(int nbCard, ZoneType zoneType, ZonePick zonePick) 
	{ 
		return getZone(zoneType).remove(nbCard, zonePick);
	}
	
	public Card[] remove(int nbCard, ZoneType zoneType) 
	{ 
		return getZone(zoneType).remove(nbCard);
	}
	
	public Card[] getCards(ZoneType zoneType) 
	{ 
		return getZone(zoneType).getCards();
	}
	
	public void shuffle(ZoneType zoneType) 
	{
		getZone(zoneType).shuffle();
	}
	
	public void moveCardToIndex(ZoneType zoneType, int sourceIndex, int destIndex) 
	{
		getZone(zoneType).moveCardToIndex(sourceIndex, destIndex);
	}
	
	private Zone getZone(ZoneType zoneType)
	{
		switch(zoneType) {
		case DECK: return deck;
		case HAND: return hand;
		case DISCARD: return discard;
		case BURN: return burn;
		case BANISH: return banish;
		case VOID: return voidZ;
		default: throw new IllegalArgumentException("ZoneType ne peut pas être null");
		}
	}

	
}
