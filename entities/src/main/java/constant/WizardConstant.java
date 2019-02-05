package constant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WizardConstant
{
	private int maxHealth;
	private int initArmor;
	private int baseMove;
	private int baseRange;
	private int baseMana;
	
	

	public WizardConstant() {
		//Empty constructor for jackson
	}



	public int getMaxHealth() {
		return maxHealth;
	}

	public int getInitArmor() {
		return initArmor;
	}

	public int getBaseMove() {
		return baseMove;
	}

	public int getBaseRange() {
		return baseRange;
	}

	public int getBaseMana() {
		return baseMana;
	}
}
