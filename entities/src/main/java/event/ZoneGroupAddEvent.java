package event;

import spell.Card;
import zone.ZonePick;
import zone.ZoneType;

public class ZoneGroupAddEvent
{
	private final Card[] cards;
	private final ZoneType dest;
	private final ZonePick destPick;

	public ZoneGroupAddEvent(Card[] cards, ZoneType dest, ZonePick destPick)
	{
		this.cards = cards;
		this.dest = dest;
		this.destPick = destPick;
	}

	public Card[] getCards() {
		return cards;
	}

	public ZoneType getDest() {
		return dest;
	}

	public ZonePick getDestPick() {
		return destPick;
	}
	
	

}
