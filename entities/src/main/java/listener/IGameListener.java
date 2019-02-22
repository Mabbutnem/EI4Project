package listener;

import boardelement.Character;

public interface IGameListener
{
	public void clearBoard(Character character);
	public void refreshRange(Character character);
}
