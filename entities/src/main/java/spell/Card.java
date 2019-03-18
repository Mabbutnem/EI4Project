package spell;

import java.util.Arrays;

import javax.swing.event.EventListenerList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import effect.IEffect;
import listener.ICardListener;
import utility.INamedObject;

public class Card extends ManaCostSpell
{
	private boolean revealed;
	
	@JsonIgnore
	private EventListenerList listeners;

	
	
	public Card() {
		super();
		listeners = new EventListenerList();
	}
	
	public Card(String name, IEffect[] effects, int cost)
	{
		super(name, effects, cost);
		
		revealed = true;
		listeners = new EventListenerList();
	}
	
	//Copy constructor
	public Card(Card c)
	{
		super(c.getName(), c.getEffects(), c.getCost());
		listeners = new EventListenerList();
		revealed = c.isRevealed();
	}
	

	
	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed)
	{
		boolean prevRevealed = isRevealed();
		
		this.revealed = revealed;
		
		fireRevealedChanged(prevRevealed, revealed);
	}
	
	
	
	public void addCardListener(ICardListener listener)
	{
		listeners.add(ICardListener.class, listener);
	}
	
	public void removeCardListener(ICardListener listener)
	{
		listeners.remove(ICardListener.class, listener);
	}
	
	public ICardListener[] getCardListeners()
	{
		return listeners.getListeners(ICardListener.class);
	}
	
	private void fireRevealedChanged(boolean prevRevealed, boolean actualRevealed)
	{
		if(prevRevealed != actualRevealed)
		{
			for(ICardListener listener : getCardListeners())
			{
				listener.onRevealedChange();
			}
		}
	}



	@Override
	public INamedObject cloneObject() {
		return new Card(this);
	}

	
	
	@Override
	public String toString() {
		return "Card [revealed=" + revealed + ", listeners=" + listeners + ", name=" + name + ", effects="
				+ Arrays.toString(effects) + ", description=" + description + ", choosenTarget=" + choosenTarget
				+ ", words=" + words + "]";
	}
	
}
