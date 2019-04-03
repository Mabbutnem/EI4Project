package ui_entities;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.google.common.base.Preconditions;

import boardelement.WizardFactory;

public class UIWizardFactoryChoice
{
	private static final int DIALOG_SIZE_X = 20;
	private static final int DIALOG_SIZE_Y = 60;
	
	private Container container;
	
	
	
	public UIWizardFactoryChoice(Container container)
	{
		this.container = container;
	}
	
	
	
	public WizardFactory chooseWizardFactory(WizardFactory[] wizardFactories)
	{
		Preconditions.checkArgument(wizardFactories.length > 0, "wizardFactories is empty");
		
		if(wizardFactories.length == 1) { return wizardFactories[0]; }
		
		UIWizardFactoryArray uiWfArray = new UIWizardFactoryArray(wizardFactories);
		
		uiWfArray.setTitle("Choose among the " + wizardFactories.length + " wizards");
		
		UIManager.put("OptionPane.minimumSize", new Dimension(uiWfArray.getWidth() + DIALOG_SIZE_X, uiWfArray.getHeight() + DIALOG_SIZE_Y));
		
		WizardFactory selected;
		
		do
		{
			JOptionPane.showInternalConfirmDialog(container, uiWfArray, "Make a choice",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
			
			selected = uiWfArray.getSelectedWf();
			
		}while(selected == null);
		
		return uiWfArray.getSelectedWf();
	}
}
