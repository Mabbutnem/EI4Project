package ui_entities;

import java.awt.Color;
import java.awt.event.MouseListener;

import boardelement.Character;
import characterlistener.IArmorListener;
import characterlistener.IHealthListener;
import characterlistener.IMoveListener;
import characterlistener.IRangeListener;

public abstract class UICharacter extends UIBoardElement
{
	private static final long serialVersionUID = 6597718398618170256L;

	
	
	protected UIValue rangeValue;
	protected UIValue armorValue;
	protected UIValue healthValue;
	protected UIValue moveValue;
	
	
	
	protected MouseListener mouseListenerCharacter;
	
	protected IRangeListener rangeListener;
	protected IArmorListener armorListener;
	protected IHealthListener healthListener;
	protected IMoveListener moveListener;

	
	
	public UICharacter(Character characterInput)
	{
		super(characterInput);
		
		mouseListenerCharacter = getUICharacterSelectionMouseListener();
		
		nameLabel.setText(characterInput.getName());
		putImageHereLabel.setText("-" + characterInput.getName().substring(0, 1) + "-");
		
		rangeValue = new UIValue(SIZE_HEADER, SIZE_HEADER, 0, "Range");
		rangeValue.setAllForeground(new Color(0, 0, 0));
		rangeValue.setLocation(0, 0);
		rangeValue.setVisible(false);
		add(rangeValue);
		
		armorValue = new UIValue(SIZE_HEADER, SIZE_HEADER, 0, "Armor");
		armorValue.setAllForeground(new Color(51, 102, 255));
		armorValue.setLocation(SIZE_X - 2*SIZE_HEADER, 0);
		armorValue.setVisible(false);
		add(armorValue);
		
		healthValue = new UIValue(SIZE_HEADER, SIZE_HEADER, 0, "Health");
		healthValue.setAllForeground(new Color(255, 51, 0));
		healthValue.setLocation(SIZE_X - SIZE_HEADER, 0);
		healthValue.setVisible(false);
		add(healthValue);
		
		moveValue = new UIValue(SIZE_HEADER, SIZE_HEADER, 0, "Move");
		moveValue.setAllForeground(new Color(0, 0, 0));
		moveValue.setLocation((SIZE_X - SIZE_HEADER)/2, SIZE_Y-SIZE_HEADER);
		moveValue.setVisible(false);
		add(moveValue);
		
		Character c = (Character) this.boardElement;
			
		rangeValue.setValue(c.getRange());
		
		armorValue.setValue(c.getArmor());
		armorListener = new IArmorListener() {
			@Override
			public void onChange(Character c, int previous, int actual) {
				armorValue.setValue(actual);
			}

			@Override
			public void onGain(Character c, int previous, int actual) {
				armorValue.setVisible(getStateUIVisible());
			}

			@Override
			public void onLoss(Character c, int previous, int actual) {
				armorValue.setVisible(getStateUIVisible() && actual > 0);
			}
		};
		c.addArmorListener(armorListener);
		
		healthValue.setValue(c.getHealth());
		healthListener = new IHealthListener() {
			@Override
			public void onChange(Character c, int previous, int actual) {
				healthValue.setValue(actual);
				healthValue.setVisible(getStateUIVisible());
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
		c.addHealthListener(healthListener);
		
		moveValue.setValue(c.getMove());
	}
	
	@Override
	public void clearListeners() {
		Character c = (Character) boardElement;

		c.removeRangeListener(rangeListener);
		c.removeArmorListener(armorListener);
		c.removeHealthListener(healthListener);
		c.removeMoveListener(moveListener);
	}
	
	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		rangeValue.addMouseListener(l);
		armorValue.addMouseListener(l);
		healthValue.addMouseListener(l);
		moveValue.addMouseListener(l);
	}
	
	protected MouseListener getUICharacterSelectionMouseListener() {
		return null;
	}
}
