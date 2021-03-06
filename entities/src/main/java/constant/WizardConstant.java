package constant;

import com.google.common.base.Preconditions;

public class WizardConstant
{
	private int maxHealth;
	private int initArmor;
	private int baseMove;
	private int baseRange;
	private int baseMana;
	private int nbInitCard;
	
	

	public WizardConstant() {
		//Empty constructor for jackson
	}
	public WizardConstant(int maxHealth, int initArmor, int baseMove, int baseRange, int baseMana, int nbInitCard) {
		Preconditions.checkArgument(maxHealth > 0, "maxHealth was %s but expected strictly positive", maxHealth);
		Preconditions.checkArgument(initArmor >= 0, "initArmor was %s but expected positive", initArmor);
		Preconditions.checkArgument(baseMove > 0, "baseMove was %s but expected strictly positive", baseMove);
		Preconditions.checkArgument(baseRange > 0, "baseRange was %s but expected strictly positive", baseRange);
		Preconditions.checkArgument(baseMana > 0, "baseMana was %s but expected strictly positive", baseMana);
		Preconditions.checkArgument(nbInitCard > 0, "nbInitCard was %s but expected strictly positive", nbInitCard);
		
		this.maxHealth = maxHealth;
		this.initArmor = initArmor;
		this.baseMove = baseMove;
		this.baseRange = baseRange;
		this.baseMana = baseMana;
		this.nbInitCard = nbInitCard;
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
	
	public int getNbInitCard() {
		return nbInitCard;
	}
	
	@Override
	public String toString() {
		return "WizardConstant [maxHealth=" + maxHealth + ", initArmor=" + initArmor + ", baseMove=" + baseMove
				+ ", baseRange=" + baseRange + ", baseMana=" + baseMana + ", nbInitCard=" + nbInitCard + "]";
	}
}
