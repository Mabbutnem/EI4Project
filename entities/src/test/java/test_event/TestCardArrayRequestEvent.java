package test_event;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
	private Card[] cards;
	private CardArrayRequestEvent e;
	
	

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
						mock(Card.class),
				};
		
		e = new CardArrayRequestEvent(5, ZonePick.CHOICE, cards);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	

	@Test
	public final void testGetNbCard() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetZonePick() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetCards() {
		fail("Not yet implemented");
	}

}
