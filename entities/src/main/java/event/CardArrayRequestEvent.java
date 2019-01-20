package event;

import spell.Card;

public class CardArrayRequestEvent
{
	private final int nbCard;
	private final Card[] cards;
	
	public CardArrayRequestEvent(int nbCard, Card[] cards)
	{
		this.nbCard = nbCard;
		this.cards = cards;
	}

	public int getNbCard() {
		return nbCard;
	}

	public Card[] getCards() {
		return cards;
	}
	
	
	
}
