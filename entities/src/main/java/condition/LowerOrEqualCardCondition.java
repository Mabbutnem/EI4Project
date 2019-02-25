package condition;

import java.util.function.Predicate;

import boardelement.Wizard;
import game.Game;
import zone.ZoneType;

public class LowerOrEqualCardCondition extends CardCondition
{

	public LowerOrEqualCardCondition(int value, ZoneType zoneType)
	{
		super(value, zoneType);
	}
	
	

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate<Game> getPredicate()
	{
		return g ->
			g.getCurrentCharacter() instanceof Wizard &&
			((Wizard) g.getCurrentCharacter()).getZoneGroup().getCards(zoneType).length <= value;
	}

}
