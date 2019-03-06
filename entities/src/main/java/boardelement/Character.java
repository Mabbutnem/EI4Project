package boardelement;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.google.common.base.Preconditions;

import characterlistener.*;
import effect.Word;

//NOT FINISHED !!
public abstract class Character implements IBoardElement
{
	protected static final String LOSS_ILLEGAL_VALUE_ERROR_MESSAGE = "Loss was %s but expected positive";
	protected static final String GAIN_ILLEGAL_VALUE_ERROR_MESSAGE = "Gain was %s but expected positive";
	private static final String DAMAGE_ILLEGAL_VALUE_ERROR_MESSAGE = "Damage was %s but expected positive";

	private boolean alive;
	private int health;
	private int armor;
	private int move;
	private int dash;
	private int range;
	private boolean hasDashed;
	private boolean freeze;
	private List<Word> words;
	
	private EventListenerList listeners;
	
	
	
	public Character()
	{
		setFreeze(false);
		setAlive(true);
		setDash(0);
		resetHasDashed();
		words = new LinkedList<>();
		
		listeners = new EventListenerList();
	}




	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		boolean prevAlive = this.alive;
		
		this.alive = alive;
		
		fireAliveChanged(prevAlive, alive);
	}
	
	public void addAliveListener(IAliveListener listener)
	{
		listeners.add(IAliveListener.class, listener);
	}
	
	public void removeAliveListener(IAliveListener listener)
	{
		listeners.remove(IAliveListener.class, listener);
	}
	
	public IAliveListener[] getAliveListeners()
	{
		return listeners.getListeners(IAliveListener.class);
	}
	
	private void fireAliveChanged(boolean prevAlive, boolean actualAlive)
	{
		if(prevAlive != actualAlive)
		{
			for(IAliveListener listener : getAliveListeners())
			{
				listener.onChange(this, actualAlive);
			}
		}
	}




	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		int prevHealth = this.health;
		
		this.health = Math.max(0, health);
		checkDeath();
		
		fireHealthChanged(prevHealth, this.health);
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
	
	public void addHealthListener(IHealthListener listener)
	{
		listeners.add(IHealthListener.class, listener);
	}
	
	public void removeHealthListener(IHealthListener listener)
	{
		listeners.remove(IHealthListener.class, listener);
	}
	
	public IHealthListener[] getHealthListeners()
	{
		return listeners.getListeners(IHealthListener.class);
	}
	
	private void fireHealthChanged(int prev, int actual)
	{
		if(actual > prev)
		{
			for(IHealthListener listener : getHealthListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onGain(this, prev, actual);
			}
		}
		
		if(actual < prev)
		{
			for(IHealthListener listener : getHealthListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onLoss(this, prev, actual);
			}
		}
	}
	
	
	
	
	public int inflictDirectDamage(int damage)
	{
		Preconditions.checkArgument(damage >= 0, DAMAGE_ILLEGAL_VALUE_ERROR_MESSAGE, damage);
		
		int realDamage = Math.min(getHealth(), damage);
		
		loseHealth(realDamage);
		
		return realDamage;
	}
	
	// return the direct damage dealth
	public int inflictDamage(int damage)
	{
		Preconditions.checkArgument(damage >= 0, DAMAGE_ILLEGAL_VALUE_ERROR_MESSAGE, damage);
		
		if(damage > getArmor())
		{
			return inflictDirectDamage(damage);
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
			return inflictDirectDamage(damage);
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
		int prevArmor = this.armor;
		
		this.armor = Math.max(0, armor);
		
		fireArmorChanged(prevArmor, this.armor);
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
	
	public void addArmorListener(IArmorListener listener)
	{
		listeners.add(IArmorListener.class, listener);
	}
	
	public void removeArmorListener(IArmorListener listener)
	{
		listeners.remove(IArmorListener.class, listener);
	}
	
	public IArmorListener[] getArmorListeners()
	{
		return listeners.getListeners(IArmorListener.class);
	}
	
	private void fireArmorChanged(int prev, int actual)
	{
		if(actual > prev)
		{
			for(IArmorListener listener : getArmorListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onGain(this, prev, actual);
			}
		}
		
		if(actual < prev)
		{
			for(IArmorListener listener : getArmorListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onLoss(this, prev, actual);
			}
		}
	}

	
	
	
	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		int prevMove = this.move;
		
		this.move = Math.max(0, move);
		
		fireMoveChanged(prevMove, this.move);
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
	
	public void addMoveListener(IMoveListener listener)
	{
		listeners.add(IMoveListener.class, listener);
	}
	
	public void removeMoveListener(IMoveListener listener)
	{
		listeners.remove(IMoveListener.class, listener);
	}
	
	public IMoveListener[] getMoveListeners()
	{
		return listeners.getListeners(IMoveListener.class);
	}

	private void fireMoveChanged(int prev, int actual)
	{
		if(actual > prev)
		{
			for(IMoveListener listener : getMoveListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onGain(this, prev, actual);
			}
		}
		
		if(actual < prev)
		{
			for(IMoveListener listener : getMoveListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onLoss(this, prev, actual);
			}
		}
	}
	
	
	
	
	public int getDash() {
		return dash;
	}

	public void setDash(int dash) {
		int prevDash = this.dash;
		
		this.dash = Math.max(0, dash);
		
		fireDashChanged(prevDash, this.dash);
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
	
	public void setHasDashed(boolean hasDashed){
		this.hasDashed = hasDashed;
	}

	public boolean hasDashed() {
		return hasDashed;
	}

	public void resetHasDashed() {
		hasDashed = false;
	}
	
	public void addDashListener(IDashListener listener)
	{
		listeners.add(IDashListener.class, listener);
	}
	
	public void removeDashListener(IDashListener listener)
	{
		listeners.remove(IDashListener.class, listener);
	}
	
	public IDashListener[] getDashListeners()
	{
		return listeners.getListeners(IDashListener.class);
	}
	
	private void fireDashChanged(int prev, int actual)
	{
		if(actual > prev)
		{
			for(IDashListener listener : getDashListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onGain(this, prev, actual);
			}
		}
		
		if(actual < prev)
		{
			for(IDashListener listener : getDashListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onLoss(this, prev, actual);
			}
		}
	}




	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		int prevRange = this.range;
		
		this.range = Math.max(0, range);
		
		fireRangeChanged(prevRange, this.range);
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
	
	public void addRangeListener(IRangeListener listener)
	{
		listeners.add(IRangeListener.class, listener);
	}
	
	public void removeRangeListener(IRangeListener listener)
	{
		listeners.remove(IRangeListener.class, listener);
	}
	
	public IRangeListener[] getRangeListeners()
	{
		return listeners.getListeners(IRangeListener.class);
	}
	
	private void fireRangeChanged(int prev, int actual)
	{
		if(actual > prev)
		{
			for(IRangeListener listener : getRangeListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onGain(this, prev, actual);
			}
		}
		
		if(actual < prev)
		{
			for(IRangeListener listener : getRangeListeners())
			{
				listener.onChange(this, prev, actual);
				listener.onLoss(this, prev, actual);
			}
		}
	}




	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		boolean prevFreeze = this.freeze;
		
		this.freeze = freeze;
		
		fireFreezeChanged(prevFreeze, this.freeze);
	}
	
	public void resetFreeze()
	{
		setFreeze(false);
	}
	
	public void addFreezeListener(IFreezeListener listener)
	{
		listeners.add(IFreezeListener.class, listener);
	}
	
	public void removeFreezeListener(IFreezeListener listener)
	{
		listeners.remove(IFreezeListener.class, listener);
	}
	
	public IFreezeListener[] getFreezeListeners()
	{
		return listeners.getListeners(IFreezeListener.class);
	}
	
	private void fireFreezeChanged(boolean prev, boolean actual)
	{
		if(prev != actual)
		{
			for(IFreezeListener listener : getFreezeListeners())
			{
				listener.onChange(this, actual);
			}
		}
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
