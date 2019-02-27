package test_effect;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
import spell.Spell;
import target.Target;
import target.TargetType;

public class TestOneValueEffect {

	private class RealOneValueEffect extends OneValueEffect{

		public RealOneValueEffect(Target target, int value) {
			super(target, value);
		}

		@Override
		public ICondition matchingCondition() {
			return null;
		}

		@Override
		protected void applyOn(Character character, Game game, ISpell spell) {
			// TODO Auto-generated method stub
		}
		
	}
	
	private RealOneValueEffect vEffect;
	
	private Target target;
	
	private ITargetRequestListener targetRequestListener;
	
	private Game game;
	
	private ISpell spell;
	
	private IEffect[] effects;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		TargetableEffect.setTargetRequestListener(targetRequestListener = mock(ITargetRequestListener.class));
		
		target = mock(Target.class);
		vEffect =  new RealOneValueEffect(target, 5);
		
		game = mock(Game.class);
		
		spell = mock(Spell.class);
		
		RealOneValueEffect effect1 = mock(RealOneValueEffect.class);
		ConditionalEffect effect2 = mock(ConditionalEffect.class);
		
		effects = new IEffect[] { effect1, effect2};
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testOneValueEffect() {
		
		int expected = 5;
		int result = vEffect.getValue();
		assertEquals(expected, result);
		
		Target expectedT = target;
		Target resultT = vEffect.getTarget();
		assertEquals(expectedT, resultT);
		
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testOneValueEffectException() {
		vEffect = new RealOneValueEffect(target, -4);
	}
	
	@Test
	public final void testAddValue() {
		vEffect.addValue(4);
		int expected = 9;
		int result = vEffect.getValue();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testAddValueException() {
		vEffect.addValue(-4);
	}
	
	@Test
	public final void testPrepare() {
		when(vEffect.getTarget().getType()).thenReturn(TargetType.YOU);
		vEffect.prepare(game, spell);
		verifyNoMoreInteractions(game);
		verifyNoMoreInteractions(spell);
		
		when(vEffect.getTarget().getType()).thenReturn(TargetType.MORE);
		when(spell.getEffects()).thenReturn(effects);
		vEffect.prepare(game, spell);
		
	}

}
