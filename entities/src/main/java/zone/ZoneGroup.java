package zone;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import listener.ICardArrayDisplayListener;
import spell.Card;

public class ZoneGroup
{
	private static ICardArrayDisplayListener cardArrayDisplayListener;
	
	@Autowired
	private AutoHideZone deckZ;
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
	
	
	
	public ZoneGroup(Card[] cards)
	{
		Preconditions.checkState(ZoneGroup.cardArrayDisplayListener != null, "cardArrayDisplayListener"
				+ " was not initialised (in static)");
		
		deckZ = new AutoHideZone(new Card[0], ZoneType.DECK, ZonePick.TOP);
		
		hand = new AutoRevealZone(new Card[0], ZoneType.HAND, ZonePick.TOP);
		discard = new AutoRevealZone(new Card[0], ZoneType.DISCARD, ZonePick.TOP);
		burn = new AutoRevealZone(new Card[0], ZoneType.BURN, ZonePick.TOP);
		
		banish = new Zone(new Card[0], ZoneType.BANISH, ZonePick.TOP);
		voidZ = new Zone(new Card[0], ZoneType.VOID, ZonePick.TOP);
		
		reset(cards);
	}
	
	

	
	public static void setCardArrayDisplayListener(ICardArrayDisplayListener cardArrayDisplayListener) {
		ZoneGroup.cardArrayDisplayListener = cardArrayDisplayListener;
	}


	

	public void reset(Card[] cards)
	{
		deckZ.removeAll();
		hand.removeAll();
		discard.removeAll();
		burn.removeAll();
		banish.removeAll();
		voidZ.removeAll();
		
		deckZ.add(cards);
	}
	
	public void transfer(ZoneType source, ZonePick sourcePick, ZoneType dest, ZonePick destPick, int nbCard) 
	{
		add(remove(nbCard, source, sourcePick), dest, destPick);
	}
	
	public void add(Card[] cards, ZoneType zoneType, ZonePick zonePick) 
	{
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
	
	public void mulligan(int nbCard)
	{
		Preconditions.checkArgument(nbCard > 0, "nbCard was %s but expected strictly positive", nbCard);
		
		//Draw nbCard
		hand.add(deckZ.remove(nbCard, ZonePick.TOP));
		
		//Choose cards to mulligan and return them in the deck
		Card[] cardsToMulligan = ZoneGroup.cardArrayDisplayListener.chooseCards(hand.getCards());
		for(Card c : cardsToMulligan) {hand.remove(c);}
		deckZ.add(cardsToMulligan);
		
		deckZ.shuffle();
		
		//Draw X cards, where X is the number of cards returned to the deck
		if(cardsToMulligan.length > 0)
		{
			hand.add(deckZ.remove(cardsToMulligan.length, ZonePick.TOP));
		}
	}
	
	public void transform()
	{
		//Burn half of your deck (rounded down)
		int nbDeckCardToBurn = deckZ.size()/2;
		if(nbDeckCardToBurn > 0)
		{
			burn.add(deckZ.remove(nbDeckCardToBurn));
		}
		
		//return cards from all zone (except the burn zone) to your deck
		deckZ.add(hand.removeAll());
		deckZ.add(discard.removeAll());
		deckZ.add(banish.removeAll());
		deckZ.add(voidZ.removeAll());
		
		deckZ.shuffle();
	}
	
	public void unvoid()
	{
		//Return all the cards from the void zone in your hand
		hand.add(voidZ.removeAll());
	}
	
	public void unbanish()
	{
		//Return one random card from the banish zone in your hand
		hand.add(banish.remove(1, ZonePick.RANDOM));
	}
	
	
	
	
	private Zone getZone(ZoneType zoneType)
	{
		Preconditions.checkArgument(zoneType != null, "zoneType was null but expected not null");
		
		switch(zoneType) {
		case DECK: return deckZ;
		case HAND: return hand;
		case DISCARD: return discard;
		case BURN: return burn;
		case BANISH: return banish;
		case VOID: return voidZ;
		default: throw new IllegalArgumentException();
		}
	}

	
}