package characterlistener;

import java.util.EventListener;

import boardelement.Character;

public interface IAliveListener extends EventListener
{
	public void onChange(Character c, boolean actual);
}
