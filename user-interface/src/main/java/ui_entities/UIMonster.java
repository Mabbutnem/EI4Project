package ui_entities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import boardelement.Character;
import boardelement.Monster;
import characterlistener.IMoveListener;
import characterlistener.IRangeListener;

public class UIMonster extends UICharacter
{
	private static final long serialVersionUID = -5722856152002173989L;

	
	
	public UIMonster(Monster monsterInput)
	{
		super(monsterInput);

		Monster m = (Monster) this.boardElement;
		
		rangeListener = new IRangeListener() {
			@Override
			public void onChange(Character c, int previous, int actual) {
				rangeValue.setValue(actual);
				rangeValue.setVisible( getStateUIVisible() && c.getRange() != ((Monster) c).getBaseRange());
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
		m.addRangeListener(rangeListener);

		moveListener = new IMoveListener() {
			@Override
			public void onChange(Character c, int previous, int actual) {
				moveValue.setValue(actual);
				moveValue.setVisible( getStateUIVisible() && c.getMove() != ((Monster) c).getBaseMove());
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
		m.addMoveListener(moveListener);
		
		addMouseListener(mouseListenerBoard);
		addMouseListener(mouseListenerCharacter);
		
		setState(false, false);
	}
	
	@Override
	protected MouseListener getUICharacterSelectionMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				boolean uiVisible = getStateUIVisible();
				
				rangeValue.setVisible(uiVisible);
				moveValue.setVisible(uiVisible);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				Monster m = (Monster) boardElement;
				boolean uiVisible = getStateUIVisible();
				
				rangeValue.setVisible(uiVisible && m.getRange() != m.getBaseRange());
				moveValue.setVisible(uiVisible && m.getMove() != m.getBaseMove());
			}
		};
	}
	
	@Override
	protected void refreshUIInFunctionOfTheState()
	{
		super.refreshUIInFunctionOfTheState();

		Monster m = (Monster) this.boardElement;
		boolean uiVisible = getStateUIVisible();
			
		rangeValue.setVisible(uiVisible && m.getRange() != m.getBaseRange());
		armorValue.setVisible(uiVisible && m.getArmor() > 0);
		healthValue.setVisible(uiVisible);
		moveValue.setVisible(uiVisible && m.getMove() != m.getBaseMove());
	}

}
