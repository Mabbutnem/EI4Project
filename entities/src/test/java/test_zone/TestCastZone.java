package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Wizard;
import spell.Card;
import spell.ISpell;
import spell.Power;
import zone.CastZone;
import zone.ZonePick;
import zone.ZoneType;

public class TestCastZone
{
	
	private CastZone castZone;
	
	private Card card;
	private Power power;
	private Wizard owner;

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
		
		card = mock(Card.class);
		power = mock(Power.class);
		owner = mock(Wizard.class);
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
	public final void testAddCardWizardZoneTypeZonePick() {
		castZone.add(card, owner, ZoneType.BURN, ZonePick.DEFAULT);
		assertEquals(card, castZone.getCurrentSpell());
		assertEquals(owner, castZone.getCurrentOwner());
		assertEquals(ZoneType.BURN, castZone.getCurrentZoneTypeDest());
		assertEquals(ZonePick.DEFAULT, castZone.getCurrentZonePickDest());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardZoneTypeZonePickException1() {
		fail("Not yet implemented");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddCardWizardZoneTypeZonePickException2() {
		fail("Not yet implemented");
	}

	@Test
	public final void testAddCardWizard() {
		fail("Not yet implemented");
	}

	@Test
	public final void testAddISpell() {
		fail("Not yet implemented");
	}

	@Test
	public final void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	public final void testCast() {
		fail("Not yet implemented");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testCastException() {
		fail("Not yet implemented");
	}

}
