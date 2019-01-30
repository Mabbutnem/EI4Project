package zone;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.base.Preconditions;

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

		public SpellWithOwner(Card card, Wizard owner, ZoneType zoneType, ZonePick zonePick) {
			this.spell = card;
			this.zoneType = zoneType;
			this.zonePick = zonePick;
			this.owner = owner;
		}

		public SpellWithOwner(ISpell spell) {
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

	private Queue<SpellWithOwner> spells;

	public CastZone()
	{
		spells = new LinkedBlockingQueue<>();
	}

	public ISpell getCurrentSpell()
	{
		if (!isEmpty()) { return spells.peek().getSpell(); }
		return null;
	}

	public ZoneType getCurrentZoneTypeDest()
	{
		if(!isEmpty()) { return spells.peek().getZoneType(); }
		return null;
	}
	
	public void setCurrentZoneTypeDest(ZoneType zoneType)
	{
		if (!isEmpty())
		{
			if(getCurrentSpell() instanceof Card)
			{
				Preconditions.checkArgument(zoneType != null, "zoneType was null but expected not null");
			}
			spells.peek().setZoneType(zoneType);
		}
	}

	public ZonePick getCurrentZonePickDest()
	{
		if(!isEmpty()) { return spells.peek().getZonePick(); }
		return null;
	}

	public void setCurrentZonePickDest(ZonePick zonePick)
	{
		if (!isEmpty())
		{
			if(getCurrentSpell() instanceof Card)
			{
				Preconditions.checkArgument(zonePick != null, "zonePick was null but expected not null");
			}
			spells.peek().setZonePick(zonePick);
		}
	}
	
	public Wizard getCurrentOwner()
	{
		if(!isEmpty()) { return spells.peek().getOwner(); }
		return null;
	}

	public void add(Card card, Wizard owner, ZoneType zoneType, ZonePick zonePick)
	{
		Preconditions.checkArgument(card != null, "card was null but expected not null");
		Preconditions.checkArgument(owner != null, "owner was null but expected not null");
		Preconditions.checkArgument(zoneType != null, "zoneType was null but expected not null");
		Preconditions.checkArgument(zonePick != null, "zonePick was null but expected not null");
		
		card.setRevealed(true);
		spells.add(new SpellWithOwner(card, owner, zoneType, zonePick));
	}

	public void add(Card card, Wizard owner)
	{
		add(card, owner, ZoneType.DISCARD, ZonePick.DEFAULT);
	}

	public void add(ISpell spell)
	{
		Preconditions.checkArgument(spell != null, "spell was null but expected not null");
		
		spells.add(new SpellWithOwner(spell));
	}

	public boolean isEmpty()
	{
		return spells.isEmpty();
	}

	public void cast(Game game)
	{
		Preconditions.checkArgument(game != null, "game was null but expected not null");
		
		if (!isEmpty())
		{
			ISpell currentSpell = getCurrentSpell();

			// Cast the spell
			currentSpell.cast(game);

			// If it's a card, return it to its owner
			if (currentSpell instanceof Card)
			{
				getCurrentOwner().getZoneGroup().add(
						new Card[] { (Card) currentSpell },
						getCurrentZoneTypeDest(),
						getCurrentZonePickDest());
			}
			
			// Delete the current SpellWithOwner
			spells.poll();
		}
	}

}
