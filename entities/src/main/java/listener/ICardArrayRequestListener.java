package listener;

import java.util.EventListener;

import event.CardArrayRequestEvent;
import spell.Card;

public interface ICardArrayRequestListener extends EventListener
{
	public Card[] getCardArray(CardArrayRequestEvent e);
}
