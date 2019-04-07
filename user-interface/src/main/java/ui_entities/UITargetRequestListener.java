package ui_entities;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import boardelement.Character;
import game.Game;
import listener.ITargetRequestListener;
import target.TargetConstraint;

public class UITargetRequestListener implements ITargetRequestListener
{
	private static final int DIALOG_SIZE_X = 20;
	private static final int DIALOG_SIZE_Y = 60;
	
	private Container container;
	
	
	
	public UITargetRequestListener(Container container)
	{
		this.container = container;
	}
	
	

	@Override
	public Character chooseTarget(Game game, TargetConstraint[] constraints)
	{
		String desc = "";
		List<TargetConstraint> constraintList = Arrays.asList(constraints);
		if(constraintList.contains(TargetConstraint.NOTYOU)) {
			desc += "not you";
		}
		if(constraintList.contains(TargetConstraint.NOTALLY)) {
			if(desc.length() > 0) { desc += ", "; }
			desc += "not ally";
		}
		if(constraintList.contains(TargetConstraint.NOTENEMY)) {
			if(desc.length() > 0) { desc += ", "; }
			desc += "not enemy";
		}
		if(desc.length() > 0) {
			desc = " (" + desc + ")";
		}
		
		UIBoardElementArray uiBoardElementArray = new UIBoardElementArray(game, false);
		uiBoardElementArray.setTitle("Choose a target" + desc);
		
		UIManager.put("OptionPane.minimumSize", new Dimension(uiBoardElementArray.getWidth() + DIALOG_SIZE_X, 
																uiBoardElementArray.getHeight() + DIALOG_SIZE_Y));
		
		Character selectedCharacter;
		
		do
		{
			JOptionPane.showInternalConfirmDialog(container, uiBoardElementArray, "Make a choice", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		
			selectedCharacter = uiBoardElementArray.getSelectedCharacter();
			
			if(!game.isValidTargetForCurrentCharacter(selectedCharacter, constraints)) {
				uiBoardElementArray.setTitle("Choose must choose a valid target" + desc);
			}
		
		}while(!game.isValidTargetForCurrentCharacter(selectedCharacter, constraints));
		
		uiBoardElementArray.clearListeners();
		
		return selectedCharacter;
	}

}
