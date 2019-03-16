package effect;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import boardelement.Character;
import condition.ICondition;
import game.Game;
import spell.ISpell;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import zone.ZonePick;
import zone.ZoneType;

@JsonTypeName("predictionEffect")
@JsonIgnoreProperties({ "target" })
public class PredictionEffect extends TargetableEffect {

	@JsonIgnore
	private RevealEffect revealEffect;
	@JsonIgnore
	private YouCanEffect youCanEffect;
	
	public PredictionEffect() {
		super(new Target(new TargetConstraint[0], TargetType.YOU));
		revealEffect = new RevealEffect(getTarget(), 1, ZoneType.DECK, ZonePick.TOP);
		youCanEffect = new YouCanEffect(
			new IEffect[0],
			new CardEffect(getTarget(), 1, ZoneType.DECK, ZonePick.TOP, ZoneType.DECK, ZonePick.BOTTOM)
			);
	}

	@Override
	public ICondition matchingCondition() {
		return revealEffect.matchingCondition();
	}

	@Override
	public String getDescription() {
		return "prediction";
	}

	@Override
	public void prepare(Game game, ISpell spell) {
		revealEffect.prepare(game, spell);
		youCanEffect.prepare(game, spell);
	}

	@Override
	public void clean() {
		revealEffect.clean();
		youCanEffect.clean();
	}

	@Override
	protected void applyOn(Character character, Game game, ISpell spell) {
		revealEffect.applyOn(character, game, spell);
		youCanEffect.apply(game, spell);
	}

}
