package zone;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Preconditions;

import event.CardArrayRequestEvent;
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
		Preconditions.checkState(Zone.cardArrayRequestListener != null, "cardArrayRequestListener"
				+ " was not initialised (in static)");

		Preconditions.checkArgument(cards != null, "cards was null but expected not null");

		Preconditions.checkArgument(zoneType != null, "zoneType was null but expected not null");
		
		Preconditions.checkArgument(defaultZonePick != null, "defaultZonePick was null but expected not null");
		Preconditions.checkArgument(defaultZonePick != ZonePick.CHOICE, "defaultZonePick was"
				+ " %s but expected not ZonePick.CHOICE", defaultZonePick);
		Preconditions.checkArgument(defaultZonePick != ZonePick.DEFAULT, "defaultZonePick was"
				+ " %s but expected not ZonePick.DEFAULT", defaultZonePick);
		
		this.cards = new LinkedList<>();
		add(cards, ZonePick.TOP);
		this.zoneType = zoneType;
		this.defaultZonePick = defaultZonePick;
	}
	
	
	
	public static void setCardArrayRequestListener(ICardArrayRequestListener cardArrayRequestListener) {
		Zone.cardArrayRequestListener = cardArrayRequestListener;
	}
	
	

	public void add(Card[] cards) {
		add(cards, ZonePick.DEFAULT);
	}

	public void add(Card[] cards, ZonePick zonePick)
	{
		Preconditions.checkArgument(cards != null, "cards was null but expected not null");
		
		Preconditions.checkArgument(zonePick != null, "zonePick was null but expected not null");
		Preconditions.checkArgument(zonePick != ZonePick.CHOICE, "zonePick was"
				+ " %s but expected not ZonePick.CHOICE", zonePick);
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
		Preconditions.checkArgument(nbCard > 0, "nbCard was %s but expected strictly positive", nbCard);

		Preconditions.checkArgument(zonePick != null, "zonePick was null but expected not null");
		if(zonePick == ZonePick.DEFAULT) { zonePick = defaultZonePick;}
		
		if(nbCard >= cards.size())
		{
			return removeAll();
		}
		else
		{
			switch (zonePick) {
			case TOP:
				return removeByTop(nbCard);

			case BOTTOM:
				return removeByBottom(nbCard);

			case RANDOM:
				return removeByRandom(nbCard);

			case CHOICE:
				return removeByChoice(nbCard);

			default:
				return new Card[0];
			}
		}
	}
	
	private Card[] removeByTop(int nbCard)
	{
		List<Card> removedCardsList = new LinkedList<>();
		
		for(int i = 0; i < nbCard; i++) {
			removedCardsList.add(cards.remove(cards.size() - 1));
		}
		return removedCardsList.toArray(new Card[0]);
	}
	
	private Card[] removeByBottom(int nbCard)
	{
		List<Card> removedCardsList = new LinkedList<>();
		
		for(int i = 0; i < nbCard; i++) {
			removedCardsList.add(cards.remove(0));
		}
		return removedCardsList.toArray(new Card[0]);
	}
	
	private Card[] removeByRandom(int nbCard)
	{
		List<Card> removedCardsList = new LinkedList<>();
		
		Random r = new Random();
		
		for(int i = 0; i < nbCard; i++) {
			removedCardsList.add(cards.remove(r.nextInt(cards.size())));
		}
		return removedCardsList.toArray(new Card[0]);
	}
	
	//A Override pour la classe dérivée AutoHideZone qui devra effectuer un mélange après le choix des cartes
	protected Card[] removeByChoice(int nbCard)
	{
		revealCards();
		CardArrayRequestEvent e = new CardArrayRequestEvent(nbCard, cards.toArray(new Card[0]));
		Card[] removedCards = Zone.cardArrayRequestListener.getCardArray(e);
		for(Card c : removedCards)
		{
			cards.remove(c);
		}
		return removedCards;
	}
	
	public void remove(Card card)
	{
		Preconditions.checkArgument(card != null, "card was null but expected not null");
		cards.remove(card);
	}

	public Card[] removeAll() {
		Card[] removedCards = cards.toArray(new Card[0]);
		cards.clear();
		return removedCards;
	}
	
	public int size()
	{
		return cards.size();
	}

	public ZoneType getZoneType() {
		return zoneType;
	}

	public void shuffle() {
		Random r = new Random();
		for(int i = 0; i < this.cards.size(); i++)
		{
			moveCardToIndex(i, r.nextInt(this.cards.size()));
		}
	}

	public Card[] getCards() {
		return cards.toArray(new Card[0]);
	}

	public void hideCards() {
		cards.forEach(c->c.setRevealed(false));
	}

	public void revealCards() {
		cards.forEach(c->c.setRevealed(true));
	}
	
	public void moveCardToIndex(int sourceIndex, int destIndex) {
		Card c = this.cards.remove(sourceIndex);
		this.cards.add(destIndex, c);
	}

}
