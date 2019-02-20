package spell;

import utility.INamedObject;

public class Card extends ManaCostSpell
{
	private boolean revealed;

	
	
	//Copy constructor
	public Card(Card c)
	{
		this.name = c.getName();
		this.revealed = c.isRevealed();
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
