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
		
		when(gameConstant.getBoardLenght()).thenReturn(10);
		when(gameConstant.getLevelCost()).thenReturn(50);
		when(gameConstant.getLevelMaxDifficulty()).thenReturn(10);
		when(gameConstant.getNbWizard()).thenReturn(3);
		
		g = new Game();
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public final void testElementaryMoveCharacterInt()
	{
		Wizard w = mock(Wizard.class);
		Wizard w0 = mock(Wizard.class);
		Wizard w1 = mock(Wizard.class);
		Monster m = mock(Monster.class);
		Monster m0 = mock(Monster.class);
		Monster m1 = mock(Monster.class);
		Corpse c = mock(Corpse.class);
		
		g.setBoard(new IBoardElement[]
				{
						null, w, w0, w1, m0, m1, null, null, null, null
				});
		g.elementaryMove(w, 4);
		
		IBoardElement[] expected = new IBoardElement[]
				{
						null, w0, w1, m0, m1, w, null, null, null, null
				};
		IBoardElement[] result = g.getBoard();
		assertArrayEquals(expected, result);
	}

}
