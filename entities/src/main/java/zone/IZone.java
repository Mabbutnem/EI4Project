package zone;

import spell.Card;

public interface IZone
{
	public void add(Card[] cards);
	public void add(Card[] cards, ZonePick zonePick);
	public Card[] remove(int nbCard);
	public Card[] remove(int nbCard, ZonePick zonePick);
	public Card[] removeAll();
	public ZoneType getZoneType();
	public void shuffle();
	public Card[] getCards();
	public void hideCards();
	public void revealCards();
	public void moveCardToIndex(int sourceIndex, int destIndex);
}
