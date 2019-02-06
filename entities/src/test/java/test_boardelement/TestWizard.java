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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	
	@Mock
	private ZoneGroup zoneGroup;
	
	@InjectMocks
	private Wizard w;
	
	private WizardFactory wFactory;
	
	private WizardConstant wConstant;
	
	private Card[] cards; 
	private Card card1;
	private Card card2;
	private Card card3;
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
						mock(Card.class),
						mock(Card.class),
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
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	


	@Test
	public final void testWizard()
	{
		w = new Wizard(wFactory, cards);
		
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
		int expected = 60;
		int result;
		w.setHealth(60);
		result = w.getHealth();
		assertEquals(expected, result);
		
		expected = 70;
		w.setHealth(90);
		result = w.getHealth();
		assertEquals(expected, result);
		
		w.setHealth(-10);
		boolean expectedB = true;
		boolean resultB = w.isTransformed();
		assertEquals(expectedB, resultB);
		expected = 70;
		result = w.getHealth();
		assertEquals(expected, result);
		
		w.setHealth(-5);
		expectedB = false;
		resultB = w.isAlive();
		assertEquals(expectedB, resultB);
		verify(gameListener, times(1)).clearBoard(w);
	}

	@Test
	public final void testResetMove() {
		int expected = wConstant.getBaseMove();
		int result;
		
		w.setMove(0);
		w.resetMove();
		result = w.getMove();
		assertEquals(expected, result);
	}

	@Test
	public final void testResetRange() {
		int expected = wConstant.getBaseRange();
		int result;
		
		w.setRange(0);
		w.resetRange();
		result = w.getRange();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetWizardConstant() {
		WizardConstant expected = wConstant;
		WizardConstant result = Wizard.getWizardConstant();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetWizardConstant() {
		Wizard.setWizardConstant(wConstant);
		WizardConstant expected = wConstant;
		WizardConstant result = Wizard.getWizardConstant();
		assertEquals(expected, result);
	}

	@Test
	public final void testUntransform() {
		boolean expected = false;
		boolean result;
		
		w.transform();
		w.untransform();
		result = w.isTransformed();
		assertEquals(expected, result);		
	}

	@Test
	public final void testTransform() {
		boolean expected = true;
		boolean result;
		w.transform();
		result = w.isTransformed();
		assertEquals(expected, result);
		
		int expectedI = 70;
		int resultI = w.getHealth();
		assertEquals(expectedI, resultI);
		
		verify(zoneGroup, times(1)).transform();
	}

	@Test
	public final void testIsTransformed() {
		boolean expected = false;
		boolean result = w.isTransformed();
		assertEquals(expected, result);
		
		w.transform();
		expected = true;
		result = w.isTransformed();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetName() {
		String expected = "sorcier";
		String result = w.getName();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetMana() {
		int expected = 20;
		int result = w.getMana();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetMana() {
		int expected = 25;
		int result;
		w.setMana(25);
		result = w.getMana();
		assertEquals(expected, result);
	}

	@Test
	public final void testLoseMana() {
		int expected = 15;
		int result;
		w.loseMana(5);
		result = w.getMana();
		assertEquals(expected, result);
	}

	@Test
	public final void testGainMana() {
		int expected = 30;
		int result;
		w.gainMana(10);
		result = w.getMana();
		assertEquals(expected, result);
	}

	@Test
	public final void testResetMana() {
		int expected = 20;
		int result;
		w.setMana(4);
		w.resetMana();
		result = w.getMana();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetPower() {
		Power expected = power;
		Power result = w.getPower();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetZoneGroup() {
		ZoneGroup expected = zoneGroup;
		ZoneGroup result = w.getZoneGroup();
		assertEquals(expected, result);
	}

	@Test
	public final void testResetCards() {
		w = new Wizard(wFactory, cards);
		
		w.resetCards(wFactory, cards);
		
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


}
