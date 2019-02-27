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
import effect.TargetableEffect;
import game.Game;
import listener.ITargetRequestListener;
import spell.ISpell;
import target.Target;
import target.TargetConstraint;
import target.TargetType;

public class TestTargetableEffect
{
	
	private class RealTargetableEffect extends TargetableEffect
	{

		public RealTargetableEffect(Target target) { super(target); }

		@Override
		public ICondition matchingCondition() { return null; }

		@Override
		public void prepare(Game game, ISpell spell) {}

		@Override
		public void clean() {}

		@Override
		//Pour tester que applyOn � �t� appel�
		protected void applyOn(Character character, Game game, ISpell spell)
		{
			character.getHealth();
			game.getBoard();
			spell.getName();
		}
		
	}
	
	private RealTargetableEffect te;
	
	private Target target;
	private TargetConstraint[] constraints;
	
	private Game game;
	private Character c0;
	private Character c1;
	private Character[] characters;
	private ISpell spell;
	
	private ITargetRequestListener targetRequestListener;
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		TargetableEffect.setTargetRequestListener(targetRequestListener = mock(ITargetRequestListener.class));
		
		when(targetRequestListener.chooseTarget(game)).thenReturn(c0);
		
		target = mock(Target.class);
		when(target.getConstraints()).thenReturn(constraints);
		
		game = mock(Game.class);
		c0 = mock(Character.class);
		c1 = mock(Character.class);
		characters = new Character[] {c0, c1};
		spell = mock(ISpell.class);
		
		te = new RealTargetableEffect(target);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	@Test (expected = IllegalStateException.class)
	public final void testTargetableEffectException1() {
		TargetableEffect.setTargetRequestListener(null);
		te = new RealTargetableEffect(target);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testTargetableEffectException2() {
		te = new RealTargetableEffect(null);
	}

	@Test
	public final void testGetTarget() {
		Target expected = target;
		Target result = te.getTarget();
		assertEquals(expected, result);
	}

	@Test
	public final void testGetDescription() {
		fail("Not yet implemented");
	}

	@Test
	public final void applyByArea()
	{
		when(target.getType()).thenReturn(TargetType.AREA);
		
		when(game.getAllAvailableTargetForCurrentCharacter(target.getConstraints())).thenReturn(characters);
		
		te.apply(game, spell);
		
		verify(c0, times(1)).getHealth();
		verify(c1, times(1)).getHealth();
		verify(game, times(2)).getBoard();
		verify(spell, times(2)).getName();
	}

	@Test
	public final void applyByChoice() {
		fail("Not yet implemented");
	}

	@Test
	public final void applyByMore() {
		fail("Not yet implemented");
	}

	@Test
	public final void applyByRandom() {
		fail("Not yet implemented");
	}

	@Test
	public final void applyByYou() {
		fail("Not yet implemented");
	}

}
