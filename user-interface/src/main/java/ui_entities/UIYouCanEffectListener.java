package ui_entities;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

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
		
		UIManager.put("OptionPane.minimumSize", new Dimension(0, 0));
		
		int option = JOptionPane.showInternalConfirmDialog(container, label, "Make a choice", 
			JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		
		return option == JOptionPane.YES_OPTION;
	}

}
