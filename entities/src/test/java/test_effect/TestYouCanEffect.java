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
import effect.YouCanEffect;
import game.Game;
import listener.IYouCanEffectListener;
import spell.ISpell;

public class TestYouCanEffect
{
	private YouCanEffect youCanEffect;
	
	private IEffect[] effects;
	private IApplicableEffect[] aEffects;
	
	private IEffect effect1;
	private IEffect effect2;
	private IEffect effect3;
	private IEffect aEffect1;
	private IEffect aEffect2;
	private IEffect aEffect3;
	
	private IApplicableEffect aEffect;
	
	private IYouCanEffectListener youCanEffectListener;
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		YouCanEffect.setYouCanEffectListener(youCanEffectListener = mock(IYouCanEffectListener.class));
		
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
		
		aEffect = mock(IApplicableEffect.class);
		
		youCanEffect = new YouCanEffect(effects, aEffect);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	@Test
	public final void testYouCanEffect() {
		//You can have 0 effect inside a youCanEffect
		youCanEffect = new YouCanEffect(new IEffect[0], aEffect);
		
		IEffect[] expected = new IEffect[0];
		IEffect[] result = youCanEffect.getEffects();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testYouCanEffectException2() {
		youCanEffect = new YouCanEffect(effects, null);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testYouCanEffectException3() {
		YouCanEffect.setYouCanEffectListener(null);
		youCanEffect = new YouCanEffect(effects, aEffect);
	}
	
	@Test
	public final void testPrepareApplyCleanIfDontWantToApply()
	{
		//Initialisation :
		when(youCanEffectListener.wantToApply(aEffect)).thenReturn(false);
		
		Game game = mock(Game.class);
		ISpell spell = mock(ISpell.class);
		
		@SuppressWarnings("unchecked")
		Predicate<Game> predicate = (Predicate<Game>) mock(Predicate.class);
		
		ICondition condition = mock(ICondition.class);
		when(condition.getPredicate()).thenReturn(predicate);
		
		when(aEffect.matchingCondition()).thenReturn(condition);
		
		
		
		//if predicate test is true
		when(predicate.test(game)).thenReturn(true);
		
		//prepare: (never prepare the effects)
		youCanEffect.prepare(game, spell);
		for(IEffect e : effects) { verify(e, never()).prepare(game, spell); }
		
		//apply: (never apply the effects)
		youCanEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
		
		//clean: (never clean the effects)
		youCanEffect.clean();
		for(IEffect e : effects) { verify(e, never()).clean(); }
		
		
		
		//if predicate test is false
		when(predicate.test(game)).thenReturn(false);
		
		//prepare: (never prepare the effects)
		youCanEffect.prepare(game, spell);
		for(IEffect e : effects) { verify(e, never()).prepare(game, spell); }
		
		//apply: (never apply the effects)
		youCanEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
		
		//clean: (never clean the effects)
		youCanEffect.clean();
		for(IEffect e : effects) { verify(e, never()).clean(); }
	}
	
	@Test
	public final void testPrepareApplyCleanIfWillApplyTrue()
	{
		//Initialisation :
		when(youCanEffectListener.wantToApply(aEffect)).thenReturn(true);
		
		Game game = mock(Game.class);
		ISpell spell = mock(ISpell.class);
		
		@SuppressWarnings("unchecked")
		Predicate<Game> predicate = (Predicate<Game>) mock(Predicate.class);
		when(predicate.test(game)).thenReturn(true);
		
		ICondition condition = mock(ICondition.class);
		when(condition.getPredicate()).thenReturn(predicate);
		
		when(aEffect.matchingCondition()).thenReturn(condition);
		
		
		
		
		//do not apply effects before preparing:
		youCanEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
		
		//prepare:
		youCanEffect.prepare(game, spell);
		for(IEffect e : effects) { verify(e, times(1)).prepare(game, spell); }
		
		//apply:
		youCanEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, times(1)).apply(game, spell); }
		
		//clean:
		youCanEffect.clean();
		for(IEffect e : effects) { verify(e, times(1)).clean(); }
		
		//do not apply effects anymore after cleaning
		for(IApplicableEffect e : aEffects) { reset(e); }
		youCanEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
	}
	
	@Test
	public final void testPrepareApplyCleanIfWillApplyFalse()
	{
		//Initialisation :
		when(youCanEffectListener.wantToApply(aEffect)).thenReturn(true);
		
		Game game = mock(Game.class);
		ISpell spell = mock(ISpell.class);
		
		@SuppressWarnings("unchecked")
		Predicate<Game> predicate = (Predicate<Game>) mock(Predicate.class);
		when(predicate.test(game)).thenReturn(false);
		
		ICondition condition = mock(ICondition.class);
		when(condition.getPredicate()).thenReturn(predicate);
		
		when(aEffect.matchingCondition()).thenReturn(condition);
		
		
		
		
		//prepare: (never prepare the effects)
		youCanEffect.prepare(game, spell);
		for(IEffect e : effects) { verify(e, never()).prepare(game, spell); }
		
		//apply: (never apply the effects)
		youCanEffect.apply(game, spell);
		for(IApplicableEffect e : aEffects) { verify(e, never()).apply(game, spell); }
		
		//clean: (never clean the effects)
		youCanEffect.clean();
		for(IEffect e : effects) { verify(e, never()).clean(); }
	}

	@Test
	public final void testGetDescription() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetEffects()
	{
		IEffect[] expected = effects;
		IEffect[] result = youCanEffect.getEffects();
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testMatchingCondition()
	{
		boolean expected = false;
		boolean result = youCanEffect.matchingCondition().getPredicate().test(mock(Game.class));
		assertEquals(expected, result);
	}

}
