package boardelement;

import java.util.Map;

import com.google.common.base.Preconditions;

import spell.Power;

public class WizardFactory
{
	private String name;
	private Power basePower;
	private Power transformedPower;
	private Map<String, Integer> mapCardsQuantity; //String:name of the card, Integer:number of cards
	
	
	
	public WizardFactory() {
		//Empty constructor for jackson
	}
	
	public WizardFactory(String name, Power basePower, Power transformedPower, Map<String, Integer> mapCardsQuantity) {
		Preconditions.checkArgument(name.length() > 0, "name length was %s but expected strictly positive", name.length());
		for(Map.Entry<String, Integer> entry : mapCardsQuantity.entrySet())
		{
			Preconditions.checkArgument(entry.getValue() > 0, "number of cards was %s but expected strictly positive", entry.getValue());
		}
		this.name = name;
		this.basePower = basePower;
		this.transformedPower = transformedPower;
		this.mapCardsQuantity = mapCardsQuantity;
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

	public Map<String, Integer> getMapCardsQuantity() {
		return mapCardsQuantity;
	}

	
	
	@Override
	public String toString() {
		return "WizardFactory [name=" + name + ", basePower=" + basePower + ", transformedPower=" + transformedPower
				+ ", mapCardsQuantity=" + mapCardsQuantity + "]";
	}
	
}
