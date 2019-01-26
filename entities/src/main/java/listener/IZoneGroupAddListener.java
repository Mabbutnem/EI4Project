package listener;

import java.util.EventListener;

import event.ZoneGroupAddEvent;

public interface IZoneGroupAddListener extends EventListener
{
	public void displayAddedCards(ZoneGroupAddEvent e);
}
