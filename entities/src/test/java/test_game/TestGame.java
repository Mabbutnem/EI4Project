package test_game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Character;
import boardelement.Corpse;
import boardelement.IBoardElement;
import boardelement.Monster;
import boardelement.Wizard;
import constant.GameConstant;
import game.Game;

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
		when(gameConstant.getLevelMaxDifficulty()).thenReturn(10);
		when(gameConstant.getNbWizard()).thenReturn(3);
		
		w = mock(Wizard.class);
		w0 = mock(Wizard.class);
		m = mock(Monster.class);
		m0 = mock(Monster.class);
		c0 = mock(Corpse.class);
		
		g = new Game();
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	
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
		verify(w).loseMove(1);

		
		
		//bloqué par un mur
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
		verifyNoMoreInteractions(w);
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
		verify(w).loseMove(1);

		
		
		//bloqué par un mur
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
		verifyNoMoreInteractions(w);
	}
	
	@Test
	public final void testRightDash()
	{
		when(w.getDash()).thenReturn(3);
		
		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		g.rightDash(w);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, null, null, w, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w).loseDash(3);
		
		
		
		//bloqué par un mur
		g.setBoard(new IBoardElement[]
				{
						null, null, null, w, null, null
				});
		g.rightDash(w);
		expected = new IBoardElement[]
				{
						null, null, null, null, null, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w).loseDash(2);
		
		
		
		//pas de dash
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, w
				});
		g.rightDash(w);
		expected = new IBoardElement[]
				{
						null, null, null, null, null, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		verify(w, times(3)).getDash();
		verifyNoMoreInteractions(w);
	}
	
	@Test
	public final void testLeftDash()
	{
		when(w.getDash()).thenReturn(3);
		
		//Situation classique
		g.setBoard(new IBoardElement[]
				{
						null, null, null, null, null, w
				});
		g.leftDash(w);
		IBoardElement[] expected = new IBoardElement[]
				{
						null, null, w, null, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w).loseDash(3);
		
		
		
		//bloqué par un mur
		g.setBoard(new IBoardElement[]
				{
						null, null, w, null, null, null
				});
		g.leftDash(w);
		expected = new IBoardElement[]
				{
						w, null, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		verify(w).loseDash(2);
		
		
		
		//pas de dash
		g.setBoard(new IBoardElement[]
				{
						w, null, null, null, null, null
				});
		g.leftDash(w);
		expected = new IBoardElement[]
				{
						w, null, null, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		verify(w, times(3)).getDash();
		verifyNoMoreInteractions(w);
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
}
