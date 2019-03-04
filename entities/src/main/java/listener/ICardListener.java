package listener;

import java.util.EventListener;

public interface ICardListener extends EventListener
{
	public void onRevealedChange();
}
