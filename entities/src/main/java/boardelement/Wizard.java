package boardelement;

import spell.Card;
import zone.ZoneGroup;

public class Wizard extends Character
{
	private ZoneGroup zoneGroup;

	
	
	public Wizard(WizardFactory wizardFactory, Card[] cards)
	{
	}
	
	
	
	@Override
	public void resetMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetRange() {
		// TODO Auto-generated method stub
		
	}

	
	
	public ZoneGroup getZoneGroup() {
		return zoneGroup;
	}

}
