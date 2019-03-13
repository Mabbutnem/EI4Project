package effect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import game.Game;
import spell.ISpell;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = As.PROPERTY,
		property = "effectType")
		@JsonSubTypes({
			@JsonSubTypes.Type(value = AddWordEffect.class, name = "addWordEffect"),
			@JsonSubTypes.Type(value = BanishEffect.class, name = "banishEffect"),
			@JsonSubTypes.Type(value = BurnEffect.class, name = "burnEffect"),
			@JsonSubTypes.Type(value = BurnItselfEffect.class, name = "burnItselfEffect"),
			@JsonSubTypes.Type(value = CardEffect.class, name = "cardEffect"),
			@JsonSubTypes.Type(value = CastEffect.class, name = "castEffect"),
			@JsonSubTypes.Type(value = DiscardEffect.class, name = "discardEffect"),
			@JsonSubTypes.Type(value = DrawEffect.class, name = "drawEffect"),
			@JsonSubTypes.Type(value = FreezeEffect.class, name = "freezeEffect"),
			@JsonSubTypes.Type(value = GainArmorEffect.class, name = "gainArmorEffect"),
			@JsonSubTypes.Type(value = GainDashEffect.class, name = "gainDashEffect"),
			@JsonSubTypes.Type(value = GainHealthEffect.class, name = "gainHealthEffect"),
			@JsonSubTypes.Type(value = GainManaEffect.class, name = "gainManaEffect"),
			@JsonSubTypes.Type(value = GainMoveEffect.class, name = "gainMoveEffect"),
			@JsonSubTypes.Type(value = GainRangeEffect.class, name = "gainRangeEffect"),
			@JsonSubTypes.Type(value = IfEffect.class, name = "ifEffect"),
			@JsonSubTypes.Type(value = InflictEffect.class, name = "inflictEffect"),
			@JsonSubTypes.Type(value = LoseArmorEffect.class, name = "loseArmorEffect"),
			@JsonSubTypes.Type(value = LoseDashEffect.class, name = "loseDashEffect"),
			@JsonSubTypes.Type(value = LoseHealthEffect.class, name = "loseHealthEffect"),
			@JsonSubTypes.Type(value = LoseManaEffect.class, name = "loseManaEffect"),
			@JsonSubTypes.Type(value = LoseMoveEffect.class, name = "loseMoveEffect"),
			@JsonSubTypes.Type(value = LoseRangeEffect.class, name = "loseRangeEffect"),
			@JsonSubTypes.Type(value = PredictionEffect.class, name = "predictionEffect"),
			@JsonSubTypes.Type(value = PullEffect.class, name = "pullEffect"),
			@JsonSubTypes.Type(value = PushEffect.class, name = "pushEffect"),
			@JsonSubTypes.Type(value = PutAfterCastEffect.class, name = "putAfterCastEffect"),
			@JsonSubTypes.Type(value = RevealEffect.class, name = "revealEffect"),
			@JsonSubTypes.Type(value = SetArmorEffect.class, name = "setArmorEffect"),
			@JsonSubTypes.Type(value = SetDashEffect.class, name = "setDashEffect"),
			@JsonSubTypes.Type(value = SetHealthEffect.class, name = "setHealthEffect"),
			@JsonSubTypes.Type(value = SetManaEffect.class, name = "setManaEffect"),
			@JsonSubTypes.Type(value = SetMoveEffect.class, name = "setMoveEffect"),
			@JsonSubTypes.Type(value = SetRangeEffect.class, name = "setRangeEffect"),
			@JsonSubTypes.Type(value = TopDeckEffect.class, name = "topDeckEffect"),
			@JsonSubTypes.Type(value = VoidEffect.class, name = "voidEffect"),
			@JsonSubTypes.Type(value = WordEffect.class, name = "wordEffect"),
			@JsonSubTypes.Type(value = YouCanEffect.class, name = "youCanEffect"),
		})

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public interface IEffect
{
	@JsonIgnore
	public String getDescription();
	public void prepare(Game game, ISpell spell);
	public void clean();
}
