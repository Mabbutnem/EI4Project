package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import spell.Card;
import zone.AutoHideZone;
import zone.AutoRevealZone;
import zone.Zone;
import zone.ZoneGroup;
import zone.ZonePick;
import zone.ZoneType;

public class TestZoneGroup
{
	@Mock
	private AutoHideZone deck;
	@Mock
	private AutoRevealZone hand;
	@Mock
	private AutoRevealZone discard;
	@Mock
	private AutoRevealZone burn;
	@Mock
	private Zone banish;
	@Mock
	private Zone voidZ;
	
	@InjectMocks
	private ZoneGroup zoneGroup;
	
	
	private Card[] cards;
	
	private Card[] addedCards;
	

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
						mock(Card.class),
				};
		
		addedCards = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
				};
		
		zoneGroup = new ZoneGroup(cards);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	

	@Test
	public final void testZoneGroup() {
		verify(deck, times(1)).add(cards, ZonePick.DEFAULT);
	}
	
	@Test
	public final void testZoneGroupCardsEmpty() {
		cards = new Card[0];
		zoneGroup = new ZoneGroup(cards);
		verify(deck, times(1)).add(cards, ZonePick.DEFAULT);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneGroupException1() {
		zoneGroup = new ZoneGroup(null);
	}

	@Test
	public final void testTransfer()
	{
		zoneGroup.transfer(ZoneType.BURN, ZonePick.BOTTOM, ZoneType.BANISH, ZonePick.DEFAULT, 2);
		Card[] removedCards = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
				};
		when(burn.remove(2)).thenReturn(removedCards);
		verify(burn, times(1)).remove(2, ZonePick.BOTTOM);
		verify(banish, times(1)).add(removedCards, ZonePick.DEFAULT);
		
		
		zoneGroup.transfer(ZoneType.DECK, ZonePick.TOP, ZoneType.HAND, ZonePick.RANDOM, 3);
		removedCards = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
				};
		when(deck.remove(3)).thenReturn(removedCards);
		verify(deck, times(1)).remove(3, ZonePick.TOP);
		verify(hand, times(1)).add(removedCards, ZonePick.RANDOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTransferException1()
	{
		zoneGroup.transfer(ZoneType.DECK, null, ZoneType.HAND, ZonePick.RANDOM, 3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTransferException2()
	{
		zoneGroup.transfer(ZoneType.DECK, null, ZoneType.HAND, ZonePick.RANDOM, 3);
	}

	@Test
	public final void testAddCardArrayZoneTypeZonePick() {
		fail("Not yet implemented");
	}

	@Test
	public final void testAddCardArrayZoneType() {
		fail("Not yet implemented");
	}

	@Test
	public final void testRemoveIntZoneTypeZonePick() {
		fail("Not yet implemented");
	}

	@Test
	public final void testRemoveIntZoneType() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetCards() {
		fail("Not yet implemented");
	}

	@Test
	public final void testShuffle() {
		zoneGroup.shuffle(ZoneType.VOID);
		verify(voidZ, times(1)).shuffle();
		
		zoneGroup.shuffle(ZoneType.DISCARD);
		verify(discard, times(1)).shuffle();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testShuffleException() {
		zoneGroup.shuffle(null);
	}

	@Test
	public final void testMoveCardToIndex() {
		zoneGroup.moveCardToIndex(ZoneType.HAND, 0, 2);
		verify(hand, times(1)).moveCardToIndex(0, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testMoveCardToIndexException() {
		zoneGroup.moveCardToIndex(null, 0, 2);
	}

}
