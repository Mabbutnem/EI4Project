package test_spell;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import effect.IEffect;
import spell.ManaCostSpell;
import utility.INamedObject;

public class TestManaCostSpell
{
	private class RealManaCostSpell extends ManaCostSpell
	{

		public RealManaCostSpell(String name, IEffect[] effects, int cost) { super(name, effects, cost); }

		@Override
		public INamedObject cloneObject() {return null;}
		
	}
	
	
	
	private RealManaCostSpell manaCostSpell;
	
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
		
		manaCostSpell = new RealManaCostSpell("spell", effects, 1);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	@Test
	public final void testManaCostSpell()
	{
		int expected = 1;
		int result = manaCostSpell.getCost();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testManaCostSpellException()
	{
		manaCostSpell = new RealManaCostSpell("spell", effects, -3);
	}

}
