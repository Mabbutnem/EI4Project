package boardelement;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import constant.WizardConstant;
import javafx.collections.ListChangeListener;
import spell.Card;
import spell.Power;
import utility.MapConverter;
import zone.ZoneGroup;
import zone.ZoneType;

public class Wizard extends Character
{
	private static WizardConstant wizardConstant;
	
	private int mana;
	private boolean transformed;
	private String name;
	private Power basePower;
	private Power transformedPower;
	@Autowired
	private ZoneGroup zoneGroup;
	
	private ListChangeListener<Card> deathWhenDeckIsEmptyListener;

	
	
	//cards: all the cards from the JSON file
	public Wizard(WizardFactory wizardFactory, Card[] cards)
	{
		super();
		Preconditions.checkState(wizardConstant != null, "wizardConstant was not initialised (in static)");
		
		Preconditions.checkArgument(wizardFactory != null, "wizardFactory was null but expected not null");
		
		setHealth(wizardConstant.getMaxHealth());
		setArmor(wizardConstant.getInitArmor());
		resetRange();
		resetMove();
		resetMana();
		
		transformed = false;
		
		name = wizardFactory.getName();
		basePower = wizardFactory.getBasePower();
		transformedPower = wizardFactory.getTransformedPower();
		
		//On créé zoneGroup
		zoneGroup = new ZoneGroup(
				convertWizardFactoryCardsToArrayCards(wizardFactory, cards));
		
		//Si un wizard n'à plus de carte dans son deck, il meurt
		deathWhenDeckIsEmptyListener = (arg0 -> 
			{
				arg0.next();
				if(arg0.wasRemoved() && arg0.getList().isEmpty())
				{
					setAlive(false);
				}
			});
		zoneGroup.getCardsView(ZoneType.DECK).addListener(deathWhenDeckIsEmptyListener);
	}
	
	
	
	//cards: all the cards from the JSON file
	private Card[] convertWizardFactoryCardsToArrayCards(WizardFactory wizardFactory, Card[] cards)
	{
		Preconditions.checkArgument(wizardFactory != null, "wizard was null but expected not null");
		Preconditions.checkArgument(cards != null, "wizard was null but expected not null");
		
		return Arrays.asList(
				MapConverter.getObjectsFromMapNamesQuantities(wizardFactory.getMapCardsQuantity(), cards)
				).toArray(new Card[0]); //Convert INamedObject[] to Card[]
	}
	
	
	
	public static WizardConstant getWizardConstant() { return wizardConstant; }
	public static void setWizardConstant(WizardConstant wizardConstant) { Wizard.wizardConstant = wizardConstant; }

	
	
	public void mulligan()
	{
		zoneGroup.mulligan(wizardConstant.getNbInitCard());
	}
	
	
	
	public void untransform()
	{
		transformed = false;
	}
	
	public void transform()
	{
		transformed = true;
		setHealth(wizardConstant.getMaxHealth());
		zoneGroup.transform();
		mulligan();
	}
	
	public boolean isTransformed()
	{
		return transformed;
	}
	
	
	
	public String getName() {
		return name;
	}

	
	
	@Override
	public void setHealth(int health)
	{
		super.setHealth(Math.min(health, wizardConstant.getMaxHealth()));
	}
	
	@Override
	protected void checkDeath()
	{
		if(transformed) {
			super.checkDeath();
		}
		else {
			if(getHealth() == 0) { transform(); }
		}
	}
	
	
	
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = Math.max(0, mana);
	}
	
	public void loseMana(int loss)
	{
		Preconditions.checkArgument(loss >= 0, LOSS_ILLEGAL_VALUE_ERROR_MESSAGE, loss);
	
		setMana(getMana() - loss);
	}
	
	public void gainMana(int gain)
	{
		Preconditions.checkArgument(gain >= 0, GAIN_ILLEGAL_VALUE_ERROR_MESSAGE, gain);
		
		setMana(getMana() + gain);
	}
	
	public void resetMana()
	{
		setMana(wizardConstant.getBaseMana());
	}

	
	
	public Power getPower()
	{
		return transformed ? transformedPower : basePower;
	}
	
	
	
	public ZoneGroup getZoneGroup() {
		return zoneGroup;
	}
	
	//cards: all the cards from the JSON file
	public void resetCards(WizardFactory wizardFactory, Card[] cards)
	{
		//Le deck va être vidé mais il ne faut pas tuer le wizard !!
		zoneGroup.getCardsView(ZoneType.DECK).removeListener(deathWhenDeckIsEmptyListener);
		
		zoneGroup.reset(convertWizardFactoryCardsToArrayCards(wizardFactory, cards));
		
		//On peut de nouveau tuer le wizard quand le deck est vide
		zoneGroup.getCardsView(ZoneType.DECK).addListener(deathWhenDeckIsEmptyListener);
	}


	
	@Override
	public void resetMove() {
		setMove(wizardConstant.getBaseMove());
	}

	@Override
	public void resetRange() {
		setRange(wizardConstant.getBaseRange());
	}
}
