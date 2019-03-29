package ui_entities;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import boardelement.IBoardElement;
import spell.ManaCostSpell;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;

public class UIBoardElement extends JPanel
{
	private static final long serialVersionUID = -3634135508516964622L;
	
	public static final int SIZE_X = 120;
	public static final int SIZE_Y = 170;
	private static final int SIZE_HEADER = 34;
	private static final int SIZE_TITLE = 34;
	private static final int SIZE_ARROW = 34;
	private static final int SIZE_MOVE = 34;

	private JLabel armorLabel;
	private JLabel healthLabel;
	private JLabel nameLabel;
	private JLabel putImageHereLabel; //A supprimer à terme !
	private JLabel moveLabel;
	private JButton rightArrowButton;
	private JButton leftArrowButton;
	
	//private listeners;
	
	private IBoardElement boardElement;
	
	private boolean waitingToConfirmSelected;
	private boolean selected;
	private boolean canBeSelected;

	
	
	public UIBoardElement(IBoardElement boardElement)
	{
		this.boardElement = boardElement;
		waitingToConfirmSelected = false;
		selected = false;
		canBeSelected = true;
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(null);
		setSize(SIZE_X, SIZE_Y);
		
		armorLabel = new JLabel();
		armorLabel.setBackground(Color.RED);
		armorLabel.setOpaque(true);
		armorLabel.setText("20");
		armorLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		armorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		armorLabel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		armorLabel.setBounds(0, 0, SIZE_HEADER, SIZE_HEADER);
		add(armorLabel);
		
	}
	
	private void setAllBackgrounds(Color color) {
		setBackground(color);
	}
	
	

	public IBoardElement getBoardElement() {
		return boardElement;
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
			setAllBackgrounds(UIConstants.SELECTED_COLOR);
		}
		else {
			setAllBackgrounds(UIConstants.BASE_COLOR);
		}
		
		this.selected = selected;
	}

	public boolean isCanBeSelected() {
		return canBeSelected;
	}

	public void setCanBeSelected(boolean canBeSelected) {
		this.canBeSelected = canBeSelected;
	}
}
