package constant;

import com.google.common.base.Preconditions;

public class CorpseConstant
{
	private int nbTurnToReborn;
	
	
	
	public CorpseConstant() {
		//Empty constructor for jackson
	}
	public CorpseConstant(int nbTurnToReborn)
	{
		Preconditions.checkArgument(nbTurnToReborn > 0, "nbTurnToReborn was %s but expected strictly positive", nbTurnToReborn);
		
		this.nbTurnToReborn = nbTurnToReborn;
	}
	
	
	
	public int getNbTurnToReborn() {
		return nbTurnToReborn;
	}
	
	
	
	@Override
	public String toString() {
		return "CorpseConstant [nbTurnToReborn=" + nbTurnToReborn + "]";
	}
	
}
