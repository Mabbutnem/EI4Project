package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import event.CardArrayRequestEvent;
import exception.NotInitialisedContextException;
import listener.ICardArrayRequestListener;
import spell.Card;
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;

final class MockedCardArrayRequestListener implements ICardArrayRequestListener
{
	public MockedCardArrayRequestListener() {}
	
	public Card[] getCardArray(CardArrayRequestEvent e)
	{
		return new Card[0];
	}
	
}

public class TestZone
{
	
	private Zone zone;
	
	private Card[] cards;
	private Card card1;
	private Card card2;
	private Card card3;
	private Card card4;
	private Card card5;
	
	private Card[] addedCards;
	private Card card6;
	private Card card7;
	private Card card8;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Zone.setCardArrayRequestListener(new MockedCardArrayRequestListener());
		
		addedCards = new Card[]
				{
						card6 = mock(Card.class),
						card7 =	mock(Card.class),
						card8 =	mock(Card.class)
				};
		
		cards = new Card[]
				{
						card1 = mock(Card.class), //BOTTOM
						card2 = mock(Card.class),
						card3 = mock(Card.class),
						card4 = mock(Card.class),
						card5 = mock(Card.class) //TOP
				};
		
		zone = new Zone(cards, ZoneType.BURN, ZonePick.TOP);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	

	@Test
	public final void testZoneExceptionCardsEmpty() {
		int expected = 0;
		
		zone = new Zone(null, ZoneType.BURN, ZonePick.TOP);
		int result = zone.getCards().length;
		assertEquals(expected, result);

		zone = new Zone(new Card[0], ZoneType.BURN, ZonePick.TOP);
		result = zone.getCards().length;
		assertEquals(expected, result);
	}
	
	@Test (expected = NotInitialisedContextException.class)
	public final void testZoneException1() {
		//cardArrayRequestListener n'a pas été initialisé
		Zone.setCardArrayRequestListener(null);
		zone = new Zone(cards, ZoneType.BURN, ZonePick.TOP);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException2() {
		//zoneType ne peut pas être null
		zone = new Zone(cards, null, ZonePick.TOP);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException3() {
		//zonePick ne peut pas être null
		zone = new Zone(cards, ZoneType.BURN, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException4() {
		//zonePick par default ne peut pas être choix
		zone = new Zone(cards, ZoneType.BURN, ZonePick.CHOICE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException5() {
		//zonePick par default ne peut pas être default, il faut en choisir un vrai par default !!
		zone = new Zone(cards, ZoneType.BURN, ZonePick.DEFAULT);
	}

	@Test
	public final void testAddCardArray() {
		fail("Not yet implemented");
	}

	@Test
	public final void testAddTOP() {
		zone.add(addedCards, ZonePick.TOP);
		
		Card[] expected = new Card[]
				{
					card1,
					card2,
					card3,
					card4,
					card5,
					card6,
					card7,
					card8,	
				};
		Card[] result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public final void testAddBOTTOM() {
		zone.add(addedCards, ZonePick.BOTTOM);
		
		Card[] expected = new Card[]
				{
					card8,
					card7,
					card6,
					card1,
					card2,
					card3,
					card4,
					card5,	
				};
		Card[] result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException1()
	{
		//le tableau de card ne peut pas etre null
		zone.add(null, ZonePick.RANDOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException2()
	{
		//zonePick ne peut pas etre null
		zone.add(new Card[0], null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException3()
	{
		//zonePick ne peut pas être choix lors d'un ajout
		zone.add(new Card[0], ZonePick.CHOICE);
	}

	@Test
	public final void testRemoveInt() {
		fail("Not yet implemented");
	}

	@Test
	public final void testRemoveIntZonePick() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetZoneType() {
		fail("Not yet implemented");
	}

	@Test
	public final void testShuffle() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetCards() {
		fail("Not yet implemented");
	}

	@Test
	public final void testHideCards() {
		fail("Not yet implemented");
	}

	@Test
	public final void testRevealCards() {
		fail("Not yet implemented");
	}

	@Test
	public final void testMoveCardToIndex() {
		fail("Not yet implemented");
	}

}
