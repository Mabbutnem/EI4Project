package spell;

import game.Game;
import utility.INamedObject;

import boardelement.Character;
import effect.IEffect;
import effect.Word;

public interface ISpell extends INamedObject
{
	public String getDescription();
	public IEffect[] getEffects();
	public Character getChoosenTarget();
	public void setChoosenTarget(Character choosenTarget);
	public boolean containsWord(Word word);
	public void addWord(Word word);
	public void cast(Game game);
}
