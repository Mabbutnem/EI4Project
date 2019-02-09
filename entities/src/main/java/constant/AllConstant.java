package constant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import boardelement.Corpse;
import boardelement.Wizard;
import game.Game;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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
	
}
