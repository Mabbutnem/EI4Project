package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import listener.ICardArrayRequestListener;
import spell.Card;
import zone.AutoHideZone;
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;

public class TestAutoHideZone
{
	private class MockCardArrayDisplayListener implements ICardArrayRequestListener
	{

		@Override
		public Card[] chooseCards(int nbCard, Card[] cards) { return null; }

		@Override
		public Card[] chooseCards(Card[] cards) { return null; }
	}
	
	private AutoHideZone zone;
	
	private Card[] cards;
	private Card card1;
	private Card card2;
	private Card card3;
	private Card card4;
	private Card card5;
	
	private Card[] addedCards;

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
						mock(Card.class),
						mock(Card.class),
						mock(Card.class)
				};
		
		cards = new Card[]
				{
						card1 = mock(Card.class), //BOTTOM
						card2 = mock(Card.class),
						card3 = mock(Card.class),
						card4 = mock(Card.class),
						card5 = mock(Card.class) //TOP
				};
		
		zone = new AutoHideZone(cards, ZoneType.BURN, ZonePick.TOP);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	
	@Test
	public final void testAutoHideZone() {
		for(Card c : zone.getCards())
		{
			verify(c, times(1)).setRevealed(false);
			verifyNoMoreInteractions(c);
		}
	}
	
	@Test
	public final void testAdd() {
		zone.add(addedCards);
		for(Card c : addedCards)
		{
			verify(c, times(1)).setRevealed(false);
			verifyNoMoreInteractions(c);
		}
	}
	
	@Test
	public final void testRemoveCHOICE() {
		//Préparation
		when(cardArrayDisplayListener.chooseCards(2, cards)).thenReturn(new Card[]
				{
						card5,
						card1,
				});
		

		//Vérification de la sortie
		Card[] expected = new Card[]
				{
					card5,
					card1,
				};
		Card[] result = zone.remove(2, ZonePick.CHOICE);
		assertArrayEquals(expected, result);
		
		//Vérification des cartes qu'il reste, quelles ont étés mélangées et cachées
		expected = new Card[]
				{
					card2,
					card3,
					card4,
				};
		result = zone.getCards();
		for(Card c : expected)
		{
			assertTrue(Arrays.asList(result).contains(c));
			verify(c, atLeastOnce()).setRevealed(false);
		}
		
		//Vérification que toutes les cartes ont étés révélées (pour les afficher à l'utilisateur)
		for(Card c : cards)
		{
			verify(c, times(1)).setRevealed(true);
		}
	}

	@Test
	public final void testShuffle() {
		zone.shuffle();
		for(Card c : zone.getCards())
		{
			verify(c, atLeastOnce()).setRevealed(false);
			verifyNoMoreInteractions(c);
		}
	}

}
