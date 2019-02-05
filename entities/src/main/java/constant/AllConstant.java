package constant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import boardelement.Wizard;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AllConstant
{
	private WizardConstant wizardConstant;

	public AllConstant() {
		//Empty constructor for jackson
	}
	public AllConstant(WizardConstant wizardConstant){
		this.wizardConstant = wizardConstant;
	}
	
	public void initAllConstant()
	{
		Wizard.setWizardConstant(wizardConstant);
	}
	
}
