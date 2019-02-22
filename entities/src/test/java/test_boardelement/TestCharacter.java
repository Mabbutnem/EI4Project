package test_boardelement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Character;
import listener.IGameListener;

public class TestCharacter
{
	class RealCharacter extends boardelement.Character
	{
		public RealCharacter()
		{
			super();
		}

		@Override
		public void resetMove() {}

		@Override
		public void resetRange() {}
	}
	
	private class MockGameListener implements IGameListener{

		@Override
		public void clearBoard(Character character) {}

		@Override
		public void refreshRange(Character character) {
			
		}
	}
	
	private MockGameListener gameListener;
	
	private RealCharacter character;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		Character.setGameListener(gameListener = mock(MockGameListener.class));
		character = new RealCharacter();
		character.setHealth(75);
		character.setArmor(23);
		character.setMove(5);
		character.setRange(7);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public final void testIsAlive() {
		boolean expected = true;
		boolean result = character.isAlive();
		assertEquals(expected, result);
	}
	
	@Test
	public final void testSetAlive() {
		boolean expected = false;
		character.setAlive(false);
		boolean result = character.isAlive();
		assertEquals(expected, result);
		
		verify(gameListener, times(1)).clearBoard(character);
	}
	
	@Test
	public final void testclearBoard() {
		character.setAlive(false);
		
	}
	

	@Test
	public final void testGetHealth() {
		int expected = 75;
		int result = character.getHealth();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetHealth() {
		character.setHealth(-5);
		int expected = 0;
		int result = character.getHealth();
		assertEquals(expected, result);
		
		verify(gameListener, times(1)).clearBoard(character);
	}

	@Test
	public final void testLoseHealth() {
		character.loseHealth(5);
		int expected = 70;
		int result = character.getHealth();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testLoseHealthException() {
		character.loseHealth(-10);
	}

	@Test
	public final void testGainHealth() {
		character.gainHealth(10);
		int expected = 85;
		int result = character.getHealth();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGainHealthException() {
		character.gainHealth(-10);
	}
	
	@Test
	public final void testInflictDirectDamage() {
		int expected = 65;
		character.inflictDirectDamage(10);
		int result = character.getHealth();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testInflictDirectDamageException() {
		character.inflictDirectDamage(-10);
	}

	@Test
	public final void testInflictDamage() {
		int expected = 0;
		int result = character.inflictDamage(5);
		assertEquals(expected, result);
		expected = 75;
		result = character.getHealth();
		assertEquals(expected, result);
		expected = 18;
		result = character.getArmor();
		assertEquals(expected, result);
		
		
		
		character.setHealth(75);
		character.setArmor(23);
		
		expected = 30;
		result = character.inflictDamage(30);
		assertEquals(expected, result);
		expected = 45;
		result = character.getHealth();
		assertEquals(expected, result);
		expected = 23;
		result = character.getArmor();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testInflictDamageException() {
		character.inflictDamage(-10);
	}
	
	@Test
	public final void testInflictAcidDamage() {
		int expected = 0;
		int result = character.inflictAcidDamage(5);
		assertEquals(expected, result);
		expected = 75;
		result = character.getHealth();
		assertEquals(expected, result);
		expected = 18;
		result = character.getArmor();
		assertEquals(expected, result);
		
		character.setHealth(75);
		character.setArmor(23);
		
		expected = 5;
		result = character.inflictAcidDamage(28);
		assertEquals(expected, result);
		expected = 70;
		result = character.getHealth();
		assertEquals(expected, result);
		expected = 0;
		result = character.getArmor();
		assertEquals(expected, result);
		
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testInflictAcidDamageException() {
		character.inflictAcidDamage(-10);
	}
	
	
	@Test
	public final void testGetArmor() {
		int expected = 23;
		int result = character.getArmor();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetArmor() {
		character.setArmor(-25);
		int expected = 0;
		int result = character.getArmor();
		assertEquals(expected, result);
	}

	@Test
	public final void testLoseArmor() {
		character.loseArmor(14);
		int expected = 9;
		int result = character.getArmor();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testLoseArmorException() {
		character.loseArmor(-10);
	}

	@Test
	public final void testGainArmor() {
		character.gainArmor(10);
		int expected = 33;
		int result = character.getArmor();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGainArmorException() {
		character.gainArmor(-10);
	}

	
	
	
	@Test
	public final void testGetMove() {
		int expected = 5;
		int result = character.getMove();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetMove() {
		character.setMove(-10);
		int expected = 0;
		int result = character.getMove();
		assertEquals(expected, result);
	}

	@Test
	public final void testLoseMove() {
		character.loseMove(4);
		int expected = 1;
		int result = character.getMove();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testLoseMoveException() {
		character.loseMove(-10);
	}

	@Test
	public final void testGainMove() {
		character.gainMove(4);
		int expected = 9;
		int result = character.getMove();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGainMoveException() {
		character.gainMove(-10);
	}

	
	
	
	@Test
	public final void testGetDash() {
		int expected = 0;
		int result = character.getDash();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetDash() {
		character.setDash(-8);
		int expected = 0;
		int result = character.getDash();
		assertEquals(expected, result);
	}

	@Test
	public final void testLoseDash() {
		character.setDash(8);
		character.loseDash(5);
		int expected = 3;
		int result = character.getDash();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testLoseDashException() {
		character.loseDash(-10);
	}

	@Test
	public final void testGainDash() {
		character.gainDash(2);
		int expected = 2;
		int result = character.getDash();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGainDashException() {
		character.gainDash(-10);
	}

	
	
	
	@Test
	public final void testGetRange() {
		int expected = 7;
		int result = character.getRange();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetRange() {
		character.setRange(-3);
		int expected = 0;
		int result = character.getRange();
		assertEquals(expected, result);
		verify(gameListener, times(2)).refreshRange(character); //une fois dans le setUp et la 2ème ici
	}

	@Test
	public final void testLoseRange() {
		character.loseRange(4);
		int expected = 3;
		int result = character.getRange();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testLoseRangeException() {
		character.loseRange(-10);
	}

	@Test
	public final void testGainRange() {
		character.gainRange(10);
		int expected = 17;
		int result = character.getRange();
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGainRangeException() {
		character.gainRange(-10);
	}

	
	
	
	@Test
	public final void testIsFreeze() {
		boolean expected = false;
		boolean result = character.isFreeze();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetFreeze() {
		character.setFreeze(true);
		boolean expected = true;
		boolean result = character.isFreeze();
		assertEquals(expected, result);
		
		character.setFreeze(false);
		expected = false;
		result = character.isFreeze();
		assertEquals(expected, result);
	}

	@Test
	public final void testResetFreeze() {
		character.resetFreeze();
		boolean expected = false;
		boolean result = character.isFreeze();
		assertEquals(expected, result);
	}

}