package test_boardelement;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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

import boardelement.Wizard;
import boardelement.WizardFactory;
import characterlistener.IManaListener;
import constant.WizardConstant;
import listener.ICardArrayRequestListener;
import spell.Card;
import spell.Power;
import zone.Zone;
import zone.ZoneGroup;
import zone.ZoneType;

public class TestWizard
{
	private class MockCardArrayDisplayListener implements ICardArrayRequestListener
	{

		@Override
		public Card[] chooseCards(int nbCard, Card[] cards) { return null; }

		@Override
		public Card[] chooseCards(Card[] cards) { return null; }
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
	private IManaListener manaListener;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		Zone.setCardArrayRequestListener(mock(MockCardArrayDisplayListener.class));
		ZoneGroup.setCardArrayRequestListener(mock(MockCardArrayDisplayListener.class));
		
		wConstant = mock(WizardConstant.class);
		Wizard.setWizardConstant(wConstant);
		when(wConstant.getBaseMana()).thenReturn(20);
		when(wConstant.getBaseMove()).thenReturn(5);
		when(wConstant.getBaseRange()).thenReturn(9);
		when(wConstant.getInitArmor()).thenReturn(30);
		when(wConstant.getMaxHealth()).thenReturn(70);
		when(wConstant.getNbInitCard()).thenReturn(5);
		
		cards = new Card[]
				{
						card1 = mock(Card.class),
						card2 = mock(Card.class),
						card3 = mock(Card.class),
						mock(Card.class),
						mock(Card.class),
				};
		when(card1.getName()).thenReturn("card1");
		when(card1.cloneObject()).thenReturn(card1);
		when(card2.getName()).thenReturn("card2");
		when(card2.cloneObject()).thenReturn(card2);
		when(card3.getName()).thenReturn("card3");
		when(card3.cloneObject()).thenReturn(card3);
		
		cardsW = new HashMap<>();
		cardsW.put("card1", 2);
		cardsW.put("card2", 3);
		cardsW.put("card3", 1);
		
		power = mock(Power.class);
		
		transformedPower = mock(Power.class);
		
		wFactory = mock(WizardFactory.class);
		when(wFactory.getName()).thenReturn("sorcier");
		when(wFactory.getBasePower()).thenReturn(power);
		when(wFactory.getTransformedPower()).thenReturn(transformedPower);
		when(wFactory.getMapCardsQuantity()).thenReturn(cardsW);
		
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
	}
	
	@Test
	public final void testResetHealth() {
		w.setHealth(1);
		
		w.resetHealth();
		
		int expected = 70;
		int result = w.getHealth();
		
		assertEquals(expected, result);
	}
	
	@Test
	public final void testResetArmor() {
		w.setArmor(1);
		
		w.resetArmor();
		
		int expected = 30;
		int result = w.getArmor();
		
		assertEquals(expected, result);
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
	public final void mulligan()
	{
		w.mulligan();
		verify(zoneGroup, times(1)).mulligan(5);
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
		verify(zoneGroup, times(1)).mulligan(5);
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
	public final void testManaListener(){
		manaListener = mock(IManaListener.class);
		w.addManaListener(manaListener);
		
		w.setMana(15);
		verify(manaListener, times(1)).onChange(w, 20, 15);
		verify(manaListener, times(1)).onLoss(w, 20, 15);
		
		reset(manaListener);
		w.gainMana(2);
		verify(manaListener, times(1)).onChange(w, 15, 17);
		verify(manaListener, times(1)).onGain(w, 15, 17);

		reset(manaListener);
		w.loseMana(2);
		verify(manaListener, times(1)).onChange(w, 17, 15);
		verify(manaListener, times(1)).onLoss(w, 17, 15);
		
		reset(manaListener);
		w.removeManaListener(manaListener);
		w.setMana(15);
		w.gainMana(2);
		w.loseMana(2);
		verify(manaListener, never()).onChange(any(), anyInt(), anyInt());
		verify(manaListener, never()).onGain(any(), anyInt(), anyInt());
		verify(manaListener, never()).onLoss(any(), anyInt(), anyInt());
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
		
		//N'est pas tué quand le deck est vidé
		boolean expected = true;
		boolean result = w.isAlive();
		assertEquals(expected, result);
	}
	
	@Test
	public final void setAliveToFalseWhen0CardInDeck()
	{
		w = new Wizard(wFactory, cards);
		
		boolean expected = true;
		boolean result = w.isAlive();
		assertEquals(expected, result);
		
		

		//Il reste une carte dans le deck donc il ne meurt pas
		w.getZoneGroup().remove(5, ZoneType.DECK);
		
		expected = true;
		result = w.isAlive();
		assertEquals(expected, result);
		
		
		
		//Il n'y a plus de carte dans le deck, il meurt..
		w.getZoneGroup().remove(1, ZoneType.DECK);
		
		expected = false;
		result = w.isAlive();
		assertEquals(expected, result);
	}


}
