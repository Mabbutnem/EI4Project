package spell;

public class Card extends ManaCostSpell
{
	private boolean revealed;

	
	
	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
	
}
