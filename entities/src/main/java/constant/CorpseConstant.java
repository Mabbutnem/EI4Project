package constant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CorpseConstant
{
	private int nbTurnToReborn;
	
	
	
	public CorpseConstant() {
		//Empty constructor for jackson
	}
	public CorpseConstant(int nbTurnToReborn)
	{
		Preconditions.checkArgument(nbTurnToReborn >= 0, "nbTurnToReborn was %s but expected positive", nbTurnToReborn);
		
		this.nbTurnToReborn = nbTurnToReborn;
	}
	
	
	
	public int getNbTurnToReborn() {
		return nbTurnToReborn;
	}
	
}
