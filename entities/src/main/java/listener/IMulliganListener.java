package listener;

import java.util.EventListener;

import spell.Card;

public interface IMulliganListener extends EventListener
{
	public Card[] mulligan(Card[] cards);
}
