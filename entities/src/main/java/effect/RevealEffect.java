package effect;

import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import boardelement.Wizard;
import condition.HigherCardCondition;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("revealEffect")
public class RevealEffect extends OneValueEffect {
	
	private ZoneType zoneSource;
	private ZonePick pickSource;
	
	public RevealEffect(Target target, int value, ZoneType zoneSource, ZonePick pickSource) {
		super(target, value);
		this.zoneSource = zoneSource;
		this.pickSource = pickSource;
	}

	@Override
	public ICondition matchingCondition() {
		return new HigherCardCondition(getValue(), zoneSource);
	}

	@Override
	public String getDescription() {
		return "reveal " + getValue() + " card" + (getValue()>1? "s ":" ") 
				+ pickSource.getDescriptionSource() + zoneSource.getDescription();
	}

	@Override
	public void prepare(Game game, ISpell spell) { 
		//Must empty
	}

	@Override
	public void clean() { 
		//Must empty
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		if(character instanceof Wizard) {
			((Wizard) character).getZoneGroup().reveal(getValue(), zoneSource, pickSource);
		}
	}
}
