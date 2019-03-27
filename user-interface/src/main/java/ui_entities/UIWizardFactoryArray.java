package ui_entities;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import boardelement.WizardFactory;

public class UIWizardFactoryArray extends JPanel
{
	private static final long serialVersionUID = 1171737307421001435L;

	private static final int TOP_DIST_TO_BORDER_Y = 34;
	private static final int BOTTOM_DIST_TO_BORDER_Y = 20;
	private static final int DIST_WF_TO_WF = 18;
	
	private Border border;
	
	private UIWizardFactory[] uiwizardFactories;
	
	
	
	public UIWizardFactoryArray(WizardFactory[] wizardFactories)
	{
		border = new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Your wizards", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0));
		setBorder(border);
		setLayout(null);
		setSize(wizardFactories.length * (UIWizardFactory.SIZE_X + DIST_WF_TO_WF) + DIST_WF_TO_WF,
				TOP_DIST_TO_BORDER_Y + UIWizardFactory.SIZE_Y + BOTTOM_DIST_TO_BORDER_Y);
		
		this.uiwizardFactories = new UIWizardFactory[wizardFactories.length];
		for(int i = 0; i < wizardFactories.length; i++)
		{
			this.uiwizardFactories[i] = new UIWizardFactory(wizardFactories[i]);
			this.uiwizardFactories[i].setLocation(DIST_WF_TO_WF + i * (UIWizardFactory.SIZE_X + DIST_WF_TO_WF), TOP_DIST_TO_BORDER_Y);
			this.uiwizardFactories[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for(int j = 0; j < uiwizardFactories.length; j++) {
						if(uiwizardFactories[j].isWaitingToConfirmSelected()) {
							uiwizardFactories[j].confirmSelected();
						}
						else if(uiwizardFactories[j].isSelected()) {
							uiwizardFactories[j].setSelected(false);
						}
					}
				}
			});
			add(this.uiwizardFactories[i]);
		}
	}
	
	
	
	public void setTitle(String title) {
		((TitledBorder) border).setTitle(title);
	}
	
	public WizardFactory getSelectedWf() {
		for(UIWizardFactory uiWf : uiwizardFactories) {
			if(uiWf.isSelected()) {
				return uiWf.getWizardFactory();
			}
		}
		
		return null;
	}

}
