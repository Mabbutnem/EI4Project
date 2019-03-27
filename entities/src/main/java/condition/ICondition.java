package condition;

import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import game.Game;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = As.PROPERTY,
		property = "conditionType")
		@JsonSubTypes({
			@JsonSubTypes.Type(value = FalseCondition.class, name = "falseCondition"),
			@JsonSubTypes.Type(value = HigherArmorCondition.class, name = "higherArmorCondition"),
			@JsonSubTypes.Type(value = HigherCardCondition.class, name = "higherCardCondition"),
			@JsonSubTypes.Type(value = HigherDashCondition.class, name = "higherDashCondition"),
			@JsonSubTypes.Type(value = HigherManaCondition.class, name = "higherManaCondition"),
			@JsonSubTypes.Type(value = HigherMoveCondition.class, name = "higherMoveCondition"),
			@JsonSubTypes.Type(value = HigherRangeCondition.class, name = "higherRangeCondition"),
			@JsonSubTypes.Type(value = LowerCardCondition.class, name = "lowerCardCondition"),
			@JsonSubTypes.Type(value = LowerHealthCondition.class, name = "lowerHealthCondition"),
			@JsonSubTypes.Type(value = TrueCondition.class, name = "trueCondition"),
			
		})
public interface ICondition
{
	public String getDescription();
	public Predicate<Game> getPredicate();
}
