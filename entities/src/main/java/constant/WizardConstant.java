package constant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

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
	public WizardConstant(int maxHealth, int initArmor, int baseMove, int baseRange, int baseMana) {
		Preconditions.checkState(maxHealth > 0, "maxHealth was %s but expected strictly positive", maxHealth);
		Preconditions.checkState(initArmor >= 0, "initArmor was %s but expected positive", initArmor);
		Preconditions.checkState(baseMove > 0, "baseMove was %s but expected strictly positive", baseMove);
		Preconditions.checkState(baseRange > 0, "baseRange was %s but expected strictly positive", baseRange);
		Preconditions.checkState(baseMana > 0, "baseMana was %s but expected strictly positive", baseMana);
		
		this.maxHealth = maxHealth;
		this.initArmor = initArmor;
		this.baseMove = baseMove;
		this.baseRange = baseRange;
		this.baseMana = baseMana;
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
