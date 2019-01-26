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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import event.ZoneGroupAddEvent;
import listener.IZoneGroupAddListener;
import spell.Card;
import zone.AutoHideZone;
import zone.AutoRevealZone;
import zone.Zone;
import zone.ZoneGroup;
import zone.ZonePick;
import zone.ZoneType;

class MockZoneGroupAddListener implements IZoneGroupAddListener
{
	public MockZoneGroupAddListener() {}

	@Override
	public void displayAddedCards(ZoneGroupAddEvent e) {}
	
}



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
	private Card card;
	
	private Card[] addedCards;
	
	private IZoneGroupAddListener zoneGroupAddListener;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Zone.setCardArrayRequestListener(new TOPCardArrayRequestListener());
		
		ZoneGroup.setZoneGroupAddListener(zoneGroupAddListener = mock(MockZoneGroupAddListener.class));
		
		cards = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						card = mock(Card.class),
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
	
	
	

	@Test //Ici on teste sans les mocks : 
	//en effet, on veut vérifier si on appelle les bons constructeurs
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
	public final void testReset()
	{
		zoneGroup.reset(addedCards);
		verify(deck, times(1)).removeAll();
		verify(deck, times(1)).add(addedCards);
		verify(hand, times(1)).removeAll();
		verify(discard, times(1)).removeAll();
		verify(burn, times(1)).removeAll();
		verify(banish, times(1)).removeAll();
		verify(voidZ, times(1)).removeAll();
	}
	
	@Test
	public final void testZoneGroupCardsEmpty() {
		cards = new Card[0];
		zoneGroup = new ZoneGroup(cards);
		
		Card[] expected = cards;
		Card[] result = zoneGroup.getCards(ZoneType.DECK);
		assertArrayEquals(expected, result);
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
		zoneGroup.transfer(null, ZonePick.TOP, ZoneType.HAND, ZonePick.RANDOM, 3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTransferException2()
	{
		//dest ne peut pas être null
		zoneGroup.transfer(ZoneType.DECK, ZonePick.TOP, null, ZonePick.RANDOM, 3);
	}

	@Test
	public final void testAdd() {
		zoneGroup.add(addedCards, ZoneType.BURN);
		verify(burn, times(1)).add(addedCards, ZonePick.DEFAULT);
		verify(zoneGroupAddListener, times(1)).displayAddedCards(Mockito.any()); //1er appel
		
		zoneGroup.add(addedCards, ZoneType.BURN, ZonePick.TOP);
		verify(burn, times(1)).add(addedCards, ZonePick.TOP);
		verify(zoneGroupAddListener, times(2)).displayAddedCards(Mockito.any()); //2eme appel
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
	public final void testRemoveCard()
	{
		zoneGroup.remove(card, ZoneType.HAND);
		verify(hand, times(1)).remove(card);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveCardException()
	{
		//ZoneType ne peut pas être null
		zoneGroup.remove(card, null);
	}
	
	@Test
	public final void testSize()
	{
		int expected = 97;
		when(deck.size()).thenReturn(expected);
		int result = zoneGroup.size(ZoneType.DECK);
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSizeException()
	{
		zoneGroup.size(null);
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
	
	@Test
	public final void testMulligan()
	{
		fail("TODO");
	}
	
	@Test
	public final void testTransform()
	{
		fail("TODO");
		
		Card[] deckCards = new Card[0];
		when(deck.size()).thenReturn(87);
		when(deck.remove(43)).thenReturn(deckCards);
		
		Card[] handCards = new Card[0];
		when(hand.removeAll()).thenReturn(handCards);
		
		Card[] discardCards = new Card[0];
		when(discard.removeAll()).thenReturn(discardCards);
		
		Card[] banishCards = new Card[0];
		when(banish.removeAll()).thenReturn(banishCards);

		Card[] voidCards = new Card[0];
		when(voidZ.removeAll()).thenReturn(voidCards);
		
		
		
		zoneGroup.transform();
		
		
		
		verify(deck, times(1)).remove(43);
		verify(burn, times(1)).add(deckCards);
		
		verify(hand, times(1)).removeAll();
		verify(deck, atLeastOnce()).add(discardCards);

		verify(discard, times(1)).removeAll();
		verify(deck, atLeastOnce()).add(discardCards);

		verify(banish, times(1)).removeAll();
		verify(deck, atLeastOnce()).add(banishCards);

		verify(voidZ, times(1)).removeAll();
		verify(deck, atLeastOnce()).add(voidCards);
		
		verify(deck, times(1)).shuffle();
	}

}
