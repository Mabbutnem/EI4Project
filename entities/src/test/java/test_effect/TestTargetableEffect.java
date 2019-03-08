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
		//Pour tester que applyOn à été appelé
		protected void applyOn(Character character, Game game, ISpell spell)
		{
			character.getHealth();
			game.getBoard();
			spell.getName();
		}

		@Override
		public String getDescription() { return null; }
		
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
		
		target = mock(Target.class);
		when(target.getConstraints()).thenReturn(constraints);
		
		game = mock(Game.class);
		c0 = mock(Character.class);
		when(c0.isAlive()).thenReturn(true);
		c1 = mock(Character.class);
		when(c1.isAlive()).thenReturn(true);
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
	public final void testGetConstraintsDescription() {
		when(target.getConstraints()).thenReturn(new TargetConstraint[0]);
		String expected = "";
		String result = te.getConstraintsDescription();
		assertEquals(expected, result);
		
		when(target.getConstraints()).thenReturn(new TargetConstraint[]
				{
						TargetConstraint.NOTYOU,
				});
		expected = " (not you)";
		result = te.getConstraintsDescription();
		assertEquals(expected, result);
		
		when(target.getConstraints()).thenReturn(new TargetConstraint[]
				{
						TargetConstraint.NOTALLY,
				});
		expected = " (not ally)";
		result = te.getConstraintsDescription();
		assertEquals(expected, result);
		
		when(target.getConstraints()).thenReturn(new TargetConstraint[]
				{
						TargetConstraint.NOTYOU, TargetConstraint.NOTALLY,
				});
		expected = " (not you, not ally)";
		result = te.getConstraintsDescription();
		assertEquals(expected, result);
	}

	@Test
	public final void testApplyByArea()
	{
		when(target.getType()).thenReturn(TargetType.AREA);
		
		when(game.getAllAvailableTargetForCurrentCharacter(target.getConstraints())).thenReturn(characters);
		
		te.apply(game, spell);
		
		verify(c0, times(1)).getHealth();
		verify(c1, times(1)).getHealth();
		verify(game, times(2)).getBoard();
		verify(spell, times(2)).getName();
		
		
		
		
		
		reset(c0);
		reset(c1);
		reset(game);
		reset(spell);
		
		when(game.getAllAvailableTargetForCurrentCharacter(target.getConstraints())).thenReturn(new Character[0]);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(c1, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
	}

	@Test
	public final void testApplyByChoiceWithSpellChoosenTargetIsNull()
	{
		when(target.getType()).thenReturn(TargetType.CHOICE);
		
		when(targetRequestListener.chooseTarget(game)).thenReturn(c0);
		
		when(game.hasValidTargetForCurrentCharacter(constraints)).thenReturn(true);
		
		when(spell.getChoosenTarget()).thenReturn(null).thenReturn(c0);
		
		te.apply(game, spell);
		
		verify(spell, times(1)).setChoosenTarget(c0);
		
		verify(c0, times(1)).getHealth();
		verify(game, times(1)).getBoard();
		verify(spell, times(1)).getName();
		
		
		

		
		reset(c0);
		reset(game);
		reset(spell);

		when(game.hasValidTargetForCurrentCharacter(constraints)).thenReturn(true);
		
		when(spell.getChoosenTarget()).thenReturn(null);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
	}
	
	@Test
	public final void testApplyByChoiceWithSpellChoosenTargetIsAlreadyInitialised()
	{
		when(target.getType()).thenReturn(TargetType.CHOICE);
		
		when(spell.getChoosenTarget()).thenReturn(c0);
		
		te.apply(game, spell);
		
		verify(c0, times(1)).getHealth();
		verify(game, times(1)).getBoard();
		verify(spell, times(1)).getName();
		
		verify(spell, never()).setChoosenTarget(c0);
	}

	@Test
	public final void testApplyByMore()
	{
		when(target.getType()).thenReturn(TargetType.MORE);

		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
	}

	@Test
	public final void testApplyByRandom() {
		when(target.getType()).thenReturn(TargetType.RANDOM);
		
		when(game.getRandomAvailableTargetForCurrentCharacter(constraints)).thenReturn(c0);
		
		when(game.hasValidTargetForCurrentCharacter(constraints)).thenReturn(true);
		
		te.apply(game, spell);
		
		verify(c0, times(1)).getHealth();
		verify(game, times(1)).getBoard();
		verify(spell, times(1)).getName();
		
		
		
		

		reset(c0);
		reset(game);
		reset(spell);
		
		when(game.hasValidTargetForCurrentCharacter(constraints)).thenReturn(false);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
	}

	@Test
	public final void testApplyByYou() {
		when(target.getType()).thenReturn(TargetType.YOU);
		
		when(game.getCurrentCharacter()).thenReturn(c0);
		
		te.apply(game, spell);
		
		verify(c0, times(1)).getHealth();
		verify(game, times(1)).getBoard();
		verify(spell, times(1)).getName();
		
		
		
		

		reset(c0);
		reset(game);
		reset(spell);

		when(game.getCurrentCharacter()).thenReturn(null);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
	}
	
	@Test
	public final void testApplyIfCharacterIsNotAlive()
	{
		when(c0.isAlive()).thenReturn(false);
		when(c1.isAlive()).thenReturn(false);
		
		

		//AREA
		when(target.getType()).thenReturn(TargetType.AREA);
		
		when(game.getAllAvailableTargetForCurrentCharacter(target.getConstraints())).thenReturn(characters);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
		
		
		
		//CHOICE
		when(target.getType()).thenReturn(TargetType.CHOICE);
		
		when(spell.getChoosenTarget()).thenReturn(c0);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
		
		
		
		//RANDOM
		when(target.getType()).thenReturn(TargetType.RANDOM);
		
		when(game.getRandomAvailableTargetForCurrentCharacter(constraints)).thenReturn(c0);
		
		when(game.hasValidTargetForCurrentCharacter(constraints)).thenReturn(true);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
		
		
		
		//YOU
		when(target.getType()).thenReturn(TargetType.YOU);
		
		when(game.getCurrentCharacter()).thenReturn(c0);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
		//YOU
		when(target.getType()).thenReturn(TargetType.YOU);
		
		when(game.getCurrentCharacter()).thenReturn(c0);
		
		te.apply(game, spell);
		
		verify(c0, never()).getHealth();
		verify(game, never()).getBoard();
		verify(spell, never()).getName();
		
		
		
	}

}
