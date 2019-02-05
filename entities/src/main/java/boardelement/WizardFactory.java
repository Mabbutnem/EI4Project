package boardelement;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import spell.Power;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WizardFactory
{
	private String name;
	private Power basePower;
	private Power transformedPower;
	private Map<String, Integer> cards; //String:name of the card, Integer:number of cards
	
	
	
	public WizardFactory() {
		//Empty constructor for jackson
	}

	
	
	public String getName() {
		return name;
	}

	public Power getBasePower() {
		return basePower;
	}

	public Power getTransformedPower() {
		return transformedPower;
	}

	public Map<String, Integer> getCards() {
		return cards;
	}
	
}
