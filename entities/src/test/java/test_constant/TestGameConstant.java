package test_constant;

import static org.junit.Assert.*;

import org.junit.Test;

import constant.GameConstant;

public class TestGameConstant {

	private GameConstant gConstant;
	
	@Test
	public void testGameConstant() {
		gConstant = new GameConstant(30, 4, 5, 8, 0, 2, 4, 12);
		
		int expected = 30;
		int result = gConstant.getBoardLenght();
		assertEquals(expected, result);
		
		expected = 4;
		result = gConstant.getNbWizard();
		assertEquals(expected, result);
		
		expected = 5;
		result = gConstant.getLevelCost();
		assertEquals(expected, result);
		
		expected = 8;
		result = gConstant.getLevelMaxDifficulty();
		assertEquals(expected, result);
		
		expected = 0;
		result = gConstant.getNbMonstersToSpawnEachTurnMin();
		assertEquals(expected, result);
		
		expected = 2;
		result = gConstant.getNbMonstersToSpawnEachTurnMax();
		assertEquals(expected, result);
		
		expected = 4;
		result = gConstant.getNbMonstersMin();
		assertEquals(expected, result);

		expected = 12;
		result = gConstant.getNbMonstersMax();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException1() {
		//boardLenght
		new GameConstant(0, 4, 5, 8, 0, 2, 4, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException2() {
		//nbWizard
		new GameConstant(30, -4, 5, 8, 0, 2, 4, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException3() {
		//nbWizard
		new GameConstant(30, 30, 5, 8, 0, 2, 4, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException4() {
		//levelCost
		new GameConstant(30, 4, -5, 8, 0, 2, 4, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException5() {
		//levelMaxDifficulty
		new GameConstant(30, 4, 5, -8, 0, 2, 4, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException6() {
		//nbMonstersToSpawnEachTurnMin
		new GameConstant(30, 4, 5, 8, -1, 2, 4, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException7() {
		//nbMonstersToSpawnEachTurnMax
		new GameConstant(30, 4, 5, 8, 2, 1, 4, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException8() {
		//nbMonstersMin
		new GameConstant(30, 4, 5, 8, 0, 2, 0, 12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException9() {
		//nbMonstersMax
		new GameConstant(30, 4, 5, 8, 0, 2, 4, 3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException10() {
		//nbMonstersMax
		new GameConstant(30, 4, 5, 8, 0, 2, 4, 30);
	}

}
