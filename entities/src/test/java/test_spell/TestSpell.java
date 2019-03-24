package test_spell;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import effect.BurnEffect;
import effect.CardEffect;
import effect.ConditionalEffect;
import effect.IApplicableEffect;
import effect.IEffect;
import effect.Word;
import game.Game;
import spell.Spell;
import target.Target;
import target.TargetType;
import utility.INamedObject;
import boardelement.Character;

public class TestSpell
{
	private class RealSpell extends Spell
	{

		public RealSpell(String name, IEffect[] effects) { super(name, effects); }

		@Override
		public INamedObject cloneObject() { return null; }
		
	}
	
	private RealSpell spell;
	
	private IEffect[] effects;
	
	private IEffect notApplicableEffect;
	private BurnEffect effectToAddValue;
	private BurnEffect effectMore;
	private CardEffect uselessEffect1;
	private CardEffect uselessEffect2;
	private CardEffect uselessEffect3;
	private ConditionalEffect effectCond;

	private Target targetToAddValue;
	private Target targetMore;
	
	

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
						effectToAddValue = mock(BurnEffect.class),
						notApplicableEffect = mock(IEffect.class),
						uselessEffect1 = mock(CardEffect.class),
						effectCond = mock(ConditionalEffect.class)
				};
		
		when(effectCond.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect2 = mock(CardEffect.class),
						effectMore = mock(BurnEffect.class),
						uselessEffect3 = mock(CardEffect.class),
				});

		when(effectToAddValue.getDescription()).thenReturn("effect to add value");
		when(notApplicableEffect.getDescription()).thenReturn("non applicable effect");
		when(uselessEffect1.getDescription()).thenReturn("useless effect");
		when(effectCond.getDescription()).thenReturn("conditional effect");
		
		Target targetUseless = mock(Target.class);
		when(targetUseless.getType()).thenReturn(TargetType.CHOICE);
		when(uselessEffect1.getTarget()).thenReturn(targetUseless);
		when(uselessEffect2.getTarget()).thenReturn(targetUseless);
		when(uselessEffect3.getTarget()).thenReturn(targetUseless);
		
		targetToAddValue = mock(Target.class);
		when(targetToAddValue.getType()).thenReturn(TargetType.CHOICE);
		when(effectToAddValue.getTarget()).thenReturn(targetToAddValue);

		targetMore = mock(Target.class);
		when(targetMore.getType()).thenReturn(TargetType.MORE);
		when(effectMore.getTarget()).thenReturn(targetMore);
		
		spell = new RealSpell("spell", effects);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	@Test
	public final void testSpell()
	{
		Character expectedC = null;
		Character resultC = spell.getChoosenTarget();
		assertEquals(expectedC, resultC);
		
		String expectedS = "effect to add value.\nnon applicable effect.\nuseless effect.\nconditional effect.\n";
		String resultS = spell.getDescription();
		assertEquals(expectedS, resultS);
		
		IEffect[] expectedE = effects;
		IEffect[] resultE = spell.getEffects();
		assertArrayEquals(expectedE, resultE);
		
		expectedS = "spell";
		resultS = spell.getName();
		assertEquals(expectedS, resultS);
	}
	
	@Test
	public final void testSpellWithNoEffect()
	{
		spell = new RealSpell("spell", new IEffect[0]);
		
		IEffect[] expected = new IEffect[0];
		IEffect[] result = spell.getEffects();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSpellException1() {
		spell = new RealSpell("", new IEffect[0]);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSpellException2()
	{
		//(pas d'effectToAddValue au dessus de effectMore)
		effects = new IEffect[]
				{
						uselessEffect1,
						uselessEffect2,
						uselessEffect3,
						effectMore
				};
		
		spell = new RealSpell("spell", effects);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSpellException3()
	{
		//(effectToAddValue situé après effectMore)
		effects = new IEffect[]
				{
						uselessEffect1,
						uselessEffect2,
						effectMore,
						uselessEffect3,
						effectToAddValue
				};
		
		spell = new RealSpell("spell", effects);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSpellException4()
	{
		//(effectToAddValue placé avant effectMore mais à 1 niveau de profondeur supplémentaire)
		effects = new IEffect[]
				{
						effectCond,
						uselessEffect3,
						effectMore
				};
		
		when(effectCond.getEffects()).thenReturn(new IEffect[]
				{
						uselessEffect1,
						effectToAddValue,
						uselessEffect2
				});
		
		spell = new RealSpell("spell", effects);
	}

	@Test
	public final void testSetChoosenTarget()
	{
		Character c = mock(Character.class);
		
		spell.setChoosenTarget(c);
		
		Character expected = c;
		Character result = spell.getChoosenTarget();
		assertEquals(expected, result);
	}

	@Test
	public final void testAddWord()
	{
		boolean expected = false;
		boolean result = spell.containsWord(Word.ACID);
		assertEquals(expected, result);
		
		spell.addWord(Word.ACID);
		
		expected = true;
		result = spell.containsWord(Word.ACID);
		assertEquals(expected, result);
	}

	@Test
	public final void testCast()
	{
		Game game = mock(Game.class);
		Character c = mock(Character.class);
		
		spell.addWord(Word.PIERCE);

		spell.setChoosenTarget(c);
		
		
		
		spell.cast(game);
		
		for(IEffect e : effects) { verify(e, times(1)).prepare(game, spell); }
		
		IApplicableEffect[] aEffects = new IApplicableEffect[] { effectToAddValue, uselessEffect1, effectCond };
		for(IApplicableEffect e : aEffects) { verify(e, times(1)).apply(game, spell); }

		for(IEffect e : effects) { verify(e, times(1)).clean(); }
		
		
		boolean expectedB = false;
		boolean resultB = spell.containsWord(Word.PIERCE);
		assertEquals(expectedB, resultB);
		
		Character expectedC = null;
		Character resultC = spell.getChoosenTarget();
		assertEquals(expectedC, resultC);
	}

}
