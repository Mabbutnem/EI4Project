package test_spell;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import effect.ConditionalEffect;
import effect.IEffect;
import effect.TargetableEffect;
import spell.Incantation;
import target.Target;
import target.TargetType;

public class TestIncantation
{
	private Incantation i;
	
	private Target target;
	
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
		target = mock(Target.class);
		when(target.getType()).thenReturn(TargetType.AREA);
		
		TargetableEffect effect = mock(TargetableEffect.class);
		when(effect.getTarget()).thenReturn(target);
		
		effects = new IEffect[]
				{
						effect,
						mock(IEffect.class)
				};
		
		i = new Incantation("incantation", effects);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	@Test (expected = IllegalArgumentException.class)
	public final void testIncantationException1()
	{
		//The first effect of an incantation has to be an effect with a target...
		
		effects = new IEffect[]
				{
						mock(IEffect.class),
						mock(IEffect.class)
				};
		
		i = new Incantation("incantation", effects);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testIncantationException2()
	{
		//If you want to add a CHOICE target type effects in your incantations effects,
		//you have to set the first effect has a CHOICE target type effect
		
		Target choiceTarget = mock(Target.class);
		when(choiceTarget.getType()).thenReturn(TargetType.CHOICE);
		
		TargetableEffect effect1 = mock(TargetableEffect.class);
		when(effect1.getTarget()).thenReturn(choiceTarget);

		
		
		effects = new IEffect[]
				{
						mock(IEffect.class),
						effect1
				};
		
		i = new Incantation("incantation", effects);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testIncantationException3()
	{
		//If you want to add a CHOICE target type effects in your incantations effects,
		//you have to set the first effect has a CHOICE target type effect
		
		//Deep choice target type effect in the tree;
		
		Target choiceTarget = mock(Target.class);
		when(choiceTarget.getType()).thenReturn(TargetType.CHOICE);
		
		TargetableEffect effect1 = mock(TargetableEffect.class);
		when(effect1.getTarget()).thenReturn(choiceTarget);
		
		ConditionalEffect cEffect = mock(ConditionalEffect.class);
		when(cEffect.getEffects()).thenReturn(new IEffect[]
				{
						mock(IEffect.class),
						effect1
				});

		
		
		effects = new IEffect[]
				{
						mock(IEffect.class),
						cEffect
				};
		
		i = new Incantation("incantation", effects);
	}
	
	@Test
	public final void testIncantationPossibleCase()
	{
		//If you want to add a CHOICE target type effects in your incantations effects,
		//you have to set the first effect has a CHOICE target type effect
		
		Target choiceTarget = mock(Target.class);
		when(choiceTarget.getType()).thenReturn(TargetType.CHOICE);
		
		TargetableEffect effect1 = mock(TargetableEffect.class);
		when(effect1.getTarget()).thenReturn(choiceTarget);
		
		TargetableEffect effect2 = mock(TargetableEffect.class);
		when(effect2.getTarget()).thenReturn(choiceTarget);

		
		
		effects = new IEffect[]
				{
						effect1,
						mock(IEffect.class),
						effect2
				};
		
		i = new Incantation("incantation", effects);
	}

	@Test
	public final void testIncantationIncantation()
	{
		Incantation iCopy = new Incantation(i);
		
		assertTrue(i != iCopy);
		
		String expectedS = i.getName();
		String resultS = iCopy.getName();
		assertEquals(expectedS, resultS);
		
		IEffect[] expectedE = i.getEffects();
		IEffect[] resultE = iCopy.getEffects();
		assertArrayEquals(expectedE, resultE);
	}

	@Test
	public final void testGetFirstTarget()
	{
		Target expected = target;
		Target result = i.getFirstTarget();
		assertEquals(expected, result);
	}

	@Test
	public final void testCloneObject()
	{
		Incantation iCopy = (Incantation) i.cloneObject();
		
		assertTrue(i != iCopy);
		
		String expectedS = i.getName();
		String resultS = iCopy.getName();
		assertEquals(expectedS, resultS);
		
		IEffect[] expectedE = i.getEffects();
		IEffect[] resultE = iCopy.getEffects();
		assertArrayEquals(expectedE, resultE);
	}

}
