package test_game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Character;
import boardelement.Corpse;
import boardelement.IBoardElement;
import boardelement.Monster;
import boardelement.MonsterFactory;
import boardelement.Wizard;
import boardelement.WizardFactory;
import constant.GameConstant;
import game.Game;
import game.Horde;
import game.Level;
import spell.Card;
import target.TargetConstraint;
import zone.ZoneGroup;

public class TestGame
{
	private Game g;
	
	private GameConstant gameConstant;
	
	private Wizard w;
	private Wizard w0;
	private Monster m;
	private Monster m0;
	private Corpse c0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		Game.setGameConstant(gameConstant = mock(GameConstant.class));
		when(gameConstant.getBoardLenght()).thenReturn(6);
		when(gameConstant.getLevelCost()).thenReturn(50);
		when(gameConstant.getLevelMaxDifficulty()).thenReturn(5);
		when(gameConstant.getNbMonstersMax()).thenReturn(3);
		when(gameConstant.getNbMonstersMin()).thenReturn(2);
		when(gameConstant.getNbMonstersToSpawnEachTurnMax()).thenReturn(1);
		when(gameConstant.getNbMonstersToSpawnEachTurnMin()).thenReturn(1);
		when(gameConstant.getNbWizard()).thenReturn(2);
		
		w = mock(Wizard.class);
		when(w.getRange()).thenReturn(2);
		w0 = mock(Wizard.class);
		when(w0.getRange()).thenReturn(2);
		m = mock(Monster.class);
		m0 = mock(Monster.class);
		c0 = mock(Corpse.class);
		
		g = new Game(new Wizard[] { w, w0 });
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	//finish and win condition
	@Test
	public final void testIsFinished()
	{
		//If no wizards remaining
		boolean expected = false;
		boolean result = g.isFinished();
		assertEquals(expected, result);
		
		g.clearBoard(w); g.clearBoard(w0);
		
		expected = true;
		result = g.isFinished();
		assertEquals(expected, result);
		
		

		//If all level completed
		g = new Game(new Wizard[] { w, w0 });
		
		g.nextEmptyLevel(); //LevelDifficulty = 1 < 5 --> not finished
		
		expected = false;
		result = g.isFinished();
		assertEquals(expected, result);
		
		g.nextEmptyLevel(); //LevelDifficulty = 2
		g.nextEmptyLevel(); //LevelDifficulty = 3
		g.nextEmptyLevel(); //...
		g.nextEmptyLevel();
		g.nextEmptyLevel(); //LevelDifficulty = 6 > 5 --> it must be finished !
		
		expected = true;
		result = g.isFinished();
		assertEquals(expected, result);
	}
	
	@Test
	public final void testIsVictory()
	{
		//If no wizards remaining
		g.clearBoard(w); g.clearBoard(w0);

		boolean expected = false;
		boolean result = g.isVictory();
		assertEquals(expected, result);
		
		
		
		//If wizards are remaining
		g = new Game(new Wizard[] { w, w0 });
		
		g.nextEmptyLevel();
		g.nextEmptyLevel();
		g.nextEmptyLevel();
		g.nextEmptyLevel();
		g.nextEmptyLevel();
		g.nextEmptyLevel(); //LevelDifficulty = 6

		expected = true;
		result = g.isVictory();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testIsVictoryException()
	{
		//LevelDifficulty = 1, you can't test if it's not finished
		g.isVictory();
	}

	
	
	//Current character
	@Test
	public final void testGetCurrentCharacter()
	{
		Character expected = null;
		Character result = g.getCurrentCharacter();
		assertEquals(expected, result);
	}
	
	@Test
	public final void testSetCurrentCharacterCharacter()
	{
		when(w0.getRange()).thenReturn(2);
		g.setCurrentCharacter(w0);
		
		Character expected = w0;
		Character result = g.getCurrentCharacter();
		assertEquals(expected, result);
		
		boolean[] expectedB = new boolean[] { true, true, true, true, false, false };
		boolean[] resultB = g.getCurrentCharacterRange();
		assertArrayEquals(expectedB, resultB);
		
		

		g.setCurrentCharacter(null);
		
		expectedB = new boolean[] { false, false, false, false, false, false };
		resultB = g.getCurrentCharacterRange();
		assertArrayEquals(expectedB, resultB);
	}

	@Test
	public final void testSetCurrentCharacterInt()
	{
		when(w0.getRange()).thenReturn(2);
		g.setCurrentCharacter(1);
		
		Character expected = w0;
		Character result = g.getCurrentCharacter();
		assertEquals(expected, result);
		
		boolean[] expectedB = new boolean[] { true, true, true, true, false, false };
		boolean[] resultB = g.getCurrentCharacterRange();
		assertArrayEquals(expectedB, resultB);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetCurrentCharacterIntException1()
	{
		//Character sélectionné est null
		g.setCurrentCharacter(2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetCurrentCharacterIntException2()
	{
		//Hors du tableau
		g.setCurrentCharacter(-1);
	}
	
	@Test
	public final void testSetFirstWizardAsCurrentCharacter()
	{
		//With wizards
		g.setFirstWizardAsCurrentCharacter();
		
		Character expected = w;
		Character result = g.getCurrentCharacter();
		assertEquals(expected, result);
		
		

		//With no wizards
		g.clearBoard(w); g.clearBoard(w0);

		g.setFirstWizardAsCurrentCharacter();
		
		expected = null;
		result = g.getCurrentCharacter();
	}
	
	
	
	//Targets for current character
	@Test
	public final void testGetRandomAvailableTargetForCurrentCharacter()
	{
		int delta = 300;
		
		g.setBoard(new IBoardElement[] {w, w0, null, m, m0, c0});
		
		when(w0.getRange()).thenReturn(2);
		g.setCurrentCharacter(w0);

		
		
		
		//no constraints
		TargetConstraint[] constraints = new TargetConstraint[0];
		
		int expectedW = 3333; int resultW = 0;
		int expectedW0 = 3333; int resultW0 = 0;
		int expectedM = 3333; int resultM = 0;
		int expectedM0 = 0; int resultM0 = 0;
		
		for(int i = 0; i < 10000; i++)
		{
			Character c = g.getRandomAvailableTargetForCurrentCharacter(constraints);
			
			if(c == w) { resultW++; }
			if(c == w0) { resultW0++; }
			if(c == m) { resultM++; }
			if(c == m0) { resultM0++; }
		}
		
		assertEquals(expectedW, resultW, delta);
		assertEquals(expectedW0, resultW0, delta);
		assertEquals(expectedM, resultM, delta);
		assertEquals(expectedM0, resultM0, delta);
		
		
		
		
		//not you
		constraints = new TargetConstraint[] { TargetConstraint.NOTYOU };
		
		expectedW = 5000; resultW = 0;
		expectedW0 = 0; resultW0 = 0;
		expectedM = 5000; resultM = 0;
		expectedM0 = 0; resultM0 = 0;
		
		for(int i = 0; i < 10000; i++)
		{
			Character c = g.getRandomAvailableTargetForCurrentCharacter(constraints);
			
			if(c == w) { resultW++; }
			if(c == w0) { resultW0++; }
			if(c == m) { resultM++; }
			if(c == m0) { resultM0++; }
		}
		
		assertEquals(expectedW, resultW, delta);
		assertEquals(expectedW0, resultW0, delta);
		assertEquals(expectedM, resultM, delta);
		assertEquals(expectedM0, resultM0, delta);
		
		
		
		
		//not ally
		constraints = new TargetConstraint[] { TargetConstraint.NOTALLY };
		
		expectedW = 0; resultW = 0;
		expectedW0 = 0; resultW0 = 0;
		expectedM = 10000; resultM = 0;
		expectedM0 = 0; resultM0 = 0;
		
		for(int i = 0; i < 10000; i++)
		{
			Character c = g.getRandomAvailableTargetForCurrentCharacter(constraints);
			
			if(c == w) { resultW++; }
			if(c == w0) { resultW0++; }
			if(c == m) { resultM++; }
			if(c == m0) { resultM0++; }
		}
		
		assertEquals(expectedW, resultW, delta);
		assertEquals(expectedW0, resultW0, delta);
		assertEquals(expectedM, resultM, delta);
		assertEquals(expectedM0, resultM0, delta);
		
		
		
		
		//not enemy
		constraints = new TargetConstraint[] { TargetConstraint.NOTENEMY };
		
		expectedW = 5000; resultW = 0;
		expectedW0 = 5000; resultW0 = 0;
		expectedM = 0; resultM = 0;
		expectedM0 = 0; resultM0 = 0;
		
		for(int i = 0; i < 10000; i++)
		{
			Character c = g.getRandomAvailableTargetForCurrentCharacter(constraints);
			
			if(c == w) { resultW++; }
			if(c == w0) { resultW0++; }
			if(c == m) { resultM++; }
			if(c == m0) { resultM0++; }
		}
		
		assertEquals(expectedW, resultW, delta);
		assertEquals(expectedW0, resultW0, delta);
		assertEquals(expectedM, resultM, delta);
		assertEquals(expectedM0, resultM0, delta);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testGetRandomAvailableTargetForCurrentCharacterException()
	{
		g.setBoard(new IBoardElement[] {w, w0, null, m, m0, c0});
		
		when(w0.getRange()).thenReturn(0);
		g.setCurrentCharacter(w0);
		
		g.getRandomAvailableTargetForCurrentCharacter(new TargetConstraint[] { TargetConstraint.NOTYOU });
	}
	
	@Test
	public final void testGetAllAvailableTargetForCurrentCharacter()
	{
		g.setBoard(new IBoardElement[] {w, w0, null, m, m0, c0});
		
		when(w0.getRange()).thenReturn(2);
		when(w0.getMove()).thenReturn(1);
		g.setCurrentCharacter(w0);
		

		
		//no constraints
		Character[] expected = new Character[] {w, w0, m};
		Character[] result = g.getAllAvailableTargetForCurrentCharacter(new TargetConstraint[0]);
		assertArrayEquals(expected, result);
		
		//not you
		expected = new Character[] {w, m};
		result = g.getAllAvailableTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTYOU});
		assertArrayEquals(expected, result);
		
		//not ally
		expected = new Character[] {m};
		result = g.getAllAvailableTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTALLY});
		assertArrayEquals(expected, result);
		
		//not enemy
		expected = new Character[] {w, w0};
		result = g.getAllAvailableTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTENEMY});
		assertArrayEquals(expected, result);
		
		//not you and not enemy
		expected = new Character[] {w};
		result = g.getAllAvailableTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTYOU, TargetConstraint.NOTENEMY});
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testIsValidTargetForCurrentCharacter()
	{
		g.setBoard(new IBoardElement[] {w, w0, null, m, m0, c0});
		
		when(w0.getRange()).thenReturn(2);
		g.setCurrentCharacter(w0);
		

		
		//no constraints
		boolean expected = true;
		boolean result = g.isValidTargetForCurrentCharacter(w, new TargetConstraint[0]);
		assertEquals(expected, result);
		
		expected = false;
		result = g.isValidTargetForCurrentCharacter(m0, new TargetConstraint[0]);
		assertEquals(expected, result);
		
		//not you
		expected = true;
		result = g.isValidTargetForCurrentCharacter(m, new TargetConstraint[] {TargetConstraint.NOTYOU});
		assertEquals(expected, result);
		
		expected = false;
		result = g.isValidTargetForCurrentCharacter(w0, new TargetConstraint[] {TargetConstraint.NOTYOU});
		assertEquals(expected, result);
		
		//not ally
		expected = true;
		result = g.isValidTargetForCurrentCharacter(m, new TargetConstraint[] {TargetConstraint.NOTALLY});
		assertEquals(expected, result);
		
		expected = false;
		result = g.isValidTargetForCurrentCharacter(w, new TargetConstraint[] {TargetConstraint.NOTALLY});
		assertEquals(expected, result);
		
		//not enemy
		expected = true;
		result = g.isValidTargetForCurrentCharacter(w0, new TargetConstraint[] {TargetConstraint.NOTENEMY});
		assertEquals(expected, result);
		
		expected = false;
		result = g.isValidTargetForCurrentCharacter(m, new TargetConstraint[] {TargetConstraint.NOTENEMY});
		assertEquals(expected, result);
		
		//not you and not enemy
		expected = true;
		result = g.isValidTargetForCurrentCharacter(w, new TargetConstraint[] {TargetConstraint.NOTYOU, TargetConstraint.NOTENEMY});
		assertEquals(expected, result);
		
		expected = false;
		result = g.isValidTargetForCurrentCharacter(w0, new TargetConstraint[] {TargetConstraint.NOTYOU, TargetConstraint.NOTENEMY});
		assertEquals(expected, result);
	}
	
	@Test
	public final void testHasValidTargetForCurrentCharacter()
	{
		g.setBoard(new IBoardElement[] {w, w0, null, null, m0, c0});
		
		when(w0.getRange()).thenReturn(2);
		g.setCurrentCharacter(w0);
		

		
		boolean expected = true;
		boolean result = g.hasValidTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTENEMY});
		assertEquals(expected, result);
		
		expected = false;
		result = g.hasValidTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTALLY});
		assertEquals(expected, result);
	}

	//Targets for the AI of monsters
	@Test
	public final void testGetAllPossibleTargetForCurrentCharacter()
	{
		g.setBoard(new IBoardElement[] {w, w0, null, m, m0, c0});
		
		when(w0.getRange()).thenReturn(1);
		when(w0.getMove()).thenReturn(1);
		g.setCurrentCharacter(w0);
		

		
		//no constraints
		Character[] expected = new Character[] {w, w0, m};
		Character[] result = g.getAllPossibleTargetForCurrentCharacter(new TargetConstraint[0]);
		assertArrayEquals(expected, result);
		
		//not you
		expected = new Character[] {w, m};
		result = g.getAllPossibleTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTYOU});
		assertArrayEquals(expected, result);
		
		//not ally
		expected = new Character[] {m};
		result = g.getAllPossibleTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTALLY});
		assertArrayEquals(expected, result);
		
		//not enemy
		expected = new Character[] {w, w0};
		result = g.getAllPossibleTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTENEMY});
		assertArrayEquals(expected, result);
		
		//not you and not enemy
		expected = new Character[] {w};
		result = g.getAllPossibleTargetForCurrentCharacter(new TargetConstraint[] {TargetConstraint.NOTYOU, TargetConstraint.NOTENEMY});
		assertArrayEquals(expected, result);
	}
	
	
	
	//The range array of the current character
	@Test
	public final void testGetCurrentCharacterRange()
	{
		boolean[] expected = new boolean[] {false, false, false, false, false, false};
		boolean[] result = g.getCurrentCharacterRange();
		assertArrayEquals(expected, result);
	}
	
	
	
	//The range array of all wizards
	@Test
	public final void testGetWizardsRange()
	{
		boolean[] expected = new boolean[] {true, true, true, true, false, false};
		boolean[] result = g.getWizardsRange();
		assertArrayEquals(expected, result);
	}
	
	
	
	//The board
	@Test
	public final void testGetBoard()
	{
		IBoardElement[] expected = new IBoardElement[] {w, w0, null, null, null, null};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
	}
	
	@Test
	public final void testSetBoard()
	{
		when(w.getRange()).thenReturn(0);
		when(w0.getRange()).thenReturn(2);
		
		g.setBoard(new IBoardElement[] {c0, m0, null, null, w, w0});
		
		IBoardElement[] expectedE = new IBoardElement[] {c0, m0, null, null, w, w0};
		IBoardElement[] resultE = g.getBoard();
		assertArrayEquals(expectedE, resultE);
		
		Character expectedC = w;
		Character resultC = g.getCurrentCharacter();
		assertEquals(expectedC, resultC);
		
		boolean[] expectedB = new boolean[] {false, false, false, true, true, true};
		boolean[] resultB = g.getWizardsRange();
		assertArrayEquals(expectedB, resultB);
		
		expectedB = new boolean[] {false, false, false, false, true, false};
		resultB = g.getCurrentCharacterRange();
		assertArrayEquals(expectedB, resultB);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetBoardException1()
	{
		g.setBoard(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSetBoardException2()
	{
		g.setBoard(new IBoardElement[] {w, w0, null, null, null, null, null});
	}
	
	@Test
	public final void testNbBoardElements()
	{
		int expected = 2;
		int result = g.nbBoardElements();
		assertEquals(expected, result);

		g.setBoard(new IBoardElement[] {c0, m0, null, null, w, w0});
		
		expected = 4;
		result = g.nbBoardElements();
		assertEquals(expected, result);
	}
	
	
	
	//The movements
	@Test
	public final void testElementaryMove()
	{
		
		//Situation basique (vers la droite)
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		int expectedInt = 2;
		int resultInt = g.elementaryMove(w, 2);
		assertEquals(expectedInt, resultInt);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, null, w, null, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//Situation basique (vers la gauche)
		g.setBoard(new IBoardElement[]
				{
						null, null, w, null, null, null
				});
		expectedInt = -1;
		resultInt = g.elementaryMove(w, -1);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, w, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//limite du board (vers la doite)
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, w
				});
		expectedInt = 0;
		resultInt = g.elementaryMove(w, 1);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, null, null, null, null, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//limite du board (vers la gauche)
		g.setBoard(new IBoardElement[]
				{
						null, w, null, null, null, null
				});
		expectedInt = -1;
		resultInt = g.elementaryMove(w, -3);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						w, null, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//"Saute" par dessus les autres characters
		g.setBoard(new IBoardElement[]
				{
						w, c0, w0, null, m0, null
				});
		expectedInt = 5;
		resultInt = g.elementaryMove(w, 5);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, c0, w0, null, m0, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//décalage des characters (vers la droite)
		g.setBoard(new IBoardElement[]
				{
						w, null, w0, c0, m0, null
				});
		expectedInt = 4;
		resultInt = g.elementaryMove(w, 4);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, w0, c0, m0, w, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//décalage des characters (vers la gauche)
		g.setBoard(new IBoardElement[]
				{
						null, w0, m0, c0, w, null
				});
		expectedInt = -3;
		resultInt = g.elementaryMove(w, -3);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, w, w0, m0, c0, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//décalage des characters + limite du board
		g.setBoard(new IBoardElement[]
				{
						w0, null, null, w, null, null
				});
		expectedInt = -3;
		resultInt = g.elementaryMove(w, -20);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						w, w0, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//Wizard qui se déplace sur un Corpse
		g.setBoard(new IBoardElement[]
				{
						w, null, c0, null, null, null
				});
		expectedInt = 2;
		resultInt = g.elementaryMove(w, 2);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, null, w, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//Monster qui se déplace sur un Corpse
		g.setBoard(new IBoardElement[]
				{
						m, null, c0, null, null, null
				});
		expectedInt = 2;
		resultInt = g.elementaryMove(m, 2);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, c0, m, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testElementaryMoveException1()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		g.elementaryMove(null, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testElementaryMoveException2()
	{
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, null
				});
		g.elementaryMove(w, 2);
	}
	
	@Test
	public final void testRightWalk()
	{
		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		g.rightWalk(w);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, w, null, null, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseMove(1);

		
		
		//bloqué par un mur
		reset(w);
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, w
				});
		g.rightWalk(w);
		expected = new IBoardElement[]
				{
						null, null, null, null, null, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseMove(0);
	}
	
	@Test
	public final void testLeftWalk()
	{

		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						null, w, null, null, null, null
				});
		g.leftWalk(w);
		IBoardElement[] expected = new IBoardElement[]
				{
						w, null, null, null, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseMove(1);

		
		
		//bloqué par un mur
		reset(w);
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		g.leftWalk(w);
		expected = new IBoardElement[]
				{
						w, null, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseMove(0);
	}
	
	@Test
	public final void testRightDash()
	{
		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		when(w.getDash()).thenReturn(3);
		g.rightDash(w);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, null, null, w, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseDash(3);
		verify(w, times(1)).setHasDashed(true);
		
		
		
		//bloqué par un mur
		reset(w);
		g.setBoard(new IBoardElement[]
				{
						null, null, null, w, null, null
				});
		when(w.getDash()).thenReturn(3);
		g.rightDash(w);
		expected = new IBoardElement[]
				{
						null, null, null, null, null, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseDash(2);
		verify(w, times(1)).setHasDashed(true);
		
		
		
		//pas de dash
		reset(w);
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, w
				});
		when(w.getDash()).thenReturn(3);
		g.rightDash(w);
		expected = new IBoardElement[]
				{
						null, null, null, null, null, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseDash(0);
		verify(w, never()).setHasDashed(true);
	}
	
	@Test
	public final void testLeftDash()
	{
		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, w
				});
		when(w.getDash()).thenReturn(3);
		g.leftDash(w);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, null, w, null, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseDash(3);
		verify(w, times(1)).setHasDashed(true);
		
		
		
		//bloqué par un mur
		reset(w);
		g.setBoard(new IBoardElement[]
				{
						null, null, w, null, null, null
				});
		when(w.getDash()).thenReturn(3);
		g.leftDash(w);
		expected = new IBoardElement[]
				{
						w, null, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w, times(1)).loseDash(2);
		verify(w, times(1)).setHasDashed(true);
		
		
		
		//pas de dash
		reset(w);
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		when(w.getDash()).thenReturn(3);
		g.leftDash(w);
		expected = new IBoardElement[]
				{
						w, null, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);

		verify(w, times(1)).loseDash(0);
		verify(w, never()).setHasDashed(true);
	}
	
	@Test
	public final void testPush()
	{
		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						null, w, w0, null, null, null
				});
		g.push(w, new Character[] {w0}, 2);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, w, null, null, w0, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		Wizard w1 = mock(Wizard.class);
		//Situation à plusieurs
		g.setBoard(new IBoardElement[]
				{
						null, w1, m0, w, w0, null
				});
		g.push(w, new Character[] {w0, w1, m0}, 1);
		expected = new IBoardElement[]
				{
						w1, m0, null, w, null, w0
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		

		//Situation à plusieurs avec murs
		g.setBoard(new IBoardElement[]
				{
						null, null, w, w0, w1, m0
				});
		g.push(w, new Character[] {w0, w1, m0}, 8);
		expected = new IBoardElement[]
				{
						null, null, w, m0, w1, w0 
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		

		//Situation à plusieurs avec innocents
		g.setBoard(new IBoardElement[]
				{
						w, null, w0, w1, m0, null
				});
		g.push(w, new Character[] {w0, m0}, 2);
		expected = new IBoardElement[]
				{
						w, null, null, w1, w0, m0
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testPushException1() 
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.push(null, new Character[] {w0}, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testPushException2()
	{
		//w not found
		g.setBoard(new IBoardElement[]
				{
						null, null, null, w0, null, null
				});
		g.push(w, new Character[] {w0}, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testPushException3()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.push(w, null, 2);
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testPushException4()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.push(w, new Character[] {w}, 2);
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testPushException5()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.push(null, new Character[] {w0}, 0);
	}
	
	@Test
	public final void testPull()
	{

		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						null, w, null, null, w0, null
				});
		g.pull(w, new Character[] {w0}, 2);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, w, w0, null, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		Wizard w1 = mock(Wizard.class);
		//Situation à plusieurs
		g.setBoard(new IBoardElement[]
				{
						w1, m0, null, w, null, w0
				});
		g.pull(w, new Character[] {w0, w1, m0}, 1);
		expected = new IBoardElement[]
				{
						null, w1, m0, w, w0, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		

		//Situation à plusieurs avec bodyblock
		g.setBoard(new IBoardElement[]
				{
						m0, null, w, null, w1, w0
				});
		g.pull(w, new Character[] {w0, w1, m0}, 8);
		expected = new IBoardElement[]
				{
						null, m0, w, w0, w1, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		

		//Situation à plusieurs avec innocents
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w1, w0, m0
				});
		g.pull(w, new Character[] {w0, m0}, 2);
		expected = new IBoardElement[]
				{
						w, null, w0, m0, w1, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testPullException1()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.pull(null, new Character[] {w0}, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testPullException2()
	{
		//w not found
		g.setBoard(new IBoardElement[]
				{
						null, null, null, w0, null, null
				});
		g.pull(w, new Character[] {w0}, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testPullException3()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.pull(w, null, 2);
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testPullException4()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.pull(w, new Character[] {w}, 2);
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testPullException5()
	{
		g.setBoard(new IBoardElement[]
				{
						w, null, null, w0, null, null
				});
		g.pull(null, new Character[] {w0}, 0);
	}

	
	
	//The turns
	@Test
	public final void testIsWizardsTurn()
	{
		boolean expected = false;
		boolean result = g.isWizardsTurn();
		assertEquals(expected, result);
	}
	
	@Test
	public final void testBeginWizardsTurn()
	{
		fail();
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testBeginWizardsTurnException()
	{
		when(w.getZoneGroup()).thenReturn(mock(ZoneGroup.class));
		when(w0.getZoneGroup()).thenReturn(mock(ZoneGroup.class));
		
		g.beginWizardsTurn();
		
		//you can't begin wizards turn if it's already the wizards turn
		g.beginWizardsTurn();
	}
	
	
	//Cast zone
	
	//Wizard's spawn
	
	
	
	//Monster's spawn
	//The turns
	
	//Cast zone
	
	//Wizard's spawn
	
	
	
	//Monster's spawn
	@Test
	public final void testSpawnMonster()
	{
		//Situation basique (pas de décalage)
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, null
				});
		g.spawnMonster(m);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, null, null, null, null, m
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		

		//Situation avec décalage de 1
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, w0
				});
		g.spawnMonster(m);
		expected = new IBoardElement[]
				{
						null, null, null, null, w0, m
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		

		//Situation avec plusieurs décalages
		g.setBoard(new IBoardElement[]
				{
						null, m0, null, null, c0, w0
				});
		g.spawnMonster(m);
		expected = new IBoardElement[]
				{
						null, m0, null, c0, w0, m
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testSpawnMonsterException1()
	{
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, null
				});
		g.spawnMonster(null);
	}
	
	@Test (expected = IllegalStateException.class)
	public final void testSpawnMonsterException2()
	{
		Wizard w1 = mock(Wizard.class);
		Monster m1 = mock(Monster.class);
		Monster m2 = mock(Monster.class);
		
		g.setBoard(new IBoardElement[]
				{
						w0, w1, m0, m1, c0, m2
				});
		g.spawnMonster(m);
	}
	
	
	
	//Level Difficulty
	
	//IGameListener Methods
	
}
