package ui_entities;

public class UINullBoardElement extends UIBoardElement
{
	private static final long serialVersionUID = -223515430110232997L;

	public UINullBoardElement()
	{
		super(null);
		
		setCanBeSelected(false);
		
		nameLabel.setVisible(false);
		putImageHereLabel.setVisible(false);
	}

	@Override
	protected void refreshUIInFunctionOfTheState()
	{
		setAllBackgrounds(selected ? UIConstants.SELECTED_COLOR : getStateBaseColor());
	}
}
