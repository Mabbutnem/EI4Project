package listener;

import spell.Card;

public interface ICardArrayRequestListener
{
	public Card[] chooseCards(int nbCard, Card[] cards);
	public Card[] chooseCards(Card[] cards); //Any number of them (can be 0)
}
