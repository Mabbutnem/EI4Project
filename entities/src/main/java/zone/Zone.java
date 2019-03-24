package zone;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import listener.ICardArrayRequestListener;
import spell.Card;

public class Zone implements IZone
{
	private static final String ZONE_PICK_NULL_ERROR_MESSAGE = "zonePick was null but expected not null";
	
	private static ICardArrayRequestListener cardArrayDisplayListener;
	
	@JsonIgnore
	private ObservableList<Card> cards;
	private ZoneType zoneType;
	private ZonePick defaultZonePick;
	
	
	
	public Zone()
	{
		super();
		
		cards = FXCollections.observableArrayList();
	}
	
	public Zone(Card[] cards, ZoneType zoneType, ZonePick defaultZonePick)
	{
		Preconditions.checkState(Zone.cardArrayDisplayListener != null, "cardArrayDisplayListener"
				+ " was not initialised (in static)");

		Preconditions.checkArgument(cards != null, "cards was null but expected not null");

		Preconditions.checkArgument(zoneType != null, "zoneType was null but expected not null");
		
		Preconditions.checkArgument(defaultZonePick != null, "defaultZonePick was null but expected not null");
		Preconditions.checkArgument(defaultZonePick != ZonePick.CHOICE, "defaultZonePick was"
				+ " %s but expected not ZonePick.CHOICE", defaultZonePick);
		Preconditions.checkArgument(defaultZonePick != ZonePick.DEFAULT, "defaultZonePick was"
				+ " %s but expected not ZonePick.DEFAULT", defaultZonePick);
		
		this.cards = FXCollections.observableArrayList();
		add(cards, ZonePick.TOP);
		this.zoneType = zoneType;
		this.defaultZonePick = defaultZonePick;
	}
	
	
	
	
	public static void setCardArrayRequestListener(ICardArrayRequestListener cardArrayDisplayListener) {
		Zone.cardArrayDisplayListener = cardArrayDisplayListener;
	}
	
	
	

	public void add(Card[] cards) {
		add(cards, ZonePick.DEFAULT);
	}

	public void add(Card[] cards, ZonePick zonePick)
	{
		Preconditions.checkArgument(cards != null, "cards was null but expected not null");
		
		Preconditions.checkArgument(zonePick != null, ZONE_PICK_NULL_ERROR_MESSAGE);
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
			for(Card c : cards) { this.cards.add(r.nextInt(this.cards.size()+1), c);}
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
		Preconditions.checkArgument(nbCard >= 0, "nbCard was %s but expected strictly positive", nbCard);

		Preconditions.checkArgument(zonePick != null, ZONE_PICK_NULL_ERROR_MESSAGE);
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
		Card[] removedCards = Zone.cardArrayDisplayListener.chooseCards(nbCard, cards.toArray(new Card[0]));
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

    @JsonProperty("cards")
	public Card[] getCards() {
		return cards.toArray(new Card[0]);
	}

    @JsonProperty("cards")
    public void setCards(Card[] card) {
    	cards.addAll(Arrays.asList(card));
    }
	
	public void reveal(int nbCard, ZonePick zonePick)
	{
		Preconditions.checkArgument(nbCard >= 0, "nbCard was %s but expected strictly positive", nbCard);

		Preconditions.checkArgument(zonePick != null, ZONE_PICK_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(zonePick != ZonePick.CHOICE, "zonePick was %s but expected different", zonePick);
		Preconditions.checkArgument(zonePick != ZonePick.RANDOM, "zonePick was %s but expected different", zonePick);
		if(zonePick == ZonePick.DEFAULT) { zonePick = defaultZonePick;}
		
		if(nbCard >= cards.size())
		{
			revealCards();
		}
		switch (zonePick) {
		case TOP:
			for(int i = 0; i < nbCard; i++) { cards.get(cards.size()-1 - i).setRevealed(true); }
			break;

		case BOTTOM:
			for(int i = 0; i < nbCard; i++) { cards.get(i).setRevealed(true); }
			break;

		default:
			break;
		}
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
	
	public void addListener(ListChangeListener<Card> listener)
	{
		cards.addListener(listener);
	}
	
	public void removeListener(ListChangeListener<Card> listener)
	{
		cards.removeListener(listener);
	}

}
