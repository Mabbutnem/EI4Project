package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import game.Game;
import spell.ISpell;

@JsonTypeName("wordEffect")
public class WordEffect implements IEffect
{
	private Word word;
	
	
	
	public WordEffect(Word word)
	{
		Preconditions.checkArgument(word != null, "word was null but expected not null");
		
		this.word = word;
	}
	
	

	public Word getWord() {
		return word;
	}
	
	@Override
	public String getDescription()
	{
		return word.toString();
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
