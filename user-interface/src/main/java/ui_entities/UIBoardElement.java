package ui_entities;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import boardelement.IBoardElement;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Font;

public abstract class UIBoardElement extends JPanel
{
	private static final long serialVersionUID = -3634135508516964622L;
	
	public static final int SIZE_X = 136;
	public static final int SIZE_Y = 170;
	protected static final int SIZE_HEADER = 34;
	protected static final int SIZE_TITLE = 34;
	protected static final int SIZE_ARROW = 34;
	
	protected JLabel nameLabel;
	protected JLabel putImageHereLabel; //A supprimer à terme !
	
	

	protected MouseListener mouseListenerBoard;
	
	protected IBoardElement boardElement;
	
	protected enum State
	{
		WIZARD_DONT_SEE,
		WIZARD_SEE_AND_CURRENT_DONT_SEE,
		WIZARD_SEE_AND_CURRENT_SEE
	}
	protected State state;
	
	protected boolean waitingToConfirmSelected;
	protected boolean selected;
	protected boolean canBeSelected;

	
	
	public UIBoardElement(IBoardElement boardElementInput)
	{
		this.boardElement = boardElementInput;
		
		mouseListenerBoard = getUIBoardSelectionMouseListener();
			
		waitingToConfirmSelected = false;
		selected = false;
		canBeSelected = true;
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(null);
		setSize(SIZE_X, SIZE_Y);
		
		nameLabel = new JLabel();
		nameLabel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setSize(SIZE_X, SIZE_TITLE);
		nameLabel.setLocation(0, SIZE_HEADER);
		add(nameLabel);
		
		putImageHereLabel = new JLabel();
		putImageHereLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		putImageHereLabel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		putImageHereLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		putImageHereLabel.setHorizontalAlignment(SwingConstants.CENTER);
		putImageHereLabel.setSize(SIZE_X, SIZE_Y-SIZE_HEADER-SIZE_TITLE-SIZE_ARROW);
		putImageHereLabel.setLocation(0, SIZE_HEADER+SIZE_TITLE);
		add(putImageHereLabel);
	}
	
	private MouseListener getUIBoardSelectionMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!canBeSelected) { return; }
				if(!selected) {
					setAllBackgrounds(UIConstants.MOUSE_ENTERED_COLOR);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				if(!canBeSelected) { return; }
				if(!selected) {
					setAllBackgrounds(getStateBaseColor());
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(!canBeSelected) { return; }
				if(selected) {
					setSelected(false);
					setAllBackgrounds(UIConstants.MOUSE_ENTERED_COLOR);
				}
				else {
					waitingToConfirmSelected = true;
				}
			}
		};
	}

	public void setState(boolean inWizardsRange, boolean inCurrentCharacterRange)
	{
		if(inWizardsRange) {
			state = inCurrentCharacterRange ? State.WIZARD_SEE_AND_CURRENT_SEE : State.WIZARD_SEE_AND_CURRENT_DONT_SEE;
		}
		else {
			state = State.WIZARD_DONT_SEE;
		}
		
		setCanBeSelected(inWizardsRange);
		
		refreshUIInFunctionOfTheState();
	}
	
	
	protected Color getStateBaseColor() {
		switch(state) {
		case WIZARD_DONT_SEE:
			return UIConstants.WIZARD_DONT_SEE_COLOR;
		case WIZARD_SEE_AND_CURRENT_DONT_SEE:
			return UIConstants.WIZARD_SEE_AND_CURRENT_DONT_SEE_COLOR;
		case WIZARD_SEE_AND_CURRENT_SEE:
			return UIConstants.BASE_COLOR;
		default:
			return UIConstants.BASE_COLOR;
		}
	}
	
	protected boolean getStateUIVisible() {
		return state != State.WIZARD_DONT_SEE;
	}
	
	protected void refreshUIInFunctionOfTheState()
	{
		setAllBackgrounds(selected ? UIConstants.SELECTED_COLOR : getStateBaseColor());
		
		nameLabel.setVisible(getStateUIVisible());
		putImageHereLabel.setVisible(getStateUIVisible());
	}
	
	protected void setAllBackgrounds(Color color) {
		setBackground(color);
	}
	
	public void clearListeners() {
		//Must be empty
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
			setAllBackgrounds(getStateBaseColor());
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
