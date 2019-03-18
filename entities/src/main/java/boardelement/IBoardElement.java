package boardelement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = As.PROPERTY,
		property = "type")
		@JsonSubTypes({
			@JsonSubTypes.Type(value = Corpse.class, name = "corpse"),
			@JsonSubTypes.Type(value = Monster.class, name = "monster"),
			@JsonSubTypes.Type(value = Wizard.class, name = "wizard")
		})
public interface IBoardElement
{

}
