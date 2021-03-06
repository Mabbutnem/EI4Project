package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.ListChangeListener;
import listener.ICardArrayRequestListener;
import spell.Card;
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;

public class TestZone
{
	private class MockCardArrayDisplayListener implements ICardArrayRequestListener
	{

		@Override
		public Card[] chooseCards(int nbCard, Card[] cards) { return null; }

		@Override
		public Card[] chooseCards(Card[] cards) { return null; }
	}
	
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
	
	private MockCardArrayDisplayListener cardArrayDisplayListener;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Zone.setCardArrayRequestListener(cardArrayDisplayListener = mock(MockCardArrayDisplayListener.class));
		
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
	public final void testZoneCardsEmpty() {
		int expected = 0;

		zone = new Zone(new Card[0], ZoneType.BURN, ZonePick.TOP);
		int result = zone.getCards().length;
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testZoneException1() {
		//cardArrayRequestListener n'a pas �t� initialis�
		Zone.setCardArrayRequestListener(null);
		zone = new Zone(cards, ZoneType.BURN, ZonePick.TOP);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException2() {
		//zoneType ne peut pas �tre null
		zone = new Zone(cards, null, ZonePick.TOP);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException3() {
		//zonePick ne peut pas �tre null
		zone = new Zone(cards, ZoneType.BURN, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException4() {
		//zonePick par default ne peut pas �tre choix
		zone = new Zone(cards, ZoneType.BURN, ZonePick.CHOICE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException5() {
		//zonePick par default ne peut pas �tre default, il faut en choisir un vrai par default !!
		zone = new Zone(cards, ZoneType.BURN, ZonePick.DEFAULT);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException6() {
		//cards ne peut pas �tre null
		zone = new Zone(null, ZoneType.BURN, ZonePick.DEFAULT);
	}

	@Test
	public final void testAdd() {
		zone.add(addedCards);

		//Ici, default est TOP
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
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testAddWithEmptyCardArray() {
		zone.add(new Card[0]);

		//Ici, default est TOP
		Card[] expected = cards;
		Card[] result = zone.getCards();
		assertArrayEquals(expected, result);
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
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testAddDEFAULT() {
		zone.add(addedCards, ZonePick.DEFAULT);
		
		//Ici, default est TOP
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
		assertArrayEquals(expected, result);
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
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testAddRANDOM() {
		zone.add(addedCards, ZonePick.RANDOM);
		
		int expected = 8;
		int result = zone.getCards().length;
		
		assertEquals(expected, result);
		for(Card c : cards)
		{
			assertTrue(Arrays.asList(zone.getCards()).contains(c));
		}
		for(Card c : addedCards)
		{
			assertTrue(Arrays.asList(zone.getCards()).contains(c));
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
		//zonePick ne peut pas �tre choix lors d'un ajout
		zone.add(new Card[0], ZonePick.CHOICE);
	}

	@Test
	public final void testRemove() {

		//Ici, default est TOP
		Card[] expected = new Card[]
				{
					card5,
					card4,
				};
		Card[] result = zone.remove(2);
		assertArrayEquals(expected, result);
		
		expected = new Card[]
				{
					card1,
					card2,
					card3,
				};
		result = zone.getCards();
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testRemoveWith0Cards()
	{
		zone.remove(0);
		
		Card[] expected = cards;
		Card[] result = zone.getCards();
		assertArrayEquals(expected, result);
		
		
		
		zone.remove(0, ZonePick.RANDOM);
		
		expected = cards;
		result = zone.getCards();
		assertArrayEquals(expected, result);
	}

	@Test
	public final void testRemoveTOP() {

		Card[] expected = new Card[]
				{
					card5,
					card4,
				};
		Card[] result = zone.remove(2, ZonePick.TOP);
		assertArrayEquals(expected, result);
		
		expected = new Card[]
				{
					card1,
					card2,
					card3,
				};
		result = zone.getCards();
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testRemoveDEFAULT() {
		//Ici, default est TOP
		Card[] expected = new Card[]
				{
					card5,
					card4,
				};
		Card[] result = zone.remove(2, ZonePick.DEFAULT);
		assertArrayEquals(expected, result);
				
		expected = new Card[]
				{
					card1,
					card2,
					card3,
				};
		result = zone.getCards();
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testRemoveBOTTOM() {
		Card[] expected = new Card[]
				{
					card1,
					card2,
				};
		Card[] result = zone.remove(2, ZonePick.BOTTOM);
		assertArrayEquals(expected, result);
		
		
		expected = new Card[]
				{
					card3,
					card4,
					card5,
				};
		result = zone.getCards();
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testRemoveRANDOM() {
		Card[] removedCards = zone.remove(2, ZonePick.RANDOM);
		
		int expected = 3;
		int result = zone.getCards().length;
		assertEquals(expected, result);
		
		for(Card c : removedCards)
		{
			Arrays.asList(cards).contains(c);
		}
		for(Card c : zone.getCards())
		{
			Arrays.asList(cards).contains(c);
		}
		
	}
	
	@Test
	public final void testRemoveCHOICE() {
		//Pr�paration
		when(cardArrayDisplayListener.chooseCards(2, cards)).thenReturn(new Card[]
				{
						card5,
						card1,
				});
		

		//V�rification de la sortie
		Card[] expected = new Card[]
				{
					card5,
					card1,
				};
		Card[] result = zone.remove(2, ZonePick.CHOICE);
		assertArrayEquals(expected, result);
		
		//V�rification des cartes qu'il reste
		expected = new Card[]
				{
					card2,
					card3,
					card4,
				};
		result = zone.getCards();
		assertArrayEquals(expected, result);
		
		//V�rification que toutes les cartes ont �t�s r�v�l�es (pour les afficher � l'utilisateur)
		for(Card c : cards)
		{
			verify(c, times(1)).setRevealed(true);
		}
	}
	
	@Test
	public final void testRemoveNbCardGreaterThanCards()
	{
		Card[] expected = cards;
		Card[] result = zone.remove(7);
		assertArrayEquals(expected, result);
		
		int expected2 = 0;
		int result2 = zone.getCards().length;
		assertEquals(expected2, result2);
	}
	
	@Test
	public final void testRemoveNbCardGreaterThanCardsWithOption()
	{
		Card[] expected = cards;
		Card[] result = zone.remove(7, ZonePick.BOTTOM);
		assertArrayEquals(expected, result);
		
		int expected2 = 0;
		int result2 = zone.getCards().length;
		assertEquals(expected2, result2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException1()
	{
		//on ne peut pas enlever moins de 0 cartes
		zone.remove(-3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException2()
	{
		//on ne peut pas enlever moins de 0 cartes
		zone.remove(-3, ZonePick.RANDOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException3()
	{
		//zonePick ne peut pas �tre null
		zone.remove(7, null);
	}
	
	@Test
	public final void testRemoveCard()
	{
		zone.remove(card4);
		
		Card[] expected = new Card[]
				{
						card1,
						card2,
						card3,
						card5,
				};
		Card[] result = zone.getCards();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveCardException()
	{
		zone.remove(null);
	}
	
	@Test
	public final void testRemoveAll()
	{
		Card[] expected = cards;
		Card[] result = zone.removeAll();
		assertArrayEquals(expected, result);
		
		int expected2 = 0;
		int result2 = zone.getCards().length;
		assertEquals(expected2, result2);
	}
	
	@Test
	public final void testSize()
	{
		int expected = cards.length;
		int result = zone.size();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetZoneType() {
		ZoneType expected = ZoneType.BURN;
		ZoneType result = zone.getZoneType();
		assertEquals(expected, result);
	}

	@Test
	public final void testShuffle() {
		
		zone.shuffle();
		
		int expected = 5;
		int result = zone.getCards().length;
		assertEquals(expected, result);
		
		for(Card c : zone.getCards())
		{
			Arrays.asList(cards).contains(c);
		}
	}

	@Test
	public final void testGetCards() {
		Card[] expected = cards;
		Card[] result = zone.getCards();
		assertArrayEquals(expected, result);
	}

	@Test
	public final void testReveal()
	{
		zone.reveal(2, ZonePick.BOTTOM);
		
		verify(card1, times(1)).setRevealed(true);
		verify(card2, times(1)).setRevealed(true);
		verify(card3, never()).setRevealed(true);
		verify(card4, never()).setRevealed(true);
		verify(card5, never()).setRevealed(true);
		
		//Reset de mock
		for(Card c : cards) { reset(c); }
		
		zone.reveal(2, ZonePick.TOP);
		
		verify(card1, never()).setRevealed(true);
		verify(card2, never()).setRevealed(true);
		verify(card3, never()).setRevealed(true);
		verify(card4, times(1)).setRevealed(true);
		verify(card5, times(1)).setRevealed(true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRevealException1()
	{
		zone.reveal(-3, ZonePick.BOTTOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRevealException2()
	{
		zone.reveal(2, ZonePick.CHOICE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRevealException3()
	{
		zone.reveal(2, ZonePick.RANDOM);
	}
	
	@Test
	public final void testHideCards() {
		zone.hideCards();
		for(Card c : zone.getCards())
		{
			verify(c, times(1)).setRevealed(false);
			verifyNoMoreInteractions(c);
		}
	}
	

	@Test
	public final void testRevealCards() {
		zone.revealCards();
		for(Card c : zone.getCards())
		{
			verify(c, times(1)).setRevealed(true);
			verifyNoMoreInteractions(c);
		}
	}
	

	@Test
	public final void testMoveCardToIndex() {
		zone.moveCardToIndex(1, 3);
		
		Card[] expected = new Card[]
				{
						card1,
						card3,
						card4,
						card2,
						card5
				};
		Card[] result = zone.getCards();
		assertArrayEquals(expected, result);
	}
	
	
	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	@Test
	public final void testListener()
	{
		ListChangeListener<Card> listener = (ListChangeListener<Card>) mock(ListChangeListener.class);
		
		
		
		zone.addListener(listener);
		
		zone.add(addedCards);
		verify(listener, times(3)).onChanged(any());
		
		
		
		reset(listener);
		
		zone.removeListener(listener);
		
		zone.add(addedCards);
		verify(listener, never()).onChanged(any());
	}

}
