package boardelement;

import com.google.common.base.Preconditions;

//NOT FINISHED !!
public abstract class Character implements IBoardElement
{
	private static final String LOSSILLEGALVALUEMESSAGE = "Loss was %s but expected strictly positive";
	private static final String GAINILLEGALVALUEMESSAGE = "Gain was %s but expected strictly positive";
	private static final String DAMAGEILLEGALVALUEMESSAGE = "Damage was %s but expected strictly positive";
	

	private boolean alive;
	private int health;
	private int armor;
	private int move;
	private int dash;
	private int range;
	private boolean freeze;
	
	
	
	
	public Character()
	{
		health = 0;
		armor = 0;
		move = 0;
		dash = 0;
		range = 0;
		freeze = false;
		setAlive(true);
	}

	
	
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
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
		Preconditions.checkArgument(loss > 0, LOSSILLEGALVALUEMESSAGE, loss);
		
		setHealth(getHealth() - loss);
	}
	
	public void gainHealth(int gain)
	{
		Preconditions.checkArgument(gain > 0, GAINILLEGALVALUEMESSAGE, gain);
		
		setHealth(getHealth() + gain);
	}
	
	public void inflictDirectDamage(int damage)
	{
		Preconditions.checkArgument(damage > 0, DAMAGEILLEGALVALUEMESSAGE, damage);
		
		loseHealth(damage);
	}
	
	// return the direct damage dealth
	public int inflictDamage(int damage)
	{
		Preconditions.checkArgument(damage > 0, DAMAGEILLEGALVALUEMESSAGE, damage);
		
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
		Preconditions.checkArgument(damage > 0, DAMAGEILLEGALVALUEMESSAGE, damage);
		
		int armorLoss = Math.min(getArmor(), damage);
		damage -= armorLoss;
		loseArmor(armorLoss);
		inflictDirectDamage(damage);
		return damage;
		
		/*if(damage > getArmor())
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
		}*/
	}

	
	
	
	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = Math.max(0, armor);
	}
	
	public void loseArmor(int loss)
	{
		Preconditions.checkArgument(loss > 0, LOSSILLEGALVALUEMESSAGE, loss);
	
		setArmor(getArmor() - loss);
	}
	
	public void gainArmor(int gain)
	{
		Preconditions.checkArgument(gain > 0, GAINILLEGALVALUEMESSAGE, gain);
		
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
		Preconditions.checkArgument(loss > 0, LOSSILLEGALVALUEMESSAGE, loss);
	
		setMove(getMove() - loss);
	}
	
	public void gainMove(int gain)
	{
		Preconditions.checkArgument(gain > 0, GAINILLEGALVALUEMESSAGE, gain);
		
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
		Preconditions.checkArgument(loss > 0, LOSSILLEGALVALUEMESSAGE, loss);
		
		setDash(getDash() - loss);
	}
	
	public void gainDash(int gain)
	{
		Preconditions.checkArgument(gain > 0, GAINILLEGALVALUEMESSAGE, gain);
		
		setDash(getDash() + gain);
	}




	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = Math.max(0, range);
	}
	
	public void loseRange(int loss)
	{
		Preconditions.checkArgument(loss > 0, LOSSILLEGALVALUEMESSAGE, loss);
		
		setRange(getRange() - loss);
	}
	
	public void gainRange(int gain)
	{
		Preconditions.checkArgument(gain > 0, GAINILLEGALVALUEMESSAGE, gain);
		
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
}
