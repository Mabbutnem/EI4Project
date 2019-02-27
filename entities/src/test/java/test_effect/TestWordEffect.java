package test_effect;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import effect.Word;
import effect.WordEffect;
import game.Game;
import spell.Spell;

public class TestWordEffect
{	
	private WordEffect we;
	

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		we = new WordEffect(Word.PIERCE);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	@Test (expected = IllegalArgumentException.class)
	public final void testWordEffectException()
	{
		we = new WordEffect(null);
	}

	@Test
	public final void testGetWord()
	{
		Word expected = Word.PIERCE;
		Word result = we.getWord();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetDescription()
	{
		String expected = "PIERCE";
		String result = we.getDescription();
		assertEquals(expected, result);
	}

	@Test
	public final void testPrepare()
	{
		Spell spell = mock(Spell.class);
		
		we.prepare(mock(Game.class), spell);
		
		verify(spell).addWord(Word.PIERCE);
	}

	@Test
	public final void testClean()
	{
		//Nothing appends
	}

}
