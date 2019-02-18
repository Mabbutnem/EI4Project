package test_game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	private Corpse c;

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
		c = mock(Corpse.class);
		
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
						null, null, w, null, null, null,
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//Situation basique (vers la gauche)
		g.setBoard(new IBoardElement[]
				{
						null, null, w, null, null, null,
				});
		expectedInt = -1;
		resultInt = g.elementaryMove(w, -1);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, w, null, null, null, null,
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
						w, c, w0, null, m0, null
				});
		expectedInt = 5;
		resultInt = g.elementaryMove(w, 5);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, c, w0, null, m0, w
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//décalage des characters (vers la droite)
		g.setBoard(new IBoardElement[]
				{
						w, null, w0, c, m0, null
				});
		expectedInt = 4;
		resultInt = g.elementaryMove(w, 4);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, w0, c, m0, w, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
		
		
		
		
		//décalage des characters (vers la gauche)
		g.setBoard(new IBoardElement[]
				{
						null, w0, m0, c, w, null
				});
		expectedInt = -3;
		resultInt = g.elementaryMove(w, -3);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, w, w0, m0, c, null
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
						w, null, c, null, null, null
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
						m, null, c, null, null, null
				});
		expectedInt = 2;
		resultInt = g.elementaryMove(m, 2);
		assertEquals(expectedInt, resultInt);
		expected = new IBoardElement[]
				{
						null, c, m, null, null, null
				};
		result = g.getBoard();
		assertArrayEquals(expected, result);
	}

}
