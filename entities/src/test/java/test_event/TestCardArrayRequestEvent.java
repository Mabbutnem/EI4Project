package test_event;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import event.CardArrayRequestEvent;
import spell.Card;
import zone.ZonePick;

public class TestCardArrayRequestEvent
{
	private CardArrayRequestEvent e;
	private List<Card> cards;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cards = new LinkedList<Card>();
		for(int i = 0; i < 5; i++)
		{
			cards.add(mock(Card.class));
		}
		e = new CardArrayRequestEvent(3, ZonePick.RANDOM, cards);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	
	@Test
	public final void testGetNbCard() {
		int expected = 3;
		int result = e.getNbCard();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetZonePick() {
		ZonePick expected = ZonePick.RANDOM;
		ZonePick result = e.getZonePick();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetCards() {
		List<Card> expected = cards;
		List<Card> result = e.getCards();
		for(int i = 0; i < expected.size(); i++)
		{
			assertEquals(expected.get(i), result.get(i));
		}
	}

}
