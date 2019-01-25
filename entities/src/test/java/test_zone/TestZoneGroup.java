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
import org.mockito.MockitoAnnotations;

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
		Zone.setCardArrayRequestListener(new TOPCardArrayRequestListener());
		
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
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	

	@Test
	public final void testZoneGroup() {
		zoneGroup = new ZoneGroup(cards);
		
		Card[] expected = cards;
		Card[] result = zoneGroup.getCards(ZoneType.DECK);
		assertArrayEquals(expected, result);
		
		expected = new Card[0];
		result = zoneGroup.getCards(ZoneType.HAND);
		assertArrayEquals(expected, result);
		
		expected = new Card[0];
		result = zoneGroup.getCards(ZoneType.DISCARD);
		assertArrayEquals(expected, result);
		
		expected = new Card[0];
		result = zoneGroup.getCards(ZoneType.BURN);
		assertArrayEquals(expected, result);
		
		expected = new Card[0];
		result = zoneGroup.getCards(ZoneType.BANISH);
		assertArrayEquals(expected, result);
		
		expected = new Card[0];
		result = zoneGroup.getCards(ZoneType.VOID);
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testZoneGroupCardsEmpty() {
		cards = new Card[0];
		zoneGroup = new ZoneGroup(cards);
		verify(deck, times(1)).add(cards, ZonePick.DEFAULT);
	}

	@Test
	public final void testTransfer()
	{
		Card[] expected = new Card[]
			{
					mock(Card.class),
					mock(Card.class),
			};
		when(burn.remove(2, ZonePick.BOTTOM)).thenReturn(expected);
		zoneGroup.transfer(ZoneType.BURN, ZonePick.BOTTOM, ZoneType.BANISH, ZonePick.DEFAULT, 2);
		verify(burn, times(1)).remove(2, ZonePick.BOTTOM);
		verify(banish, times(1)).add(expected, ZonePick.DEFAULT);
		
		
		expected = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
				};
		when(deck.remove(3, ZonePick.TOP)).thenReturn(expected);
		zoneGroup.transfer(ZoneType.DECK, ZonePick.TOP, ZoneType.HAND, ZonePick.RANDOM, 3);
		verify(deck, times(1)).remove(3, ZonePick.TOP);
		verify(hand, times(1)).add(expected, ZonePick.RANDOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTransferException1()
	{
		//source ne peut pas être null
		zoneGroup.transfer(ZoneType.DECK, null, ZoneType.HAND, ZonePick.RANDOM, 3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTransferException2()
	{
		//dest ne peut pas être null
		zoneGroup.transfer(ZoneType.DECK, ZonePick.TOP, ZoneType.HAND, null, 3);
	}

	@Test
	public final void testAdd() {
		zoneGroup.add(addedCards, ZoneType.BURN);
		verify(burn, times(1)).add(addedCards, ZonePick.DEFAULT);
		
		zoneGroup.add(addedCards, ZoneType.BURN, ZonePick.TOP);
		verify(burn, times(1)).add(addedCards, ZonePick.TOP);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException1()
	{
		//ZoneType ne peut pas être null
		zoneGroup.add(addedCards, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException2()
	{
		//ZoneType ne peut pas être null
		zoneGroup.add(addedCards, null, ZonePick.BOTTOM);
	}

	@Test
	public final void testRemove() {
		Card[] expected = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
				};
		when(banish.remove(2, ZonePick.DEFAULT)).thenReturn(expected);
		Card[] result = zoneGroup.remove(2, ZoneType.BANISH);
		verify(banish, times(1)).remove(2, ZonePick.DEFAULT);
		assertArrayEquals(expected, result);

		
		expected = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
				};
		when(banish.remove(3, ZonePick.RANDOM)).thenReturn(expected);
		result = zoneGroup.remove(3, ZoneType.BANISH, ZonePick.RANDOM);
		verify(banish, times(1)).remove(3, ZonePick.RANDOM);
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException1()
	{
		//ZoneType ne peut pas être null
		zoneGroup.remove(3, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException2()
	{
		//ZoneType ne peut pas être null
		zoneGroup.remove(3, null, ZonePick.BOTTOM);
	}

	@Test
	public final void testGetCards() {
		Card[] expected = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
				};
		when(voidZ.getCards()).thenReturn(expected);
		Card[] result = zoneGroup.getCards(ZoneType.VOID);
		verify(voidZ, times(1)).getCards();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGetCardsException() {
		//ZoneType ne peut pas être null
		zoneGroup.getCards(null);
	}

	@Test
	public final void testShuffle() {
		zoneGroup.shuffle(ZoneType.VOID);
		verify(voidZ, times(1)).shuffle();
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
