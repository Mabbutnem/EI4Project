package boardelement;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import constant.CorpseConstant;
import utility.Proba;

@JsonTypeName("corpse")
public class Corpse implements IBoardElement
{
	private static CorpseConstant corpseConstant;
	
	private Monster monster;
	private boolean willReborn;
	private int counterToReborn;
	
	
	
	public Corpse()
	{
		super();
	}
	
	public Corpse(Monster monster)
	{
		Preconditions.checkState(corpseConstant != null, "corpseConstant was not initialised (in static)");
		
		Preconditions.checkArgument(monster != null, "monster was null but expected not null");
		
		monster.reset();
		this.monster = monster;
		willReborn = Proba.willHappen(monster.getRebornProbability());
		counterToReborn = 0;
	}



	public static CorpseConstant getCorpseConstant() { return corpseConstant; }
	public static void setCorpseConstant(CorpseConstant corpseConstant) { Corpse.corpseConstant = corpseConstant; }



	public Monster getMonster() {
		return monster;
	}



	public boolean isWillReborn() {
		return willReborn;
	}



	public int getCounterToReborn() {
		return counterToReborn;
	}
	
	public void incrCounterToReborn() {
		counterToReborn++;
	}
	
	public boolean counterReachedReborn() {
		return counterToReborn >= corpseConstant.getNbTurnToReborn();
	}
	
	
	
	
	
}
