package test_effect;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.function.Predicate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import condition.ICondition;
import effect.IApplicableEffect;
import effect.IEffect;
import effect.IfEffect;
import game.Game;
import spell.ISpell;

public class TestIfEffect
{
	private IfEffect ifEffect;
	
	private IEffect[] effects;
	private IApplicableEffect[] aEffects;
	
	private IEffect aEffect1;
	private IEffect aEffect2;
	private IEffect aEffect3;
	
	private IEffect effect1;
	private IEffect effect2;
	private IEffect effect3;
	
	private ICondition condition;
	
	

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
						effect1 = mock(IEffect.class),
						aEffect1 = mock(IApplicableEffect.class),
						effect2 = mock(IEffect.class),
						aEffect2 = mock(IApplicableEffect.class),
						effect3 = mock(IEffect.class),
						aEffect3 = mock(IApplicableEffect.class)
				};
		
		aEffects = new IApplicableEffect[]
				{
						(IApplicableEffect) aEffect1,						
						(IApplicableEffect) aEffect2,
						(IApplicableEffect) aEffect3
				};
		when(effect1.getDescription()).thenReturn("inflict 5 damage");
		when(effect2.getDescription()).thenReturn("heal 4 HP to yourself");
		when(effect3.getDescription()).thenReturn("draw 2 cards");
		
		when(aEffect1.getDescription()).thenReturn("inflict 3 damage");
		when(aEffect2.getDescription()).thenReturn("heal 2 HP");
		when(aEffect3.getDescription()).thenReturn("draw 1 card");
		condition = mock(ICondition.class);
		when(condition.getDescription()).thenReturn("if you have less than 5 HP");
		
		ifEffect = new IfEffect(effects, condition);
	}

	@After
	public void tearDown() throws Exception {
	}



	@Test (expected = IllegalArgumentException.class)
	public final void testIfEffectException1() {
		ifEffect = new IfEffect(new IEffect[0], condition);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testIfEffectException2() {
		ifEffect = new IfEffect(effects, null);
	}
	
	@Test
	public final void testPrepareApplyCleanIfWillApplyTrue()
	{
		//Initialisation :
		Game game = mock(Game.class);
		ISpell spell = mock(ISpell.class);
	
		@SuppressWarnings("unchecked")
		Predicate<Game> predicate = (Predicate<Game>) mock(Predicate.class);
		
		when(predicate.test(game)).thenReturn(true);
		
		when(condition.getPredicate()).thenReturn(predicate);
		
		
		
		
		//do not apply effects before preparing:
		ifEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
		
		//prepare:
		ifEffect.prepare(game, spell);
		for(IEffect e : effects) { verify(e, times(1)).prepare(game, spell); }
		
		//apply:
		ifEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, times(1)).apply(game, spell); }
		
		//clean:
		ifEffect.clean();
		for(IEffect e : effects) { verify(e, times(1)).clean(); }
		
		//do not apply effects anymore after cleaning
		for(IApplicableEffect e : aEffects) { reset(e); }
		ifEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
	}
	
	@Test
	public final void testPrepareApplyCleanIfWillApplyFalse()
	{
		//Initialisation :
		Game game = mock(Game.class);
		ISpell spell = mock(ISpell.class);
	
		@SuppressWarnings("unchecked")
		Predicate<Game> predicate = (Predicate<Game>) mock(Predicate.class);
		
		when(predicate.test(game)).thenReturn(false);
		
		when(condition.getPredicate()).thenReturn(predicate);
		
		
		
		
		//prepare: (never prepare the effects)
		ifEffect.prepare(game, spell);
		for(IEffect e : effects) { verify(e, never()).prepare(game, spell); }
		
		//apply: (never apply the effects)
		ifEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
		
		//clean: (never clean the effects)
		ifEffect.clean();
		for(IEffect e : effects) { verify(e, never()).clean(); }
	}

	@Test
	public final void testGetDescription() {
		String expected = "if you have less than 5 HP :\ninflict 5 damage, inflict 3 damage, heal 4 HP to yourself, heal 2 HP, draw 2 cards and draw 1 card";
		String result = ifEffect.getDescription();
		
		assertEquals(expected, result);
	}

	@Test
	public final void testGetEffects()
	{
		IEffect[] expected = effects;
		IEffect[] result = ifEffect.getEffects();
		assertArrayEquals(expected, result);
	}

	@Test
	public final void testMatchingCondition()
	{
		boolean expected = false;
		boolean result = ifEffect.matchingCondition().getPredicate().test(mock(Game.class));
		assertEquals(expected, result);
	}

}
