package test_zone;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import listener.ICardArrayRequestListener;
import spell.Card;
import zone.AutoRevealZone;
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;

public class TestAutoRevealZone
{
	private class MockCardArrayDisplayListener implements ICardArrayRequestListener
	{

		@Override
		public Card[] chooseCards(int nbCard, Card[] cards) { return null; }

		@Override
		public Card[] chooseCards(Card[] cards) { return null; }
	}

	private AutoRevealZone zone;
	
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
		Zone.setCardArrayDisplayListener(mock(MockCardArrayDisplayListener.class));
		
		addedCards = new Card[]
				{
						mock(Card.class),
						mock(Card.class),
						mock(Card.class)
				};
		
		cards = new Card[]
				{
						mock(Card.class), //BOTTOM
						mock(Card.class),
						mock(Card.class),
						mock(Card.class),
						mock(Card.class) //TOP
				};
		
		zone = new AutoRevealZone(cards, ZoneType.BURN, ZonePick.TOP);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	
	@Test
	public final void testAutoRevealZone()
	{
		for(Card c : zone.getCards())
		{
			verify(c, times(1)).setRevealed(true);
			verifyNoMoreInteractions(c);
		}
	}
	
	@Test
	public final void testAdd() {
		zone.add(addedCards);
		for(Card c : addedCards)
		{
			verify(c, times(1)).setRevealed(true);
			verifyNoMoreInteractions(c);
		}
	}

	@Test
	public final void testShuffle()
	{
		zone.shuffle();
		for(Card c : zone.getCards())
		{
			verify(c, atLeastOnce()).setRevealed(true);
			verifyNoMoreInteractions(c);
		}
	}

}
