package spell;

import javax.swing.event.EventListenerList;

import effect.IEffect;
import listener.ICardListener;
import utility.INamedObject;

public class Card extends ManaCostSpell
{
	private boolean revealed;
	
	private EventListenerList listeners;

	
	
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
		boolean oldRevealed = isRevealed();
		
		this.revealed = revealed;
		
		fireRevealedChanged(oldRevealed, revealed);
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
	
	private void fireRevealedChanged(boolean oldRevealed, boolean actualRevealed)
	{
		if(oldRevealed != actualRevealed)
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
