package characterlistener;

import java.util.EventListener;

public interface IHealthListener extends EventListener
{
	public void onChange(CharacterIntValueEvent e);
	public void onGain(CharacterIntValueEvent e);
	public void onLoss(CharacterIntValueEvent e);
}
