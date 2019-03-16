package constant;

import boardelement.Corpse;
import boardelement.Wizard;
import game.Game;

public class AllConstant
{
	private WizardConstant wizardConstant;
	private CorpseConstant corpseConstant;
	private GameConstant gameConstant;

	public AllConstant() {
		//Empty constructor for jackson
	}
	public AllConstant(WizardConstant wizardConstant, CorpseConstant corpseConstant, GameConstant gameConstant){
		this.wizardConstant = wizardConstant;
		this.corpseConstant = corpseConstant;
		this.gameConstant = gameConstant;
	}
	
	public void initAllConstant()
	{
		Wizard.setWizardConstant(wizardConstant);
		Corpse.setCorpseConstant(corpseConstant);
		Game.setGameConstant(gameConstant);
	}
	
	@Override
	public String toString() {
		return "AllConstant [wizardConstant=" + wizardConstant + ", corpseConstant=" + corpseConstant
				+ ", gameConstant=" + gameConstant + "]";
	}
	
}
