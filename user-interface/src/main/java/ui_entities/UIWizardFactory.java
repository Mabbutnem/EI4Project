package ui_entities;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import boardelement.WizardFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UIWizardFactory extends JPanel
{
	private static final long serialVersionUID = 6027656129191722020L;
	
	public static final int SIZE_X = 100;
	public static final int SIZE_Y = 60;

	private WizardFactory wizardFactory;

	private boolean waitingToConfirmSelected;
	private boolean selected;
	
	
	
	public UIWizardFactory(WizardFactory wf)
	{
		this.wizardFactory = wf;
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(null);
		setSize(SIZE_X, SIZE_Y);
		
		JLabel lblNewLabel = new JLabel(wf.getName());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, SIZE_X, SIZE_Y);
		add(lblNewLabel);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!selected) {
					setBackground(UIConstants.MOUSE_ENTERED_COLOR);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				if(!selected) {
					setBackground(UIConstants.BASE_COLOR);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(selected) {
					setSelected(false);
					setBackground(UIConstants.MOUSE_ENTERED_COLOR);
				}
				else {
					waitingToConfirmSelected = true;
				}
			}
			
		});
		
		setBackground(UIConstants.BASE_COLOR);
	}
	
	
	
	public WizardFactory getWizardFactory() {
		return wizardFactory;
	}
	
	public boolean isWaitingToConfirmSelected() {
		return waitingToConfirmSelected;
	}
	
	public void confirmSelected() {
		waitingToConfirmSelected = false;
		setSelected(true);
	}
	
	public void cancelSelected() {
		waitingToConfirmSelected = false;
		selected = false;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if(selected) {
			setBackground(UIConstants.SELECTED_COLOR);
		}
		else {
			setBackground(UIConstants.BASE_COLOR);
		}
		
		this.selected = selected;
	}
}
