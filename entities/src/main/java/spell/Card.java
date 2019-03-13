package spell;

import javax.swing.event.EventListenerList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import effect.IEffect;
import listener.ICardListener;
import utility.INamedObject;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Card extends ManaCostSpell
{
	private boolean revealed;
	
	@JsonIgnore
	private EventListenerList listeners;

	
	
	public Card() {
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
	
	@JsonIgnore
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
	
}
