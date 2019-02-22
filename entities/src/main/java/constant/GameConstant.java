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
	private float boardDensityLimit;

	
	public GameConstant() {
		//Empty constructor for jackson
	}
	public GameConstant(int boardLenght, int nbWizard, int levelCost, int levelMaxDifficulty,
			int nbMonstersToSpawnEachTurnMin, int nbMonstersToSpawnEachTurnMax, float boardDensityLimit)
	{
		Preconditions.checkArgument(nbWizard > 0, "nbWizard was %s but expected strictly positive", nbWizard);
		Preconditions.checkArgument(boardLenght > nbWizard + 1, "boardLenght was %s but expected higher than %s (nbWizard + 1) ", boardLenght, nbWizard + 1);
		Preconditions.checkArgument(levelCost > 0, "levelMaxCost was %s but expected strictly positive", levelCost);
		Preconditions.checkArgument(levelMaxDifficulty > 0, "levelMaxDifficulty was %s but expected strictly positive", levelMaxDifficulty);
		Preconditions.checkArgument(nbMonstersToSpawnEachTurnMin >= 0, "nbMonstersToSpawnEachTurnMin was %s but expected positive", nbMonstersToSpawnEachTurnMin);
		Preconditions.checkArgument(nbMonstersToSpawnEachTurnMax >= nbMonstersToSpawnEachTurnMin, "nbMonstersToSpawnEachTurnMax was %s"
				+ " but expected higher or equal to %s (nbMonstersToSpawnEachTurnMin)", nbMonstersToSpawnEachTurnMax, nbMonstersToSpawnEachTurnMin);
		Preconditions.checkArgument(boardDensityLimit <= 1, "boardDensityLimit was %s but expected fewer or equal to 1", boardDensityLimit);
		
		float boardDensityLimitMin = ((float)(nbWizard + 1))/((float)boardLenght);
		Preconditions.checkArgument(boardDensityLimit > boardDensityLimitMin, "boardDensityLimit was %s but expected higher than %s "
				+ "(nbWizard + 1)/boardLenght", boardDensityLimit, boardDensityLimitMin);
		
		this.boardLenght = boardLenght;
		this.nbWizard = nbWizard;
		this.levelCost = levelCost;
		this.levelMaxDifficulty = levelMaxDifficulty;
		this.nbMonstersToSpawnEachTurnMin = nbMonstersToSpawnEachTurnMin;
		this.nbMonstersToSpawnEachTurnMax = nbMonstersToSpawnEachTurnMax;
		this.boardDensityLimit = boardDensityLimit;
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
	
	public float getBoardDensityLimit() {
		return boardDensityLimit;
	}
	
	
}
