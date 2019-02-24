package condition;

import boardelement.Wizard;
import game.Game;
import zone.ZoneType;

public class CardNumberCondition implements ICondition
{
	private int cardNumber;
	private ZoneType zoneSource;
	
	

	public CardNumberCondition(int cardNumber, ZoneType zoneSource)
	{
		this.cardNumber = cardNumber;
		this.zoneSource = zoneSource;
	}
	
	

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean test(Game game)
	{
		if(game.getCurrentCharacter() instanceof Wizard)
		{
			return ((Wizard) game.getCurrentCharacter()).getZoneGroup().getCards(zoneSource).length >= cardNumber;
		}
		
		return false;
	}

}
