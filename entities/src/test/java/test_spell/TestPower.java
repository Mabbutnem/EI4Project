package test_spell;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import effect.IEffect;
import spell.Power;

public class TestPower
{
	private Power p;
	
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
		
		p = new Power("power", effects, 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	

	@Test
	public final void testPowerPower()
	{
		Power pCopy = new Power(p);
		
		assertTrue(p != pCopy);
		
		String expectedS = p.getName();
		String resultS = pCopy.getName();
		assertEquals(expectedS, resultS);
		
		IEffect[] expectedE = p.getEffects();
		IEffect[] resultE = pCopy.getEffects();
		assertArrayEquals(expectedE, resultE);
		
		int expectedI = p.getCost();
		int resultI = pCopy.getCost();
		assertEquals(expectedI, resultI);
	}

	@Test
	public final void testCloneObject()
	{
		Power pCopy = (Power) p.cloneObject();
		
		assertTrue(p != pCopy);
		
		String expectedS = p.getName();
		String resultS = pCopy.getName();
		assertEquals(expectedS, resultS);
		
		IEffect[] expectedE = p.getEffects();
		IEffect[] resultE = pCopy.getEffects();
		assertArrayEquals(expectedE, resultE);
		
		int expectedI = p.getCost();
		int resultI = pCopy.getCost();
		assertEquals(expectedI, resultI);
	}

}
