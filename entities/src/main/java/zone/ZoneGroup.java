package zone;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.inject.assistedinject.Assisted;

import event.ZoneGroupAddEvent;
import listener.IZoneGroupAddListener;
import spell.Card;

public class ZoneGroup
{
	private static IZoneGroupAddListener zoneGroupAddListener;
	
	@Autowired
	private AutoHideZone deck;
	@Autowired
	private AutoRevealZone hand;
	@Autowired
	private AutoRevealZone discard;
	@Autowired
	private AutoRevealZone burn;
	@Autowired
	private Zone banish;
	@Autowired
	private Zone voidZ;
	
	
	
	public ZoneGroup(@Assisted Card[] cards)
	{
		Preconditions.checkState(ZoneGroup.zoneGroupAddListener != null, "zoneGroupAddListener"
				+ " was not initialised (in static)");
		
		deck = new AutoHideZone(new Card[0], ZoneType.DECK, ZonePick.TOP);
		
		hand = new AutoRevealZone(new Card[0], ZoneType.HAND, ZonePick.TOP);
		discard = new AutoRevealZone(new Card[0], ZoneType.DISCARD, ZonePick.TOP);
		burn = new AutoRevealZone(new Card[0], ZoneType.BURN, ZonePick.TOP);
		
		banish = new Zone(new Card[0], ZoneType.BANISH, ZonePick.TOP);
		voidZ = new Zone(new Card[0], ZoneType.VOID, ZonePick.TOP);
		
		reset(cards);
	}
	
	

	public static void setZoneGroupAddListener(IZoneGroupAddListener zoneGroupAddListener) {
		ZoneGroup.zoneGroupAddListener = zoneGroupAddListener;
	}



	public void reset(Card[] cards)
	{
		deck.removeAll();
		hand.removeAll();
		discard.removeAll();
		burn.removeAll();
		banish.removeAll();
		voidZ.removeAll();
		
		deck.add(cards);
	}
	
	public void transfer(ZoneType source, ZonePick sourcePick, ZoneType dest, ZonePick destPick, int nbCard) 
	{
		Card[] movingCards = getZone(source).remove(nbCard, sourcePick);
		
		add(movingCards, dest, destPick);
	}
	
	public void add(Card[] cards, ZoneType zoneType, ZonePick zonePick) 
	{
		ZoneGroup.zoneGroupAddListener.displayAddedCards(new ZoneGroupAddEvent(cards, zoneType, zonePick));
		
		getZone(zoneType).add(cards, zonePick);
	}
	
	public void add(Card[] cards, ZoneType zoneType)
	{
		add(cards, zoneType, ZonePick.DEFAULT);
	}
	
	public Card[] remove(int nbCard, ZoneType zoneType, ZonePick zonePick) 
	{ 
		return getZone(zoneType).remove(nbCard, zonePick);
	}
	
	public Card[] remove(int nbCard, ZoneType zoneType) 
	{ 
		return remove(nbCard, zoneType, ZonePick.DEFAULT);
	}
	
	public void remove(Card card, ZoneType zoneType)
	{
		getZone(zoneType).remove(card);
	}
	
	public int size(ZoneType zoneType)
	{
		return getZone(zoneType).size();
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
	
	public void mulligan()
	{
		//TODO
	}
	
	public void transform()
	{
		int nbDeckCardToBurn = deck.size()/2;
		if(nbDeckCardToBurn > 0)
		{
			burn.add(deck.remove(nbDeckCardToBurn));
		}
		
		deck.add(hand.removeAll());
		deck.add(discard.removeAll());
		deck.add(banish.removeAll());
		deck.add(voidZ.removeAll());
		
		deck.shuffle();
		
		mulligan();
	}
	
	
	
	
	private Zone getZone(ZoneType zoneType)
	{
		Preconditions.checkArgument(zoneType != null, "zoneType was null but expected not null");
		
		switch(zoneType) {
		case DECK: return deck;
		case HAND: return hand;
		case DISCARD: return discard;
		case BURN: return burn;
		case BANISH: return banish;
		case VOID: return voidZ;
		default: throw new IllegalArgumentException();
		}
	}

	
}
