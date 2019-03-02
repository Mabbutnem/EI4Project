package boardelement;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Preconditions;

import effect.Word;
import listener.IGameListener;

//NOT FINISHED !!
public abstract class Character implements IBoardElement
{
	protected static final String LOSS_ILLEGAL_VALUE_ERROR_MESSAGE = "Loss was %s but expected positive";
	protected static final String GAIN_ILLEGAL_VALUE_ERROR_MESSAGE = "Gain was %s but expected positive";
	private static final String DAMAGE_ILLEGAL_VALUE_ERROR_MESSAGE = "Damage was %s but expected positive";
	
	private static IGameListener gameListener;

	private boolean alive;
	private int health;
	private int armor;
	private int move;
	private int dash;
	private int range;
	private boolean freeze;
	private List<Word> words;
	
	
	
	public Character()
	{
		Preconditions.checkState(Character.gameListener != null, "gameListener"
				+ " was not initialised (in static)");
		
		setFreeze(false);
		setAlive(true);
		setDash(0);
		words = new LinkedList<>();
	}

	
	

	public static void setGameListener(IGameListener gameListener) {
		Character.gameListener = gameListener;
	}




	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
		if(!isAlive())
		{
			Character.gameListener.clearBoard(this);
		}
	}




	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = Math.max(0, health);
		checkDeath();
	}
	
	protected void checkDeath() {
		if(getHealth() == 0) {
			setAlive(false);
		}
	}
	
	public void loseHealth(int loss)
	{
		Preconditions.checkArgument(loss >= 0, LOSS_ILLEGAL_VALUE_ERROR_MESSAGE, loss);
		
		setHealth(getHealth() - loss);
	}
	
	public void gainHealth(int gain)
	{
		Preconditions.checkArgument(gain >= 0, GAIN_ILLEGAL_VALUE_ERROR_MESSAGE, gain);
		
		setHealth(getHealth() + gain);
	}
	
	public void inflictDirectDamage(int damage)
	{
		Preconditions.checkArgument(damage >= 0, DAMAGE_ILLEGAL_VALUE_ERROR_MESSAGE, damage);
		
		loseHealth(damage);
	}
	
	// return the direct damage dealth
	public int inflictDamage(int damage)
	{
		Preconditions.checkArgument(damage >= 0, DAMAGE_ILLEGAL_VALUE_ERROR_MESSAGE, damage);
		
		if(damage > getArmor())
		{
			inflictDirectDamage(damage);
			return damage;
		}
		else
		{
			loseArmor(damage);
			return 0;
		}
	}
	
	public int inflictAcidDamage(int damage)
	{
		Preconditions.checkArgument(damage >= 0, DAMAGE_ILLEGAL_VALUE_ERROR_MESSAGE, damage);
		
		if(damage > getArmor())
		{
			damage -= getArmor();
			setArmor(0);
			inflictDirectDamage(damage);
			return damage;
		}
		else
		{
			loseArmor(damage);
			return 0;
		}
	}

	
	
	
	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = Math.max(0, armor);
	}
	
	public void loseArmor(int loss)
	{
		Preconditions.checkArgument(loss >= 0, LOSS_ILLEGAL_VALUE_ERROR_MESSAGE, loss);
	
		setArmor(getArmor() - loss);
	}
	
	public void gainArmor(int gain)
	{
		Preconditions.checkArgument(gain >= 0, GAIN_ILLEGAL_VALUE_ERROR_MESSAGE, gain);
		
		setArmor(getArmor() + gain);
	}

	
	
	
	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = Math.max(0, move);
	}
	
	public void loseMove(int loss)
	{
		Preconditions.checkArgument(loss >= 0, LOSS_ILLEGAL_VALUE_ERROR_MESSAGE, loss);
	
		setMove(getMove() - loss);
	}
	
	public void gainMove(int gain)
	{
		Preconditions.checkArgument(gain >= 0, GAIN_ILLEGAL_VALUE_ERROR_MESSAGE, gain);
		
		setMove(getMove() + gain);
	}
	
	public abstract void resetMove();

	
	
	
	public int getDash() {
		return dash;
	}

	public void setDash(int dash) {
		this.dash = Math.max(0, dash);
	}
	
	public void loseDash(int loss)
	{
		Preconditions.checkArgument(loss >= 0, LOSS_ILLEGAL_VALUE_ERROR_MESSAGE, loss);
		
		setDash(getDash() - loss);
	}
	
	public void gainDash(int gain)
	{
		Preconditions.checkArgument(gain >= 0, GAIN_ILLEGAL_VALUE_ERROR_MESSAGE, gain);
		
		setDash(getDash() + gain);
	}




	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		int previousRange = this.range;
		this.range = Math.max(0, range);
		
		if(previousRange != this.range)
		{
			Character.gameListener.refreshRange(this);
		}
	}
	
	public void loseRange(int loss)
	{
		Preconditions.checkArgument(loss >= 0, LOSS_ILLEGAL_VALUE_ERROR_MESSAGE, loss);
		
		setRange(getRange() - loss);
	}
	
	public void gainRange(int gain)
	{
		Preconditions.checkArgument(gain >= 0, GAIN_ILLEGAL_VALUE_ERROR_MESSAGE, gain);
		
		setRange(getRange() + gain);
	}
	
	public abstract void resetRange();

	
	
	
	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}
	
	public void resetFreeze()
	{
		setFreeze(false);
	}




	public boolean containsWord(Word word)
	{
		return words.contains(word);
	}
	
	public void addWord(Word word)
	{
		if(!words.contains(word))
		{
			words.add(word);
		}
	}
	
	public void clearWords()
	{
		words.clear();
	}
}
