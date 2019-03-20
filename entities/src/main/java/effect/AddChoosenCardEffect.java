package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;

import boardelement.Character;
import boardelement.Wizard;
import condition.ICondition;
import condition.TrueCondition;
import game.Game;
import listener.ICardArrayRequestListener;
import spell.Card;
import spell.ISpell;
import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("addChoosenCardEffect")
public class AddChoosenCardEffect extends OneValueEffect
{
	private static ICardArrayRequestListener cardArrayRequestListener;
	
	private String[] cardNames;
	private ZoneType destZone;
	private ZonePick destPick;
	
	
	
	public AddChoosenCardEffect() {
		super();
	}
	
	public AddChoosenCardEffect(Target target, int value, String[] cardNames, ZoneType destZone, ZonePick destPick)
	{
		super(target, value);
		
		Preconditions.checkState(cardArrayRequestListener != null, "cardArrayRequestListener was not initialized (in static)");
		
		this.cardNames = cardNames;
		this.destZone = destZone;
		this.destPick = destPick;
	}
	
	

	public static void setCardArrayRequestListener(ICardArrayRequestListener cardArrayRequestListener) {
		AddChoosenCardEffect.cardArrayRequestListener = cardArrayRequestListener;
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
			return "all targets choose a card and add it " + (getValue() > 1? getValue() + " times ":"") + destPick.getDescriptionDest()  + destZone.getDescription() + getConstraintsDescription();
		case CHOICE:
			return "a chosen target choose a card and add it " + (getValue() > 1? getValue() + " times ":"") + destPick.getDescriptionDest()  + destZone.getDescription() + getConstraintsDescription();
		case MORE:
			return "choose a card and add it " + getValue() + " more time" + (getValue() > 1? "s ":" ") + destPick.getDescriptionDest()  + destZone.getDescription();
		case RANDOM:
			return "a random target choose a card and add it " + (getValue() > 1? getValue() + " times ":"") + destPick.getDescriptionDest()  + destZone.getDescription() + getConstraintsDescription();
		case YOU:
			return "choose a card and add it " + (getValue() > 1? getValue() + " times ":"") + destPick.getDescriptionDest()  + destZone.getDescription();
		default:
			return "";		
		}
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell)
	{
		if(character instanceof Wizard)
		{
			int value = Math.min(getValue(), cardNames.length);
			
			Card[] cards = cardArrayRequestListener.chooseCards(value, game.getCards(cardNames));
			
			((Wizard) character).getZoneGroup().add(cards, destZone, destPick);
		}
	}
}
