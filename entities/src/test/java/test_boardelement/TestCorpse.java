package test_boardelement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Corpse;
import boardelement.Monster;
import constant.CorpseConstant;

public class TestCorpse {

	private Corpse corpse;
	private Monster m;
	private CorpseConstant corpseC;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		corpseC = mock(CorpseConstant.class);
		when(corpseC.getNbTurnToReborn()).thenReturn(1);
		Corpse.setCorpseConstant(corpseC);
		m = mock(Monster.class);
		when(m.getRebornProbability()).thenReturn(1.0f);
		corpse = new Corpse(m);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCorpse() {
		int expected = 0;
		int result = corpse.getCounterToReborn();
		assertEquals(expected, result);
		
		boolean expectedB = true;
		boolean resultB = corpse.isWillReborn();
		assertEquals(expectedB, resultB);
		
		Monster expectedM = m;
		Monster resultM = corpse.getMonster();
		assertEquals(expectedM, resultM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCorpseException() {
		corpse = new Corpse(null);
	}
	
	@Test
	public void testGetCorpseConstant() {
		CorpseConstant expected = corpseC;
		CorpseConstant result = Corpse.getCorpseConstant();
		assertEquals(expected, result);
	}
	
	@Test
	public void testIncCounterToReborn() {
		corpse.incrCounterToReborn();
		int expected = 1;
		int result = corpse.getCounterToReborn();
		assertEquals(expected, result);
	}
	
	@Test
	public void testCounterReachedReborn() {
		boolean expected = false;
		boolean result = corpse.counterReachedReborn();
		assertEquals(expected, result);
		
		corpse.incrCounterToReborn();
		
		expected = true;
		result = corpse.counterReachedReborn();
		assertEquals(expected, result);
	}

}
