package event;

import spell.Card;
import zone.ZonePick;


//NOT FINISHED !!
public class CardArrayRequestEvent
{
	private final int nbCard;
	private final ZonePick zonePick;
	private final Card[] cards;
	
	public CardArrayRequestEvent(int nbCard, ZonePick zonePick, Card[] cards)
	{
		this.nbCard = nbCard;
		this.zonePick = zonePick;
		this.cards = cards;
	}

	public int getNbCard() {
		return nbCard;
	}

	public ZonePick getZonePick() {
		return zonePick;
	}

	public Card[] getCards() {
		return cards;
	}
	
	
	
}
