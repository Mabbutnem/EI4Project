package characterlistener;

import boardelement.Character;

public class CharacterIntValueEvent
{
	private Character character;
	private int prevValue;
	private int actualValue;
	
	

	public CharacterIntValueEvent(Character character2, int prevValue, int actualValue) {
		super();
		this.character = character2;
		this.prevValue = prevValue;
		this.actualValue = actualValue;
	}
	
	
	
	public Character getCharacter() {
		return character;
	}
	
	public int getPrevValue() {
		return prevValue;
	}

	public int getActualValue() {
		return actualValue;
	}
}
