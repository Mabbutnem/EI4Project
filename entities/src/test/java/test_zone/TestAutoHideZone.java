package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import spell.Card;
import zone.AutoHideZone;
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;

public class TestAutoHideZone
{

	private AutoHideZone zone;
	
	private Card[] cards;
	private Card card1;
	private Card card2;
	private Card card3;
	private Card card4;
	private Card card5;
	
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
	public final void testRemoveCHOICE()
	{
		Card[] expected = new Card[]
				{
						card5,
						card4,
				};
		Card[] result = zone.remove(2, ZonePick.CHOICE);
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
		
		
		for(Card c : zone.getCards())
		{
			verify(c, times(1)).setRevealed(true);
			verify(c, atLeastOnce()).setRevealed(false);
			verifyNoMoreInteractions(c);
		}
		
		
		expected = new Card[]
				{
						card1,
						card2,
						card3,
				};
		for(Card c : zone.getCards())
		{
			Arrays.asList(expected).contains(c);
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
