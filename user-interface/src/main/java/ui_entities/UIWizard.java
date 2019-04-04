package ui_entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import boardelement.Wizard;
import boardelement.Character;
import characterlistener.IManaListener;
import characterlistener.IMoveListener;
import characterlistener.IRangeListener;
import characterlistener.ITransformedListener;

public class UIWizard extends UICharacter
{
	private static final long serialVersionUID = 7233457885607438707L;
	
	
	
	private UIValue manaValue;
	private JLabel transformedLabel;
	

	private boolean canMoveWithArrow;
	private JButton leftArrowButton;
	private JButton rightArrowButton;

	private IManaListener manaListener;
	private ITransformedListener transformedListener;
	
	
	
	public UIWizard(Wizard wizardInput)
	{
		super(wizardInput);
		
		Wizard w = (Wizard) this.boardElement;

		rangeListener = new IRangeListener() {
			@Override
			public void onChange(Character c, int previous, int actual) {
				rangeValue.setValue(actual);
				rangeValue.setVisible( getStateUIVisible() && c.getRange() != Wizard.getWizardConstant().getBaseRange());
			}

			@Override
			public void onGain(Character c, int previous, int actual) {
				//Must be empty
			}

			@Override
			public void onLoss(Character c, int previous, int actual) {
				//Must be empty
			}
		};
		w.addRangeListener(rangeListener);

		moveListener = new IMoveListener() {
			@Override
			public void onChange(Character c, int previous, int actual) {
				moveValue.setValue(actual);
				moveValue.setVisible( getStateUIVisible() && c.getMove() != Wizard.getWizardConstant().getBaseMove());
			}

			@Override
			public void onGain(Character c, int previous, int actual) {
				//Must be empty
			}

			@Override
			public void onLoss(Character c, int previous, int actual) {
				//Must be empty
			}
		};
		w.addMoveListener(moveListener);
		
		manaValue = new UIValue(SIZE_HEADER, SIZE_HEADER, 0, "Mana");
		manaValue.setAllForeground(new Color(0, 153, 0));
		manaValue.setLocation(SIZE_HEADER, 0);
		manaValue.setVisible(false);
		add(manaValue);
		
		manaValue.setValue(w.getMana());
		manaListener = new IManaListener() {
			@Override
			public void onChange(Character c, int previous, int actual) {
				manaValue.setValue(actual);
				manaValue.setVisible(getStateUIVisible() && actual != Wizard.getWizardConstant().getBaseMana());
			}

			@Override
			public void onGain(Character c, int previous, int actual) {
				//Must be empty
			}

			@Override
			public void onLoss(Character c, int previous, int actual) {
				//Must be empty
			}
		};
		w.addManaListener(manaListener);
		
		transformedLabel = new JLabel("T");
		transformedLabel.setVisible(false);
		transformedLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		transformedLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		transformedLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		transformedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		transformedLabel.setSize(SIZE_TITLE, SIZE_TITLE);
		transformedLabel.setLocation(SIZE_X - SIZE_TITLE, SIZE_HEADER);
		add(transformedLabel);
		
		transformedListener = new ITransformedListener() {
			@Override
			public void onChange(Character c, boolean actual) {
				transformedLabel.setVisible(getStateUIVisible() && actual);
				nameLabel.setSize(SIZE_X - (actual ? SIZE_TITLE : 0), SIZE_TITLE);
			}
		};
		w.addTransformedListener(transformedListener);
		
		addMouseListener(mouseListenerBoard);
		addMouseListener(mouseListenerCharacter);
		
		canMoveWithArrow = false;
		
		leftArrowButton = new JButton("<");
		leftArrowButton.setVisible(false);
		leftArrowButton.setBorder(new MatteBorder(0, 2, 2, 1, (Color) new Color(0, 0, 0)));
		leftArrowButton.setHorizontalTextPosition(SwingConstants.CENTER);
		leftArrowButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		leftArrowButton.setSize((SIZE_X - SIZE_HEADER)/2, SIZE_ARROW);
		leftArrowButton.setLocation(0, SIZE_Y-SIZE_ARROW);
		leftArrowButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				leftArrowButton.setBackground(UIConstants.MOUSE_ENTERED_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				leftArrowButton.setBackground(getStateBaseColor());
			}
		});
		add(leftArrowButton);
		
		rightArrowButton = new JButton(">");
		rightArrowButton.setVisible(false);
		rightArrowButton.setBorder(new MatteBorder(0, 1, 2, 2, (Color) new Color(0, 0, 0)));
		rightArrowButton.setHorizontalTextPosition(SwingConstants.CENTER);
		rightArrowButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		rightArrowButton.setSize((SIZE_X - SIZE_HEADER)/2, SIZE_ARROW);
		rightArrowButton.setLocation((SIZE_X + SIZE_HEADER)/2, SIZE_Y-SIZE_ARROW);
		rightArrowButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				rightArrowButton.setBackground(UIConstants.MOUSE_ENTERED_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				rightArrowButton.setBackground(getStateBaseColor());
			}
		});
		add(rightArrowButton);
		
		setState(false, false);
	}
	
	@Override
	public void clearListeners() {
		super.clearListeners();
		
		Wizard w = (Wizard) boardElement;

		w.removeManaListener(manaListener);
		w.removeTransformedListener(transformedListener);
	}
	
	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		manaValue.addMouseListener(l);
	}
	
	@Override
	protected MouseListener getUICharacterSelectionMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				boolean uiVisible = getStateUIVisible();
				
				rangeValue.setVisible(uiVisible);
				moveValue.setVisible(uiVisible);
				manaValue.setVisible(uiVisible);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				Wizard w = (Wizard) boardElement;
				boolean uiVisible = getStateUIVisible();
				
				rangeValue.setVisible(uiVisible && (selected || w.getRange() != Wizard.getWizardConstant().getBaseRange()));
				moveValue.setVisible(uiVisible && (selected || w.getMove() != Wizard.getWizardConstant().getBaseMove()));
				manaValue.setVisible(uiVisible && (selected || w.getMana() != Wizard.getWizardConstant().getBaseMana()));
			}
		};
	}
	
	@Override
	protected void refreshUIInFunctionOfTheState()
	{
		super.refreshUIInFunctionOfTheState();

		Wizard w = (Wizard) this.boardElement;
		boolean uiVisible = getStateUIVisible();
			
		rangeValue.setVisible(uiVisible && (selected || w.getRange() != Wizard.getWizardConstant().getBaseRange()));
		armorValue.setVisible(uiVisible && w.getArmor() > 0);
		healthValue.setVisible(uiVisible);
		moveValue.setVisible(uiVisible && (selected || w.getMove() != Wizard.getWizardConstant().getBaseMove()));
		manaValue.setVisible(uiVisible && (selected || w.getMana() != Wizard.getWizardConstant().getBaseMana()));
		transformedLabel.setVisible(uiVisible && w.isTransformed());

		leftArrowButton.setBackground(getStateBaseColor());
		rightArrowButton.setBackground(getStateBaseColor());
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		
		if(canMoveWithArrow) {
			leftArrowButton.setVisible(this.selected);
			rightArrowButton.setVisible(this.selected);
		}
	}

	public boolean isCanMoveWithArrow() {
		return canMoveWithArrow;
	}

	public void setCanMoveWithArrow(boolean canMoveWithArrow) {
		this.canMoveWithArrow = canMoveWithArrow;
	}
	
	public void addLeftArrowButtonListener(ActionListener l) {
		leftArrowButton.addActionListener(l);
	}
	
	public void addRightArrowButtonListener(ActionListener l) {
		rightArrowButton.addActionListener(l);
	}

}
