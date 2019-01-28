package zone;

import java.util.PriorityQueue;
import java.util.Queue;

import boardelement.Wizard;
import game.Game;
import spell.Card;
import spell.ISpell;

public class CastZone
{
	private class SpellWithOwner
	{
		private ISpell spell;
		private Wizard owner;
		private ZoneType zoneType;
		private ZonePick zonePick;
		
		
		public SpellWithOwner(Card card, Wizard owner, ZoneType zoneType, ZonePick zonePick)
		{
			this.spell = card;
			this.zoneType = zoneType;
			this.zonePick = zonePick;
			this.owner = owner;
		}
		public SpellWithOwner(ISpell spell)
		{
			this.spell = spell;
			this.zoneType = null;
			this.zonePick = null;
			this.owner = null;
		}


		public ISpell getSpell() {
			return spell;
		}

		public ZoneType getZoneType() {
			return zoneType;
		}
		public void setZoneType(ZoneType zoneType) {
			this.zoneType = zoneType;
		}
		
		public ZonePick getZonePick() {
			return zonePick;
		}
		public void setZonePick(ZonePick zonePick) {
			this.zonePick = zonePick;
		}
		
		public Wizard getOwner() {
			return owner;
		}
	}
	
	
	
	Queue<SpellWithOwner> spells;
	
	
	
	public CastZone()
	{
		spells = new PriorityQueue<>();
	}
	
	
	
	public ISpell getCurrentSpell()
	{
		return spells.peek().getSpell();
	}
	
	public void setCurrentZoneTypeDest(ZoneType zoneType)
	{
		spells.peek().setZoneType(zoneType);
	}
	
	public void setCurrentZoneTypeDest(ZonePick zonePick)
	{
		spells.peek().setZonePick(zonePick);
	}
	
	public void add(Card card, Wizard owner, ZoneType zoneType, ZonePick zonePick)
	{
		card.setRevealed(true);
		spells.add(new SpellWithOwner(card, owner, zoneType, zonePick));
	}
	
	public void add(Card card, Wizard owner)
	{
		add(card, owner, ZoneType.DISCARD, ZonePick.DEFAULT);
	}
	
	public void add(ISpell spell)
	{
		spells.add(new SpellWithOwner(spell));
	}
	
	public boolean isEmpty()
	{
		return spells.isEmpty();
	}
	
	public void cast(Game game)
	{
		ISpell currentSpell = getCurrentSpell();
		
		//Cast the spell
		currentSpell.cast(game);
		
		//If it's a card, return it to its owner
		if(currentSpell instanceof Card)
		{
			spells.peek().getOwner().getZoneGroup().add(
					new Card[] {(Card) currentSpell},
					spells.peek().getZoneType(),
					spells.peek().getZonePick());
		}
		
	}
	
}
