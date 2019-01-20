package zone;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import event.CardArrayRequestEvent;
import exception.NotInitialisedContextException;
import listener.ICardArrayRequestListener;
import spell.Card;

public class Zone implements IZone
{
	private static ICardArrayRequestListener cardArrayRequestListener;
	
	private List<Card> cards;
	private final ZoneType zoneType;
	private final ZonePick defaultZonePick;
	
	
	
	public Zone(Card[] cards, ZoneType zoneType, ZonePick defaultZonePick)
	{
		if(cardArrayRequestListener == null) { throw new NotInitialisedContextException("cardArrayRequestListener"
				+ "n'a pas été initialisé (en static)");}
		
		if(cards == null) { cards = new Card[0];}
		
		if(zoneType == null) { throw new IllegalArgumentException("zoneType ne peut pas être null");}
		
		if(defaultZonePick == null) { throw new IllegalArgumentException("defaultZonePick ne peut pas être null");}
		if(defaultZonePick == ZonePick.CHOICE) { throw new IllegalArgumentException("defaultZonePick ne peut pas être CHOICE");}
		if(defaultZonePick == ZonePick.DEFAULT) { throw new IllegalArgumentException("defaultZonePick ne peut pas être DEFAULT");}
		
		this.cards = new LinkedList<Card>();
		add(cards, ZonePick.TOP);
		this.zoneType = zoneType;
		this.defaultZonePick = defaultZonePick;
	}
	
	
	
	public static void setCardArrayRequestListener(ICardArrayRequestListener cardArrayRequestListener) {
		Zone.cardArrayRequestListener = cardArrayRequestListener;
	}
	
	//A Override pour la classe dérivée AutoHideZone qui devra effectuer un mélange après le choix des cartes
	protected Card[] removeByChoice(int nbCard, ZonePick zonePick)
	{
		CardArrayRequestEvent e = new CardArrayRequestEvent(nbCard, zonePick, cards.toArray(new Card[0]));
		Card[] removedCards = cardArrayRequestListener.getCardArray(e);
		for(Card c : removedCards)
		{
			cards.remove(c);
		}
		return removedCards;
	}
	
	

	public void add(Card[] cards) {
		add(cards, ZonePick.DEFAULT);
	}

	public void add(Card[] cards, ZonePick zonePick)
	{
		if(cards == null) { throw new IllegalArgumentException("cards ne peut pas être null");}
		
		if(zonePick == null) { throw new IllegalArgumentException("zonePick ne peut pas être null");}
		if(zonePick == ZonePick.CHOICE) { throw new IllegalArgumentException("zonePick ne peut pas être CHOICE");}
		if(zonePick == ZonePick.DEFAULT) { zonePick = defaultZonePick;}
		
		switch(zonePick)
		{
		case TOP:
			for(Card c : cards) { this.cards.add(c);}
			break;
			
		case BOTTOM:
			for(Card c : cards) { this.cards.add(0, c);}
			break;
			
		case RANDOM:
			Random r = new Random();
			for(Card c : cards) { this.cards.add(r.nextInt(this.cards.size()), c);}
			break;
			
		default:
			break;
		}
		
	}

	public Card[] remove(int nbCard) {
		return remove(nbCard, ZonePick.DEFAULT);
	}

	public Card[] remove(int nbCard, ZonePick zonePick)
	{
		if(nbCard <= 0)  { throw new IllegalArgumentException("nbCard ne peut pas être inférieur ou égal à 0");}
		
		if(zonePick == null) { throw new IllegalArgumentException("zonePick ne peut pas être null");}
		if(zonePick == ZonePick.DEFAULT) { zonePick = defaultZonePick;}
		
		Card[] cardArray = null;
		List<Card> removedCards = new LinkedList<Card>();
		int i = 0;
		
		switch(zonePick)
		{
		case TOP:
			while(i<nbCard&&!cards.isEmpty())
			{
				removedCards.add(cards.remove(cards.size()-1));
				i++;
			}
			cardArray = removedCards.toArray(new Card[0]);
			break;
			
		case BOTTOM:
			while(i<nbCard&&!cards.isEmpty())
			{
				removedCards.add(cards.remove(0));
				i++;
			}
			cardArray = removedCards.toArray(new Card[0]);
			break;
			
		case RANDOM:
			Random r = new Random();
			while(i<nbCard&&!cards.isEmpty())
			{
				removedCards.add(cards.remove(r.nextInt(cards.size())));
				i++;
			}
			cardArray = removedCards.toArray(new Card[0]);
			break;
			
		case CHOICE:
			cardArray = removeByChoice(nbCard, zonePick);
			break;
			
		default:
			break;
		}
		
		
		return cardArray;
	}

	public ZoneType getZoneType() {
		return zoneType;
	}

	public void shuffle() {
		Random r = new Random();
		for(int i = 0; i < this.cards.size(); i++)
		{
			moveCardToIndex(i, r.nextInt(this.cards.size()+1));
		}
	}

	public Card[] getCards() {
		return cards.toArray(new Card[0]);
	}

	public void hideCards() {
		// TODO Auto-generated method stub
		
	}

	public void revealCards() {
		// TODO Auto-generated method stub
		
	}
	
	public void moveCardToIndex(int sourceIndex, int destIndex) {
		Card c = this.cards.remove(sourceIndex);
		this.cards.add(destIndex, c);
	}

}
