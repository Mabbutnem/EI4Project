package listener;

import spell.Card;

public interface ICardDaoListener
{
	Card getCard(String name);
	Card[] getCards(String[] names);
}
