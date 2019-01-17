package boardelement;


//NOT FINISHED !!
public abstract class Character implements IBoardElement
{
	private static final String LOSSEXCEPTIONMESSAGE = "loss ne peut pas être inférieur ou égal à 0";
	private static final String GAINEXCEPTIONMESSAGE = "gain ne peut pas être inférieur ou égal à 0";
	
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
	}

	
	
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = Math.max(0, health);
	}
	
	public void loseHealth(int loss)
	{
		if(loss <= 0) { throw new IllegalArgumentException(LOSSEXCEPTIONMESSAGE);}
		
		setHealth(getHealth() - loss);
	}
	
	public void gainHealth(int gain)
	{
		if(gain <= 0) { throw new IllegalArgumentException(GAINEXCEPTIONMESSAGE);}
		
		setHealth(getHealth() + gain);
	}
	
	public void inflictDamage(int damage)
	{
		if(damage <= 0) { throw new IllegalArgumentException("damage ne peut pas être inférieur ou égal à 0");}
		
		if(damage > getArmor())
		{
			loseHealth(damage);
		}
		else
		{
			loseArmor(damage);
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
		if(loss <= 0) { throw new IllegalArgumentException(LOSSEXCEPTIONMESSAGE);}
	
		setArmor(getArmor() - loss);
	}
	
	public void gainArmor(int gain)
	{
		if(gain <= 0) { throw new IllegalArgumentException(GAINEXCEPTIONMESSAGE);}
		
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
		if(loss <= 0) { throw new IllegalArgumentException(LOSSEXCEPTIONMESSAGE);}
	
		setMove(getMove() - loss);
	}
	
	public void gainMove(int gain)
	{
		if(gain <= 0) { throw new IllegalArgumentException(GAINEXCEPTIONMESSAGE);}
		
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
		if(loss <= 0) { throw new IllegalArgumentException(LOSSEXCEPTIONMESSAGE);}
		
		setDash(getDash() - loss);
	}
	
	public void gainDash(int gain)
	{
		if(gain <= 0) { throw new IllegalArgumentException(GAINEXCEPTIONMESSAGE);}
		
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
		if(loss <= 0) { throw new IllegalArgumentException(LOSSEXCEPTIONMESSAGE);}
		
		setRange(getRange() - loss);
	}
	
	public void gainRange(int gain)
	{
		if(gain <= 0) { throw new IllegalArgumentException(GAINEXCEPTIONMESSAGE);}
		
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
