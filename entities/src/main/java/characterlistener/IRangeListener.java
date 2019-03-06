package characterlistener;

import java.util.EventListener;

import boardelement.Character;

public interface IRangeListener extends EventListener
{
	public void onChange(Character c, int previous, int actual);
	public void onGain(Character c, int previous, int actual);
	public void onLoss(Character c, int previous, int actual);
}
