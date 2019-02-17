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

	
	public GameConstant() {
		//Empty constructor for jackson
	}
	public GameConstant(int boardLenght, int nbWizard, int levelCost, int levelMaxDifficulty)
	{
		Preconditions.checkArgument(nbWizard > 0, "nbWizard was %s but expected strictly positive", nbWizard);
		Preconditions.checkArgument(boardLenght > nbWizard + 1, "boardLenght was %s but expected higher than %s (nbWizard + 1) ", boardLenght, nbWizard + 1);
		Preconditions.checkArgument(levelCost > 0, "levelMaxCost was %s but expected strictly positive", levelCost);
		Preconditions.checkArgument(levelMaxDifficulty > 0, "levelMaxDifficulty was %s but expected strictly positive", levelMaxDifficulty);
		
		this.boardLenght = boardLenght;
		this.nbWizard = nbWizard;
		this.levelCost = levelCost;
		this.levelMaxDifficulty = levelMaxDifficulty;
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
	
	
}
