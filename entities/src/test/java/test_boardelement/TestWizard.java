package test_boardelement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Character;
import boardelement.Wizard;
import boardelement.WizardFactory;
import constant.WizardConstant;
import listener.IGameListener;
import spell.Card;
import spell.Power;
import zone.ZoneType;

public class TestWizard
{
	private Wizard w;
	
	private WizardFactory wFactory;
	
	
	private Card[] cards; 
	private Card card1;
	private Card card2;
	private Card card3;
	private Card card4;
	private Card card5;
	private Map<String, Integer> cardsW;
	private Power power;
	private Power transformedPower;
	
	private class MockGameListener implements IGameListener{

		@Override
		public void clearBoard(Character character) {}
	}
	
	private MockGameListener gameListener;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		WizardConstant wConstant = mock(WizardConstant.class);
		Wizard.setWizardConstant(wConstant);
		when(wConstant.getBaseMana()).thenReturn(20);
		when(wConstant.getBaseMove()).thenReturn(5);
		when(wConstant.getBaseRange()).thenReturn(9);
		when(wConstant.getInitArmor()).thenReturn(30);
		when(wConstant.getMaxHealth()).thenReturn(70);
		
		cards = new Card[]
				{
						card1 = mock(Card.class),
						card2 = mock(Card.class),
						card3 = mock(Card.class),
						card4 = mock(Card.class),
						card5 = mock(Card.class),
				};
		when(card1.getName()).thenReturn("card1");
		when(card2.getName()).thenReturn("card2");
		when(card3.getName()).thenReturn("card3");
		
		cardsW = new HashMap<>();
		cardsW.put("card1", 2);
		cardsW.put("card2", 3);
		cardsW.put("card3", 1);
		
		power = mock(Power.class);
		
		transformedPower = mock(Power.class);
		
		Character.setGameListener(gameListener = mock(MockGameListener.class));
		
		wFactory = mock(WizardFactory.class);
		when(wFactory.getName()).thenReturn("sorcier");
		when(wFactory.getBasePower()).thenReturn(power);
		when(wFactory.getTransformedPower()).thenReturn(transformedPower);
		when(wFactory.getCards()).thenReturn(cardsW);
		
		w = new Wizard(wFactory, cards);
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	


	@Test
	public final void testWizard()
	{
		w.getZoneGroup().getCards(ZoneType.DECK);
	}
	
	@Test
	public final void testSetHealth() {
		fail("Not yet implemented");
	}

	@Test
	public final void testResetMove() {
		fail("Not yet implemented");
	}

	@Test
	public final void testResetRange() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetWizardConstant() {
		fail("Not yet implemented");
	}

	@Test
	public final void testSetWizardConstant() {
		fail("Not yet implemented");
	}

	@Test
	public final void testUntransform() {
		fail("Not yet implemented");
	}

	@Test
	public final void testTransform() {
		fail("Not yet implemented");
	}

	@Test
	public final void testIsTransformed() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetMana() {
		fail("Not yet implemented");
	}

	@Test
	public final void testSetMana() {
		fail("Not yet implemented");
	}

	@Test
	public final void testLoseMana() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGainMana() {
		fail("Not yet implemented");
	}

	@Test
	public final void testResetMana() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetPower() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetZoneGroup() {
		fail("Not yet implemented");
	}

	@Test
	public final void testResetCards() {
		fail("Not yet implemented");
	}

	@Test
	public final void testSetAlive() {
		fail("Not yet implemented");
	}

	@Test
	public final void testObject() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	public final void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public final void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public final void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public final void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public final void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	public final void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public final void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	public final void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	public final void testWait() {
		fail("Not yet implemented");
	}

	@Test
	public final void testFinalize() {
		fail("Not yet implemented");
	}

}
