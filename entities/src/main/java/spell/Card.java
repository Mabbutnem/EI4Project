package spell;

import effect.IEffect;
import utility.INamedObject;

public class Card extends ManaCostSpell
{
	private boolean revealed;

	
	
	public Card(String name, IEffect[] effects, int cost)
	{
		super(name, effects, cost);
		revealed = true;
	}
	
	//Copy constructor
	public Card(Card c)
	{
		super(c.getName(), c.getEffects(), c.getCost());
		revealed = c.isRevealed();
	}
	

	
	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}



	@Override
	public INamedObject cloneObject() {
		return new Card(this);
	}
	
}
