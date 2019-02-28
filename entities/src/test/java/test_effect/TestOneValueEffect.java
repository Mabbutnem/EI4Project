package test_effect;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Character;
import condition.ICondition;
import effect.ConditionalEffect;
import effect.IEffect;
import effect.OneValueEffect;
import effect.TargetableEffect;
import game.Game;
import listener.ITargetRequestListener;
import spell.ISpell;
import target.Target;
import target.TargetType;

public class TestOneValueEffect {

	private class RealOneValueEffect extends OneValueEffect{

		public RealOneValueEffect(Target target, int value) { super(target, value); }

		@Override
		public ICondition matchingCondition() { return null; }

		@Override
		protected void applyOn(Character character, Game game, ISpell spell) {}
		
	}
	
	private class OtherRealOneValueEffect extends OneValueEffect{

		public OtherRealOneValueEffect(Target target, int value) { super(target, value); }

		@Override
		public ICondition matchingCondition() { return null; }

		@Override
		protected void applyOn(Character character, Game game, ISpell spell) {}
		
	}
	
	private RealOneValueEffect effect;
	
	private Target target;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		
		target = mock(Target.class);
		
		effect =  new RealOneValueEffect(target, 5);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testOneValueEffect() {
		
		int expected = 5;
		int result = effect.getValue();
		assertEquals(expected, result);
		
		Target expectedT = target;
		Target resultT = effect.getTarget();
		assertEquals(expectedT, resultT);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testOneValueEffectException() {
		effect = new RealOneValueEffect(target, -4);
	}
	
	@Test
	public final void testAddValue() {
		effect.addValue(4);
		int expected = 9;
		int result = effect.getValue();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddValueException() {
		effect.addValue(-4);
	}
	
	@Test
	public final void testPrepareWithTargetTypeOtherThanMore()
	{
		ISpell spell = mock(ISpell.class);
		Game game = mock(Game.class);
		
		when(target.getType()).thenReturn(TargetType.CHOICE);
		
		effect.prepare(game, spell);
		
		verifyNoMoreInteractions(spell);
		verifyNoMoreInteractions(game);
	}
	
	@Test
	public final void testPrepareWithMoreTargetType()
	{
		//Initialisation:
		ISpell spell = mock(ISpell.class);
		
		Game game = mock(Game.class);

		when(target.getType()).thenReturn(TargetType.MORE);

		//On ne mock pas ces objets car on ne peut pas stubber leur méthode getClass() qui seront utilisées dans la fonction prepare
		RealOneValueEffect effectToAddValue = new RealOneValueEffect(mock(Target.class), 3);
		OtherRealOneValueEffect uselessEffect1 = new OtherRealOneValueEffect(mock(Target.class), 0);
		OtherRealOneValueEffect uselessEffect2 = new OtherRealOneValueEffect(mock(Target.class), 0);
		OtherRealOneValueEffect uselessEffect3 = new OtherRealOneValueEffect(mock(Target.class), 0);
		
		ConditionalEffect condEffect = mock(ConditionalEffect.class);
		
		
		
		
		
		//cas 1: (au même niveau dans l'arbre)
		when(spell.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect1,
						effectToAddValue,
						uselessEffect2,
						effect
				});
		
		effect.prepare(game, spell);
		
		verify(spell, atLeastOnce()).getEffects();
		verifyNoMoreInteractions(spell);
		
		verifyNoMoreInteractions(game);
		
		int expected = 3+5;
		int result = effectToAddValue.getValue();
		assertEquals(expected, result);
		
		expected = 5;
		result = effect.getValue();
		assertEquals(expected, result);
		
		expected = 0;
		result = uselessEffect1.getValue(); assertEquals(expected, result);
		result = uselessEffect2.getValue(); assertEquals(expected, result);
		result = uselessEffect3.getValue(); assertEquals(expected, result);
		
		
		

		
		//cas 2: (au même niveau dans l'arbre avec un effet conditionnel entre les deux)
		
		//resets:
		reset(spell);
		reset(game);
		effectToAddValue = new RealOneValueEffect(mock(Target.class), 3);
		
		when(spell.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect1,
						effectToAddValue,
						condEffect,
						effect
				});
		
		when(condEffect.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect2,
						uselessEffect3
				});
		
		effect.prepare(game, spell);
		
		verify(spell, atLeastOnce()).getEffects();
		verifyNoMoreInteractions(spell);
		
		verifyNoMoreInteractions(game);
		
		expected = 3+5;
		result = effectToAddValue.getValue();
		assertEquals(expected, result);
		
		expected = 5;
		result = effect.getValue();
		assertEquals(expected, result);
		
		expected = 0;
		result = uselessEffect1.getValue(); assertEquals(expected, result);
		result = uselessEffect2.getValue(); assertEquals(expected, result);
		result = uselessEffect3.getValue(); assertEquals(expected, result);
		
		
		
		

		//cas 3: (effect à 1 niveau de profondeur de effectToAddValue dans l'arbre)
		
		//resets:
		reset(spell);
		reset(game);
		effectToAddValue = new RealOneValueEffect(mock(Target.class), 3);
		
		when(spell.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect1,
						effectToAddValue,
						condEffect,
						
				});
		
		when(condEffect.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect2,
						effect,
						uselessEffect3
				});
		
		effect.prepare(game, spell);
		
		verify(spell, atLeastOnce()).getEffects();
		verifyNoMoreInteractions(spell);
		
		verifyNoMoreInteractions(game);
		
		expected = 3+5;
		result = effectToAddValue.getValue();
		assertEquals(expected, result);
		
		expected = 5;
		result = effect.getValue();
		assertEquals(expected, result);
		
		expected = 0;
		result = uselessEffect1.getValue(); assertEquals(expected, result);
		result = uselessEffect2.getValue(); assertEquals(expected, result);
		result = uselessEffect3.getValue(); assertEquals(expected, result);
		
	}

	@Test (expected = IllegalStateException.class)
	public final void testPrepareWithMoreTargetTypeException1()
	{
		//Initialisation:
		ISpell spell = mock(ISpell.class);
		
		when(target.getType()).thenReturn(TargetType.MORE);

		//On ne mock pas ces objets car on ne peut pas stubber leur méthode getClass() qui seront utilisées dans la fonction prepare
		OtherRealOneValueEffect uselessEffect1 = new OtherRealOneValueEffect(mock(Target.class), 0);
		OtherRealOneValueEffect uselessEffect2 = new OtherRealOneValueEffect(mock(Target.class), 0);
		
		
		
		//cas 4: (pas d'effectToAddValue au dessus de effect)
		when(spell.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect1,
						uselessEffect2,
						effect
				});
		
		effect.prepare(mock(Game.class), spell);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testPrepareWithMoreTargetTypeException2()
	{
		//Initialisation:
		ISpell spell = mock(ISpell.class);
		
		when(target.getType()).thenReturn(TargetType.MORE);

		//On ne mock pas ces objets car on ne peut pas stubber leur méthode getClass() qui seront utilisées dans la fonction prepare
		RealOneValueEffect effectToAddValue = new RealOneValueEffect(mock(Target.class), 3);
		OtherRealOneValueEffect uselessEffect1 = new OtherRealOneValueEffect(mock(Target.class), 0);
		OtherRealOneValueEffect uselessEffect2 = new OtherRealOneValueEffect(mock(Target.class), 0);
		
		
		
		//cas 5: (effectToAddValue situé après effect)
		when(spell.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect1,
						uselessEffect2,
						effect,
						effectToAddValue
				});
		
		effect.prepare(mock(Game.class), spell);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testPrepareWithMoreTargetTypeException3()
	{
		//Initialisation:
		ISpell spell = mock(ISpell.class);
		
		when(target.getType()).thenReturn(TargetType.MORE);

		//On ne mock pas ces objets car on ne peut pas stubber leur méthode getClass() qui seront utilisées dans la fonction prepare
		RealOneValueEffect effectToAddValue = new RealOneValueEffect(mock(Target.class), 3);
		OtherRealOneValueEffect uselessEffect1 = new OtherRealOneValueEffect(mock(Target.class), 0);
		OtherRealOneValueEffect uselessEffect2 = new OtherRealOneValueEffect(mock(Target.class), 0);
		
		ConditionalEffect condEffect = mock(ConditionalEffect.class);
		
		
		
		//cas 6: (effectToAddValue placé avant effect mais à 1 niveau de profodeur supplémentaire)
		when(spell.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect1,
						condEffect,
						effect
				});
		when(condEffect.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect2,
						effectToAddValue
				});
		
		effect.prepare(mock(Game.class), spell);
	}
	
	@Test
	public final void testClean()
	{
		effect.addValue(10);
		
		effect.clean();
		
		int expected = 5;
		int result = effect.getValue();
		assertEquals(expected, result);
	}

}
