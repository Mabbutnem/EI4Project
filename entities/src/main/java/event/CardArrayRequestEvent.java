package event;

import java.util.List;

import spell.Card;
import zone.ZonePick;

public class CardArrayRequestEvent
{
	private final int nbCard;
	private final ZonePick zonePick;
	private final List<Card> cards;
	
	public CardArrayRequestEvent(int nbCard, ZonePick zonePick, List<Card> cards)
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

	public List<Card> getCards() {
		return cards;
	}
	
	
	
}
