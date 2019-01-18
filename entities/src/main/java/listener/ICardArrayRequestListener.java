package listener;

import java.util.EventListener;

import event.CardArrayRequestEvent;
import spell.Card;

public interface ICardArrayRequestListener extends EventListener
{
	public Card[] removeAndGetCardArray(CardArrayRequestEvent e);
}
