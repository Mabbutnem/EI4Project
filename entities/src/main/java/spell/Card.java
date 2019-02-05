package spell;

public class Card extends ManaCostSpell
{
	private String name;
	private boolean revealed;



	public String getName() {
		return name;
	}
	
	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
	
}
