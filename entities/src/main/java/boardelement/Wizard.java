package boardelement;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import constant.WizardConstant;
import spell.Card;
import spell.Power;
import utility.MapConverter;
import zone.ZoneGroup;

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
		Preconditions.checkArgument(loss >= 0, LOSSILLEGALVALUEMESSAGE, loss);
	
		setMana(getMana() - loss);
	}
	
	public void gainMana(int gain)
	{
		Preconditions.checkArgument(gain >= 0, GAINILLEGALVALUEMESSAGE, gain);
		
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
		zoneGroup.reset(convertWizardFactoryCardsToArrayCards(wizardFactory, cards));
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
