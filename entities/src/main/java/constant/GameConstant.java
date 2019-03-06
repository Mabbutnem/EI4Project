package constant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameConstant
{
	private int boardLenght;
	private int nbWizard;
	private int levelCost;
	private int levelMaxDifficulty;
	private int nbMonstersToSpawnEachTurnMin;
	private int nbMonstersToSpawnEachTurnMax;
	private int nbMonstersMin;
	private int nbMonstersMax;

	
	public GameConstant() {
		//Empty constructor for jackson
	}
	public GameConstant(int boardLenght, int nbWizard, int levelCost, int levelMaxDifficulty,
			int nbMonstersToSpawnEachTurnMin, int nbMonstersToSpawnEachTurnMax, int nbMonstersMin, int nbMonstersMax)
	{
		Preconditions.checkArgument(boardLenght > 0, "boardLenght was %s but expected strictly positive", boardLenght);
		
		Preconditions.checkArgument(nbWizard > 0, "nbWizard was %s but expected strictly positive", nbWizard);
		Preconditions.checkArgument(nbWizard < boardLenght, "nbWizard was %s but expected fewer than %s (boardLenght)", nbWizard, boardLenght);
		
		Preconditions.checkArgument(levelCost > 0, "levelMaxCost was %s but expected strictly positive", levelCost);
		
		Preconditions.checkArgument(levelMaxDifficulty > 0, "levelMaxDifficulty was %s but expected strictly positive", levelMaxDifficulty);
		
		Preconditions.checkArgument(nbMonstersToSpawnEachTurnMin >= 0, "nbMonstersToSpawnEachTurnMin was %s but expected positive", nbMonstersToSpawnEachTurnMin);
		Preconditions.checkArgument(nbMonstersToSpawnEachTurnMax >= nbMonstersToSpawnEachTurnMin, "nbMonstersToSpawnEachTurnMax was %s"
				+ " but expected higher or equal to %s (nbMonstersToSpawnEachTurnMin)", nbMonstersToSpawnEachTurnMax, nbMonstersToSpawnEachTurnMin);
		
		Preconditions.checkArgument(nbMonstersMin > 0, "nbMonstersMin was %s but expected strictly positive");
		
		Preconditions.checkArgument(nbMonstersMax >= nbMonstersMin, "nbMonstersMax was %s"
				+ " but expected higher or equals than %s (nbMonstersMin)", nbMonstersMax, nbMonstersMin);
		Preconditions.checkArgument(nbMonstersMax < boardLenght, "nbMonstersMax was %s but expected fewer than %s (boardLenght)", nbMonstersMax, boardLenght);
		
		this.boardLenght = boardLenght;
		this.nbWizard = nbWizard;
		this.levelCost = levelCost;
		this.levelMaxDifficulty = levelMaxDifficulty;
		this.nbMonstersToSpawnEachTurnMin = nbMonstersToSpawnEachTurnMin;
		this.nbMonstersToSpawnEachTurnMax = nbMonstersToSpawnEachTurnMax;
		this.nbMonstersMin = nbMonstersMin;
		this.nbMonstersMax = nbMonstersMax;
	}
	
	
	
	public int getBoardLenght() {
		return boardLenght;
	}
	
	public int getNbWizard() {
		return nbWizard;
	}
	
	public int getLevelCost() {
		return levelCost;
	}
	
	public int getLevelMaxDifficulty() {
		return levelMaxDifficulty;
	}
	
	public int getNbMonstersToSpawnEachTurnMin() {
		return nbMonstersToSpawnEachTurnMin;
	}
	
	public int getNbMonstersToSpawnEachTurnMax() {
		return nbMonstersToSpawnEachTurnMax;
	}
	
	public int getNbMonstersMin() {
		return nbMonstersMin;
	}
	
	public int getNbMonstersMax() {
		return nbMonstersMax;
	}
	
	
}
