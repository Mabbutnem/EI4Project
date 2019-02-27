package test_constant;

import static org.junit.Assert.*;

import org.junit.Test;

import constant.GameConstant;

public class TestGameConstant {

	private GameConstant gConstant;
	
	@Test
	public void testGameConstant() {
		gConstant = new GameConstant(10, 4, 5, 8, 0, 2, 0.6f);
		
		int expected = 10;
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
		
		float expectedF = 0.6f;
		float resultF = gConstant.getBoardDensityLimit();
		assertEquals(expectedF, resultF, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException1() {
		gConstant = new GameConstant(3, 4, 5, 8, 0, 2, 0.6f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException2() {
		gConstant = new GameConstant(10, -4, 5, 8, 0, 2, 0.6f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException3() {
		gConstant = new GameConstant(10, 4, -5, 8, 0, 2, 0.6f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException4() {
		gConstant = new GameConstant(10, 4, 5, -8, 0, 2, 0.6f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException5() {
		gConstant = new GameConstant(10, 4, 5, 8, -1, 2, 0.6f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException6() {
		gConstant = new GameConstant(10, 4, 5, 8, 2, 1, 0.6f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException7() {
		gConstant = new GameConstant(10, 4, 5, 8, 0, 2, 1.5f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGameConstantException8() {
		//Test si boardDensityLimit ne peut pas être inférieure à la densité limite qui est (4+1)/10=0.4f
		gConstant = new GameConstant(10, 4, 5, 8, 0, 2, 0.2f);
	}

}
