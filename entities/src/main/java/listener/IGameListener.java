package listener;

import boardelement.IBoardElement;
import boardelement.Character;

public interface IGameListener
{
	public void clearBoard(IBoardElement boardElement);
	public void refreshRange(Character character);
}
