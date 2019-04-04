package ui_entities;

import java.awt.Color;
import java.awt.event.MouseListener;

import boardelement.Corpse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UICorpse extends UIBoardElement
{
	private static final long serialVersionUID = 2077730586321395356L;

	
	
	private UIValue counterToReachRebornValue;
	private final ChangeListener<Number> counterToReachRebornListener;
	
	
	
	public UICorpse(Corpse corpseInput)
	{
		super(corpseInput);
		
		Corpse c = (Corpse) this.boardElement;

		nameLabel.setText(c.getMonster().getName() + " corpse");
		putImageHereLabel.setText("_..._");

		
		counterToReachRebornValue = new UIValue(SIZE_HEADER, SIZE_HEADER, 0, "Turns");
		counterToReachRebornValue.setAllForeground(new Color(0, 0, 0));
		counterToReachRebornValue.setLocation((SIZE_X-SIZE_HEADER)/2, 0);
		counterToReachRebornValue.setVisible(false);
		add(counterToReachRebornValue);
		
		counterToReachRebornValue.setValue(c.getCounterToReborn());
		counterToReachRebornListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				counterToReachRebornValue.setValue(newValue.intValue());
				counterToReachRebornValue.setVisible(getStateUIVisible());
			}
		};
		c.addListener(counterToReachRebornListener);
		

		addMouseListener(mouseListenerBoard);
		
		setState(false, false);
	}
	
	@Override
	public void clearListeners() {
		Corpse c = (Corpse) boardElement;

		c.removeListener(counterToReachRebornListener);
	}
	
	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		counterToReachRebornValue.addMouseListener(l);
	}

	@Override
	protected void refreshUIInFunctionOfTheState()
	{
		super.refreshUIInFunctionOfTheState();

		boolean uiVisible = getStateUIVisible();
			
		counterToReachRebornValue.setVisible(uiVisible);
	}

}
