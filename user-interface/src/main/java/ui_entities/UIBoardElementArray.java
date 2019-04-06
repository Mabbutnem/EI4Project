package ui_entities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import boardelement.Character;
import boardelement.Corpse;
import boardelement.IBoardElement;
import boardelement.Monster;
import boardelement.Wizard;
import game.Game;
import javafx.collections.ListChangeListener;

public class UIBoardElementArray extends JPanel
{
	private static final long serialVersionUID = 7771802407599471447L;

	private static final int NB_ELEMENT_DISPLAYED = 9;
	private static final int TOP_DIST_TO_BORDER_Y = 34;
	private static final int BOTTOM_DIST_TO_BORDER_Y = 20;
	private static final int DIST_ELEMENT_TO_ELEMENT = 18;

	private ListChangeListener<IBoardElement> boardListener;
	private ListChangeListener<Boolean> rangeListener;
	
	private Character selectedCharacter;
	
	private MouseAdapter businessChangeMouseListener;
	private ActionListener leftArrowButtonListener;
	private ActionListener rightArrowButtonListener;
	
	private Border border;
	
	private Game game;
	private UIBoardElement[] uiBoardElements;
	
	private boolean canMoveElements;

	//Arrows
	private int actualDisplayedFirstIdx;
	private UIChoiceArrow leftArrow;
	private UIChoiceArrow rightArrow;

	
	
	public UIBoardElementArray(Game gameInput, boolean canMoveElementsInput)
	{
		this.game = gameInput;
		
		canMoveElements = canMoveElementsInput;
		
		initialize();
		
		border = new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0));
		setBorder(border);
		setLayout(null);
		
		actualDisplayedFirstIdx = 0;
		
		leftArrow = new UIChoiceArrow();
		leftArrow.setLocation(DIST_ELEMENT_TO_ELEMENT, TOP_DIST_TO_BORDER_Y);
		leftArrow.setToLeftChoiceArrow();
		leftArrow.addArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(1, 0, uiBoardElements.length, true);
			}
		});
		leftArrow.addDirectArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(actualDisplayedFirstIdx, 0, uiBoardElements.length, true);
			}
		});
		add(leftArrow);
		
		rightArrow = new UIChoiceArrow();
		rightArrow.setLocation(UIChoiceArrow.SIZE_X + NB_ELEMENT_DISPLAYED*(UIBoardElement.SIZE_X+DIST_ELEMENT_TO_ELEMENT) + 2*DIST_ELEMENT_TO_ELEMENT, TOP_DIST_TO_BORDER_Y);
		rightArrow.setToRightChoiceArrow();
		rightArrow.addArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(-1, 0, uiBoardElements.length, true);
			}
		});
		rightArrow.addDirectArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(NB_ELEMENT_DISPLAYED + actualDisplayedFirstIdx - uiBoardElements.length, 0, uiBoardElements.length, true);
			}
		});
		add(rightArrow);

		refreshArrows();
		refreshUIBoardElementsVisibility();
		refreshPanelSize();
		
		rangeListener = new ListChangeListener<Boolean>() {
			@Override
			public void onChanged(Change<? extends Boolean> arg0) {
				arg0.next();
				
				for(int i = 0; i < uiBoardElements.length; i++) {
					uiBoardElements[i].setState(game.getWizardsRange()[i], game.getCurrentCharacterRange()[i]);
				}
			}
		};
		game.addCurrentCharacterRangeListener(rangeListener);
		game.addWizardsRangeListener(rangeListener);
		
		boardListener = getNewBoardListener();
		game.addBoardListener(boardListener);

		((TitledBorder) border).setTitle(gameInput.getName());
		
	}
	
	private void initialize()
	{
		boolean[] currentCharacterRange = game.getCurrentCharacterRange();
		boolean[] wizardsRange = game.getWizardsRange();
		IBoardElement[] board = game.getBoard();
		uiBoardElements = new UIBoardElement[board.length];
		
		for(int i = 0; i < uiBoardElements.length; i++)
		{
			if(board[i] instanceof Wizard) {
				uiBoardElements[i] = new UIWizard((Wizard) board[i]);
				((UIWizard) uiBoardElements[i]).setCanMoveWithArrow(canMoveElements);
			}
			if(board[i] instanceof Monster) { uiBoardElements[i] = new UIMonster((Monster) board[i]); }
			if(board[i] instanceof Corpse) { uiBoardElements[i] = new UICorpse((Corpse) board[i]); }
			if(board[i] == null) { uiBoardElements[i] = new UINullBoardElement(); }
			
			uiBoardElements[i].setLocation(UIChoiceArrow.SIZE_X + i*(UIBoardElement.SIZE_X+DIST_ELEMENT_TO_ELEMENT) + 2*DIST_ELEMENT_TO_ELEMENT, TOP_DIST_TO_BORDER_Y);
			uiBoardElements[i].setState(wizardsRange[i], currentCharacterRange[i]);
			if(uiBoardElements[i] instanceof UICharacter) { uiBoardElements[i].addMouseListener(getUICardArrayMouseListener()); }
			add(uiBoardElements[i]);
		}
	}
	
	private MouseAdapter getUICardArrayMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				confirmSelectionAndUnselectAllOthers();
			}
		};
	}
	
	private void confirmSelectionAndUnselectAllOthers() {
		for(int j = 0; j < uiBoardElements.length; j++) {
			if(uiBoardElements[j].isWaitingToConfirmSelected()) {
				uiBoardElements[j].confirmSelected();
			}
			else if(uiBoardElements[j].isSelected()) {
				uiBoardElements[j].setSelected(false);
			}
		}
		
		for(int j = 0; j < uiBoardElements.length; j++) {
			if(uiBoardElements[j].isSelected() && uiBoardElements[j] instanceof UICharacter) {
				selectedCharacter = (Character) uiBoardElements[j].getBoardElement();
				return;
			}
		}
		selectedCharacter = null;
	}
	
	private ListChangeListener<IBoardElement> getNewBoardListener()
	{
		return new ListChangeListener<IBoardElement>() {

			public void onChanged(Change<? extends IBoardElement> arg0) {
				arg0.next();
				
				if(!arg0.wasReplaced()) { return; }
				
				int idx = arg0.getFrom();
				
				uiBoardElements[idx].clearListeners();
				remove(uiBoardElements[idx]);

				
				IBoardElement be = arg0.getAddedSubList().get(0);
				
				if(be instanceof Wizard) {
					uiBoardElements[idx] = new UIWizard((Wizard) game.getBoard()[idx]);
					((UIWizard) uiBoardElements[idx]).setCanMoveWithArrow(canMoveElements);
					((UIWizard) uiBoardElements[idx]).addLeftArrowButtonListener(leftArrowButtonListener);
					((UIWizard) uiBoardElements[idx]).addRightArrowButtonListener(rightArrowButtonListener);
				}
				if(be instanceof Monster) { uiBoardElements[idx] = new UIMonster((Monster) game.getBoard()[idx]); }
				if(be instanceof Corpse) { uiBoardElements[idx] = new UICorpse((Corpse) game.getBoard()[idx]); }
				if(be == null) { uiBoardElements[idx] = new UINullBoardElement(); }

				uiBoardElements[idx].setLocation(UIChoiceArrow.SIZE_X + (idx-actualDisplayedFirstIdx)*(UIBoardElement.SIZE_X+DIST_ELEMENT_TO_ELEMENT) + 2*DIST_ELEMENT_TO_ELEMENT, TOP_DIST_TO_BORDER_Y);
				uiBoardElements[idx].setVisible(actualDisplayedFirstIdx <= idx && idx < actualDisplayedFirstIdx+NB_ELEMENT_DISPLAYED);
				if(uiBoardElements[idx] instanceof UICharacter) { uiBoardElements[idx].addMouseListener(getUICardArrayMouseListener()); }
				uiBoardElements[idx].addMouseListener(businessChangeMouseListener);

				if(selectedCharacter == uiBoardElements[idx].getBoardElement()) {
					uiBoardElements[idx].setSelected(true);
				}
				
				add(uiBoardElements[idx]);
				
				repaint();
			}
		};
	}
	
	private void shiftUISpells(int delta, int firstIdx, int lastIdx, boolean modifyActualDisplayedFirstIdx) { //lastIdx is not included
		if(modifyActualDisplayedFirstIdx) { actualDisplayedFirstIdx -= delta; }
		
		for(int i = firstIdx; i < lastIdx; i++) {
			uiBoardElements[i].setLocation(uiBoardElements[i].getX() + delta*(UIBoardElement.SIZE_X + DIST_ELEMENT_TO_ELEMENT), uiBoardElements[i].getY());
			uiBoardElements[i].setVisible(actualDisplayedFirstIdx <= i && i < actualDisplayedFirstIdx+NB_ELEMENT_DISPLAYED);
		}
		
		refreshArrows();
		
		repaint();
	}
	
	private void refreshArrows() {
		leftArrow.setVisible(actualDisplayedFirstIdx > 0);
		leftArrow.setNbNotDisplayedElements(actualDisplayedFirstIdx);
		
		rightArrow.setVisible(actualDisplayedFirstIdx < uiBoardElements.length - NB_ELEMENT_DISPLAYED);
		rightArrow.setNbNotDisplayedElements(uiBoardElements.length - NB_ELEMENT_DISPLAYED - actualDisplayedFirstIdx);
	}
	
	private void refreshUIBoardElementsVisibility() {
		for(int i = 0; i < uiBoardElements.length; i++) {
			uiBoardElements[i].setVisible(actualDisplayedFirstIdx <= i && i < actualDisplayedFirstIdx+NB_ELEMENT_DISPLAYED);
		}
	}
	
	private void refreshPanelSize() {
		setSize( 2*UIChoiceArrow.SIZE_X + Math.min(NB_ELEMENT_DISPLAYED, uiBoardElements.length)*(UIBoardElement.SIZE_X+DIST_ELEMENT_TO_ELEMENT) + 3*DIST_ELEMENT_TO_ELEMENT,
				TOP_DIST_TO_BORDER_Y + BOTTOM_DIST_TO_BORDER_Y + UIBoardElement.SIZE_Y);
	}
	
	public void clearListeners() {
		for(UIBoardElement uiBE : uiBoardElements) {
			uiBE.clearListeners();
		}
		
		game.removeBoardListener(boardListener);
		game.removeCurrentCharacterRangeListener(rangeListener);
		game.removeWizardsRangeListener(rangeListener);
	}
	
	
	
	public void setTitle(String title) {
		border = new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), title, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0));
		setBorder(border);
	}

	public Character getSelectedCharacter()
	{
		return selectedCharacter;
	}
	
	public void setBusinessChangeMouseListener(MouseAdapter l) {
		businessChangeMouseListener = l;
		
		for(UIBoardElement uiBE : uiBoardElements) {
			uiBE.addMouseListener(l);
		}
	}
	
	public void setLeftArrowButtonListener(ActionListener l) {
		leftArrowButtonListener = l;
		
		for(UIBoardElement uiBE : uiBoardElements) {
			if(uiBE instanceof UIWizard) {
				((UIWizard) uiBE).addLeftArrowButtonListener(l);
			}
		}
	}
	
	public void setRightArrowButtonListener(ActionListener l) {
		rightArrowButtonListener = l;
		
		for(UIBoardElement uiBE : uiBoardElements) {
			if(uiBE instanceof UIWizard) {
				((UIWizard) uiBE).addRightArrowButtonListener(l);
			}
		}
	}

}
