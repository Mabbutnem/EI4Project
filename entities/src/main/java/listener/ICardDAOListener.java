package listener;

import spell.Card;

public interface ICardDAOListener
{
	Card getCard(String name);
	Card[] getCards(String[] names);
}
