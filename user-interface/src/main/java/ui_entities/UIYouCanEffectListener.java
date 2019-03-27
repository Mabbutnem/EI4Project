package ui_entities;

import java.awt.Container;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import effect.IApplicableEffect;
import listener.IYouCanEffectListener;

public class UIYouCanEffectListener implements IYouCanEffectListener
{
	private Container container;
	
	
	
	public UIYouCanEffectListener(Container container)
	{
		JOptionPane.setDefaultLocale(new Locale("en"));
		
		this.container = container;
	}
	
	
	
	@Override
	public boolean wantToApply(IApplicableEffect effect)
	{
		JLabel label = new JLabel(effect.getDescription() + " ?");
		
		int option = JOptionPane.showInternalConfirmDialog(container, label, "Make a choice", 
			JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		
		return option == JOptionPane.YES_OPTION;
	}

}
