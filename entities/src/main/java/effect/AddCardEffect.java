package effect;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import boardelement.Wizard;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import spell.Card;
import spell.ISpell;
import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("addCardEffect")
public class AddCardEffect extends OneValueEffect
{
	private String cardName;
	private ZoneType destZone;
	private ZonePick destPick;
	
	
	
	public AddCardEffect() {
		super();
	}
	
	public AddCardEffect(Target target, int value, String cardName, ZoneType destZone, ZonePick destPick)
	{
		super(target, value);
		this.cardName = cardName;
		this.destZone = destZone;
		this.destPick = destPick;
	}
	
	

	@Override
	public ICondition matchingCondition()
	{
		return new TrueCondition();
	}

	@Override
	public String getDescription() {
		switch(getTarget().getType())
		{
		case AREA:
			return "all targets add " + getValue() + " " + cardName + " " + destPick.getDescriptionDest()  + destZone.getDescription() + getConstraintsDescription();
		case CHOICE:
			return "a chosen target add " + getValue() + " " + cardName + " " + destPick.getDescriptionDest()  + destZone.getDescription() + getConstraintsDescription();
		case MORE:
			return "add " + getValue() + " more " + cardName + " " + destPick.getDescriptionDest()  + destZone.getDescription();
		case RANDOM:
			return "a random target add " + getValue() + " " + cardName + " " + destPick.getDescriptionDest()  + destZone.getDescription() + getConstraintsDescription();
		case YOU:
			return "add " + getValue() + " " + cardName + " " + destPick.getDescriptionDest()  + destZone.getDescription();
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell)
	{
		if(character instanceof Wizard)
		{
			List<Card> cardList = new LinkedList<>();
			for(int i = 0; i < getValue(); i++) { cardList.add(game.getCard(cardName)); }
			
			((Wizard) character).getZoneGroup().add(cardList.toArray(new Card[0]), destZone, destPick);
		}
	}

}
