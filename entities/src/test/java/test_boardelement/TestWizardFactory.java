package test_boardelement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import boardelement.WizardFactory;
import spell.Power;

public class TestWizardFactory {

	private WizardFactory w;
	private Power baseP;
	private Power transformedP;
	private Map<String, Integer> cards;
	
	@Before
	public void setUp() throws Exception
	{
		baseP = mock(Power.class);
		transformedP = mock(Power.class);
		cards = new HashMap<>();
		cards.put("card1", 2);
		cards.put("card2", 3);
		cards.put("card3", 1);
		
	}
	
	@Test
	public void testWizardFactory() {
		w = new WizardFactory("wizard", baseP, transformedP, cards);
		
		String expected = "wizard";
		String result = w.getName();
		assertEquals(expected, result);
		
		Power expectedP = baseP;
		Power resultP = w.getBasePower();
		assertEquals(expectedP, resultP);
		
		expectedP = transformedP;
		resultP = w.getTransformedPower();
		assertEquals(expectedP, resultP);
		
		Map<String, Integer> expectedM = cards;
		Map<String, Integer> resultM = w.getMapCardsQuantity();
		assertEquals(expectedM, resultM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testWizardFactoryName() {
		w = new WizardFactory("", baseP, transformedP, cards);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testWizardFactoryCards() {
		cards.put("card3", -2);
		w = new WizardFactory("wizard", baseP, transformedP, cards);
	}

}
