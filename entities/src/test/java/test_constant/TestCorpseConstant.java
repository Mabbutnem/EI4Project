package test_constant;

import static org.junit.Assert.*;

import org.junit.Test;

import constant.CorpseConstant;

public class TestCorpseConstant {

	private CorpseConstant cConstant;
	
	@Test
	public void testCorpseConstant() {
		cConstant = new CorpseConstant(2);
		
		int expected = 2;
		int result = cConstant.getNbTurnToReborn();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testCorpseConstantException() {
		cConstant = new CorpseConstant(-2);
	}

}
