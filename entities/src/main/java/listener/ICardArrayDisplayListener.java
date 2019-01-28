package listener;

import spell.Card;
import zone.ZonePick;
import zone.ZoneType;

public interface ICardArrayDisplayListener
{
	public Card[] chooseCards(int nbCard, Card[] cards);
	public Card[] chooseCards(Card[] cards); //Any number of them (can be 0)
	public void displayAddCards(Card[] cards, ZoneType dest, ZonePick destPick);
	public void displayTransferCards(Card[] cards, ZoneType source, ZonePick sourcePick, ZoneType dest, ZonePick destPick);
}
