package listener;

import java.util.EventListener;

import spell.Card;

public interface IAnyNumberCardArrayRequestListener extends EventListener
{
	public Card[] getCardArray(Card[] cards);
}
