package ui_entities;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.google.common.base.Preconditions;

import listener.ICardArrayRequestListener;
import spell.Card;
import spell.ISpell;

public class UICardArrayRequestListener implements ICardArrayRequestListener
{
	private static final int DIALOG_SIZE_X = 20;
	private static final int DIALOG_SIZE_Y = 60;
	
	private Container container;
	
	
	
	public UICardArrayRequestListener(Container container)
	{
		this.container = container;
	}
	
	

	@Override
	public Card[] chooseCards(int nbCard, Card[] cards)
	{
		if(nbCard <= 0) { return new Card[0]; }
		
		Preconditions.checkArgument(nbCard <= cards.length, "nbCard must be lower or equal to cards length");
		
		UICardArray uiCardArray = new UICardArray(cards);
		uiCardArray.setNbCanBeSelected(nbCard);
		uiCardArray.setTitle("Choose " + nbCard + " card" + (nbCard > 1 ? "s" : ""));
		
		UIManager.put("OptionPane.minimumSize", new Dimension(uiCardArray.getWidth() + DIALOG_SIZE_X, uiCardArray.getHeight() + DIALOG_SIZE_Y));
		
		ISpell[] selectedSpells;
		
		do
		{
			JOptionPane.showInternalConfirmDialog(container, uiCardArray, "Make a choice", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		
			selectedSpells = uiCardArray.getSelectedSpells();
			
			if(selectedSpells.length != nbCard) {
				uiCardArray.setTitle("You must choose " + nbCard + " card" + (nbCard > 1 ? "s" : "") + " !");
			}
		
		}while(selectedSpells.length != nbCard);
		
		uiCardArray.clearListeners();
		
		
		
		Card[] choosenCards = new Card[selectedSpells.length];
		for(int i = 0; i < choosenCards.length; i++) {
			choosenCards[i] = (Card) selectedSpells[i];
		}
		
		return choosenCards;
	}

	@Override
	public Card[] chooseCards(Card[] cards)
	{
		UICardArray uiCardArray = new UICardArray(cards);
		uiCardArray.setNbCanBeSelected(cards.length);
		uiCardArray.setTitle("Choose any number of cards to mulligan");

		UIManager.put("OptionPane.minimumSize", new Dimension(uiCardArray.getWidth() + DIALOG_SIZE_X, uiCardArray.getHeight() + DIALOG_SIZE_Y));
		
		JOptionPane.showInternalConfirmDialog(container, uiCardArray, "Make a choice and confirm", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);

		
		
		ISpell[] selectedSpells = uiCardArray.getSelectedSpells();
		uiCardArray.clearListeners();
		Card[] choosenCards = new Card[selectedSpells.length];
		for(int i = 0; i < choosenCards.length; i++) {
			choosenCards[i] = (Card) selectedSpells[i];
		}
		
		return choosenCards;
	}

}
