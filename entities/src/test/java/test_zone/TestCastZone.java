package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import boardelement.Wizard;
import game.Game;
import spell.Card;
import spell.ISpell;
import spell.Power;
import zone.CastZone;
import zone.ZoneGroup;
import zone.ZonePick;
import zone.ZoneType;

public class TestCastZone
{
	
	private CastZone castZone;
	
	@Mock
	private Card card;
	@Mock
	private Power power;
	@Mock
	private Wizard owner;
	@Mock
	private Game game;
	
	@Captor
	private ArgumentCaptor<Card[]> cardArrayCaptor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		castZone = new CastZone();
		
	     MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	@Test
	public final void testGetCurrentSpell() {
		ISpell expected = null;
		ISpell result = castZone.getCurrentSpell();
		assertEquals(expected, result);

		
		
		castZone.add(power);
		
		expected = power;
		result = castZone.getCurrentSpell();
		assertEquals(expected, result);
	}
	
	@Test
	public final void testGetCurrentZoneTypeDest() {
		ZoneType expected = null;
		ZoneType result = castZone.getCurrentZoneTypeDest();
		assertEquals(expected, result);
		
		
		
		castZone.add(power);
		
		expected = null;
		result = castZone.getCurrentZoneTypeDest();
		assertEquals(expected, result);
		
		
		
		castZone = new CastZone();
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		
		expected = ZoneType.BURN;
		result = castZone.getCurrentZoneTypeDest();
		assertEquals(expected, result);
	}
	
	@Test
	public final void testSetCurrentZoneTypeDest() {
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		castZone.setCurrentZoneTypeDest(ZoneType.BANISH);
		
		ZoneType expected = ZoneType.BANISH;
		ZoneType result = castZone.getCurrentZoneTypeDest();
		assertEquals(expected, result);
		
		
		
		castZone = new CastZone();
		castZone.add(power);
		castZone.setCurrentZoneTypeDest(ZoneType.BANISH);
		
		expected = ZoneType.BANISH;
		result = castZone.getCurrentZoneTypeDest();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetCurrentZoneTypeDestException() {
		//Si currentSpell est une carte, on ne peut pas set ZoneType à null
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		castZone.setCurrentZoneTypeDest(null);
	}
	
	@Test
	public final void testGetCurrentZonePickDest() {
		ZonePick expected = null;
		ZonePick result = castZone.getCurrentZonePickDest();
		assertEquals(expected, result);
		
		
		
		castZone.add(power);
		
		expected = null;
		result = castZone.getCurrentZonePickDest();
		assertEquals(expected, result);
		
		
		
		castZone = new CastZone();
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		
		expected = ZonePick.DEFAULT;
		result = castZone.getCurrentZonePickDest();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetCurrentZonePickDest() {
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		castZone.setCurrentZonePickDest(ZonePick.BOTTOM);
		
		ZonePick expected = ZonePick.BOTTOM;
		ZonePick result = castZone.getCurrentZonePickDest();
		assertEquals(expected, result);
		
		
		
		castZone = new CastZone();
		castZone.add(power);
		castZone.setCurrentZonePickDest(ZonePick.BOTTOM);
		
		expected = ZonePick.BOTTOM;
		result = castZone.getCurrentZonePickDest();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetCurrentZonePickDestException() {
		//Si currentSpell est une carte, on ne peut pas set ZoneType à null
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		castZone.setCurrentZonePickDest(null);
	}
	
	@Test
	public final void testGetCurrentOwner()
	{
		Wizard expected = null;
		Wizard result = castZone.getCurrentOwner();
		assertEquals(expected, result);

		
		
		castZone.add(power);
		
		expected = null;
		result = castZone.getCurrentOwner();
		assertEquals(expected, result);
		
		
		
		castZone = new CastZone();
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		expected = owner;
		result = castZone.getCurrentOwner();
		assertEquals(expected, result);
	}

	@Test
	public final void testAdd() {
		//Initialisation
		when(owner.getZoneGroup()).thenReturn(mock(ZoneGroup.class));

		//Vérifications
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		assertEquals(card, castZone.getCurrentSpell());
		assertEquals(owner, castZone.getCurrentOwner());
		assertEquals(ZoneType.BURN, castZone.getCurrentZoneTypeDest());
		assertEquals(ZonePick.DEFAULT, castZone.getCurrentZonePickDest());
		
		
		
		castZone.add(power);
		assertEquals(card, castZone.getCurrentSpell());
		assertEquals(owner, castZone.getCurrentOwner());
		assertEquals(ZoneType.BURN, castZone.getCurrentZoneTypeDest());
		assertEquals(ZonePick.DEFAULT, castZone.getCurrentZonePickDest());
		
		
		
		castZone.cast(game);
		assertEquals(power, castZone.getCurrentSpell());
		assertEquals(null, castZone.getCurrentOwner());
		assertEquals(null, castZone.getCurrentZoneTypeDest());
		assertEquals(null, castZone.getCurrentZonePickDest());
		
		
		
		castZone.cast(game);
		
		castZone.add(card, owner);
		assertEquals(card, castZone.getCurrentSpell());
		assertEquals(owner, castZone.getCurrentOwner());
		assertEquals(ZoneType.DISCARD, castZone.getCurrentZoneTypeDest());
		assertEquals(ZonePick.DEFAULT, castZone.getCurrentZonePickDest());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardZoneTypeZonePickException1() {
		castZone.add(null, owner, ZoneType.BURN, ZonePick.DEFAULT);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardZoneTypeZonePickException2() {
		castZone.add(card, null, ZoneType.BURN, ZonePick.DEFAULT);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardZoneTypeZonePickException3() {
		castZone.add(card, owner, null, ZonePick.DEFAULT);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardZoneTypeZonePickException4() {
		castZone.add(card, owner, ZoneType.BURN, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardException1() {
		castZone.add(null, owner);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardException2() {
		castZone.add(card, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddISpellException() {
		castZone.add(null);
	}

	@Test
	public final void testIsEmpty() {
		boolean expected = true;
		boolean result = castZone.isEmpty();
		assertEquals(expected, result);
		
		castZone.add(power);
		expected = false;
		result = castZone.isEmpty();
		assertEquals(expected, result);
	}

	@Test
	public final void testCast()
	{
		//Initialisation
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		ZoneGroup ownerZoneGroup = mock(ZoneGroup.class);
		when(owner.getZoneGroup()).thenReturn(ownerZoneGroup);
		
		
		castZone.cast(game);
		
		
		//Vérifications
		verify(card, times(1)).cast(game);
		verify(owner, times(1)).getZoneGroup();
		verify(ownerZoneGroup, times(1)).add(cardArrayCaptor.capture(), eq(ZoneType.BURN), eq(ZonePick.DEFAULT));
		assertArrayEquals(new Card[] {card}, cardArrayCaptor.getAllValues().get(0));
		assertEquals(true, castZone.isEmpty());
	}
	
	@Test
	public final void testCastIfNotCard()
	{
		//Initialisation
		castZone.add(power);
		
		
		castZone.cast(game);
		
		
		//Vérifications
		verify(power, times(1)).cast(game);
		assertEquals(true, castZone.isEmpty());
	}
	
	@Test
	public final void testCastIfIsEmpty()
	{
		castZone.cast(game);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testCastException() {
		castZone.cast(null);
	}

}
