package effect;

import game.Game;
import spell.ISpell;

public class WordEffect implements IEffect
{
	private Word word;
	
	

	public Word getWord() {
		return word;
	}
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepare(Game game, ISpell spell)
	{
		spell.addWord(word);
	}

	@Override
	public void clean()
	{
		//No need to clean a WordEffect
	}

}
