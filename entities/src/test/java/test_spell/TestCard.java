package test_spell;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import effect.IEffect;
import listener.ICardListener;
import spell.Card;

public class TestCard
{
	private Card c;
	
	private IEffect[] effects;
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		effects = new IEffect[]
				{
						mock(IEffect.class),
						mock(IEffect.class)
				};
		
		c = new Card("card", effects, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public final void testCard()
	{
		boolean expected = true;
		boolean result = c.isRevealed();
		assertEquals(expected, result);
		
		assertTrue(Arrays.asList(c.getCardListeners()).isEmpty());
	}
	
	@Test
	public final void testCardCard()
	{
		Card cCopy = new Card(c);
		
		assertTrue(c != cCopy);
		
		String expectedS = c.getName();
		String resultS = cCopy.getName();
		assertEquals(expectedS, resultS);
		
		IEffect[] expectedE = c.getEffects();
		IEffect[] resultE = cCopy.getEffects();
		assertArrayEquals(expectedE, resultE);
		
		int expectedI = c.getCost();
		int resultI = cCopy.getCost();
		assertEquals(expectedI, resultI);
	}

	@Test
	public final void testSetRevealed()
	{
		ICardListener listener1 = mock(ICardListener.class);
		ICardListener listener2 = mock(ICardListener.class);
		
		c.addCardListener(listener1);
		c.addCardListener(listener2);
		
		
		
		
		c.setRevealed(false);
		
		boolean expected = false;
		boolean result = c.isRevealed();
		assertEquals(expected, result);
		
		verify(listener1, times(1)).onRevealedChange(false);
		verify(listener2, times(1)).onRevealedChange(false);
		
		
		
		
		reset(listener1);
		reset(listener2);
		
		c.setRevealed(true);
		
		expected = true;
		result = c.isRevealed();
		assertEquals(expected, result);
		
		verify(listener1, times(1)).onRevealedChange(true);
		verify(listener2, times(1)).onRevealedChange(true);
		
		
		
		
		reset(listener1);
		reset(listener2);

		c.setRevealed(true);
		
		//The value is the same, it don't trigger the listeners
		verify(listener1, never()).onRevealedChange(anyBoolean());
		verify(listener2, never()).onRevealedChange(anyBoolean());
	}

	@Test
	public final void testCardListener()
	{
		ICardListener listener = mock(ICardListener.class);
		
		c.addCardListener(listener);
		assertTrue(Arrays.asList(c.getCardListeners()).contains(listener));
		
		c.removeCardListener(listener);
		assertTrue(Arrays.asList(c.getCardListeners()).isEmpty());
	}

	@Test
	public final void testCloneObject()
	{
		Card cCopy = (Card) c.cloneObject();
		
		assertTrue(c != cCopy);
		
		String expectedS = c.getName();
		String resultS = cCopy.getName();
		assertEquals(expectedS, resultS);
		
		IEffect[] expectedE = c.getEffects();
		IEffect[] resultE = cCopy.getEffects();
		assertArrayEquals(expectedE, resultE);
		
		int expectedI = c.getCost();
		int resultI = cCopy.getCost();
		assertEquals(expectedI, resultI);
	}

}
