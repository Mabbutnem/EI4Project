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
import listener.ICardArrayDisplayListener;
import listener.IGameListener;
import spell.Card;
import spell.Power;
import zone.Zone;
import zone.ZoneGroup;
import zone.ZonePick;
import zone.ZoneType;

public class TestWizard
{
	private class MockCardArrayDisplayListener implements ICardArrayDisplayListener
	{

		@Override
		public Card[] chooseCards(int nbCard, Card[] cards) { return null; }

		@Override
		public Card[] chooseCards(Card[] cards) { return null; }

		@Override
		public void displayAddCards(Card[] cards, ZoneType dest, ZonePick destPick) {}

		@Override
		public void displayTransferCards(Card[] cards, ZoneType source, ZonePick sourcePick, ZoneType dest,
				ZonePick destPick) {}
	}
	
	private class MockGameListener implements IGameListener{

		@Override
		public void clearBoard(Character character) {}
	}
	
	
	
	private Wizard w;
	
	private WizardFactory wFactory;
	
	private WizardConstant wConstant;
	
	private Card[] cards; 
	private Card card1;
	private Card card2;
	private Card card3;
	private Card card4;
	private Card card5;
	private Map<String, Integer> cardsW;
	private Power power;
	private Power transformedPower;
	
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
		Zone.setCardArrayDisplayListener(mock(MockCardArrayDisplayListener.class));
		ZoneGroup.setCardArrayDisplayListener(mock(MockCardArrayDisplayListener.class));
		
		wConstant = mock(WizardConstant.class);
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
		int expected = 30;
		int result = w.getArmor();
		assertEquals(expected, result);
		
		expected = 0;
		result = w.getDash();
		assertEquals(expected, result);
		
		expected = 70;
		result = w.getHealth();
		assertEquals(expected, result);
		
		expected = 20;
		result = w.getMana();
		assertEquals(expected, result);
		
		expected = 5;
		result = w.getMove();
		assertEquals(expected, result);
		
		expected = 9;
		result = w.getRange();
		assertEquals(expected, result);
		
		String expectedS = "sorcier";
		String resultS = w.getName();
		assertEquals(expectedS, resultS);
		
		Power expectedP = power;
		Power resultP = w.getPower();
		assertEquals(expectedP, resultP);

		int nbCard1Expected = 2;
		int nbCard2Expected = 3;
		int nbCard3Expected = 1;
		int nbCard1Result = 0;
		int nbCard2Result = 0;
		int nbCard3Result = 0;
		for(Card c : w.getZoneGroup().getCards(ZoneType.DECK))
		{
			if(c.getName().equals("card1")) { nbCard1Result++;}
			else if(c.getName().equals("card2")) { nbCard2Result++;}
			else if(c.getName().equals("card3")) { nbCard3Result++;}
		}
		assertEquals(nbCard1Expected, nbCard1Result);
		assertEquals(nbCard2Expected, nbCard2Result);
		assertEquals(nbCard3Expected, nbCard3Result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testWizardException1()
	{
		w = new Wizard(null, cards);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testWizardException2()
	{
		w = new Wizard(wFactory, null);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testWizardException3()
	{
		Wizard.setWizardConstant(null);
		w = new Wizard(wFactory, cards);
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
