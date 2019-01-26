package test_event;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import event.ZoneGroupAddEvent;
import spell.Card;
import zone.ZonePick;
import zone.ZoneType;

public class TestZoneGroupAddEvent
{
	private ZoneGroupAddEvent e;
	private Card[] cards;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cards = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
				};
		e = new ZoneGroupAddEvent(cards, ZoneType.HAND, ZonePick.DEFAULT);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public final void testGetCards() {
		Card[] expected = cards;
		Card[] result = e.getCards();
		assertArrayEquals(expected, result);
	}

	@Test
	public final void testGetDest() {
		ZoneType expected = ZoneType.HAND;
		ZoneType result = e.getDest();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetDestPick() {
		ZonePick expected = ZonePick.DEFAULT;
		ZonePick result = e.getDestPick();
		assertEquals(expected, result);
	}

}
