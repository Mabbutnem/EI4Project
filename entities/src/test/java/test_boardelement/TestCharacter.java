package test_boardelement;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import characterlistener.IAliveListener;
import characterlistener.IArmorListener;
import characterlistener.IDashListener;
import characterlistener.IFreezeListener;
import characterlistener.IHealthListener;
import characterlistener.IMoveListener;
import characterlistener.IRangeListener;
import effect.Word;

public class TestCharacter
{
	private class RealCharacter extends boardelement.Character
	{
		public RealCharacter()
		{
			super();
		}

		@Override
		public void resetMove() {}

		@Override
		public void resetRange() {}

		@Override
		public void resetHealth() {}

		@Override
		public void resetArmor() {}
	}
	
	private RealCharacter character;

	private IAliveListener aliveListener;
	private IHealthListener healthListener;
	private IArmorListener armorListener;
	private IMoveListener moveListener;
	private IDashListener dashListener;
	private IRangeListener rangeListener;
	private IFreezeListener freezeListener;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
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
		aliveListener = mock(IAliveListener.class);
		character.addAliveListener(aliveListener);
		
		boolean expected = false;
		character.setAlive(false);
		boolean result = character.isAlive();
		assertEquals(expected, result);
		
		verify(aliveListener, times(1)).onChange(character, false);
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
	public final void testHealthListener(){
		healthListener = mock(IHealthListener.class);
		character.addHealthListener(healthListener);
		
		character.setHealth(50);
		verify(healthListener, times(1)).onChange(character, 75, 50);
		verify(healthListener, times(1)).onLoss(character, 75, 50);
		
		reset(healthListener);
		character.gainHealth(10);
		verify(healthListener, times(1)).onChange(character, 50, 60);
		verify(healthListener, times(1)).onGain(character, 50, 60);

		reset(healthListener);
		character.loseHealth(10);
		verify(healthListener, times(1)).onChange(character, 60, 50);
		verify(healthListener, times(1)).onLoss(character, 60, 50);
		
		reset(healthListener);
		character.removeHealthListener(healthListener);
		System.out.println(healthListener);
		character.setHealth(50);
		character.gainHealth(10);
		character.loseHealth(10);
		verify(healthListener, never()).onChange(any(), anyInt(), anyInt());
		verify(healthListener, never()).onGain(any(), anyInt(), anyInt());
		verify(healthListener, never()).onLoss(any(), anyInt(), anyInt());
	}
	
	@Test
	public final void testInflictDirectDamage() {
		int expected = 10;
		int result = character.inflictDirectDamage(10);
		assertEquals(expected, result);
		
		expected = 65;
		result = character.getHealth();
		assertEquals(expected, result);
		
		expected = 65;
		result = character.inflictDirectDamage(85);
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
	public final void testArmorListener(){
		armorListener = mock(IArmorListener.class);
		character.addArmorListener(armorListener);
		
		character.setArmor(15);
		verify(armorListener, times(1)).onChange(character, 23, 15);
		verify(armorListener, times(1)).onLoss(character, 23, 15);
		
		reset(armorListener);
		character.gainArmor(10);
		verify(armorListener, times(1)).onChange(character, 15, 25);
		verify(armorListener, times(1)).onGain(character, 15, 25);

		reset(armorListener);
		character.loseArmor(10);
		verify(armorListener, times(1)).onChange(character, 25, 15);
		verify(armorListener, times(1)).onLoss(character, 25, 15);
		
		reset(armorListener);
		character.removeArmorListener(armorListener);
		character.setArmor(50);
		character.gainArmor(10);
		character.loseArmor(10);
		verify(armorListener, never()).onChange(any(), anyInt(), anyInt());
		verify(armorListener, never()).onGain(any(), anyInt(), anyInt());
		verify(armorListener, never()).onLoss(any(), anyInt(), anyInt());
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
	public final void testMoveListener(){
		moveListener = mock(IMoveListener.class);
		character.addMoveListener(moveListener);
		
		character.setMove(8);
		verify(moveListener, times(1)).onChange(character, 5, 8);
		verify(moveListener, times(1)).onGain(character, 5, 8);
		
		reset(moveListener);
		character.gainMove(2);
		verify(moveListener, times(1)).onChange(character, 8, 10);
		verify(moveListener, times(1)).onGain(character, 8, 10);

		reset(moveListener);
		character.loseMove(2);
		verify(moveListener, times(1)).onChange(character, 10, 8);
		verify(moveListener, times(1)).onLoss(character, 10, 8);
		
		reset(moveListener);
		character.removeMoveListener(moveListener);
		character.setMove(8);
		character.gainMove(2);
		character.loseMove(2);
		verify(moveListener, never()).onChange(any(), anyInt(), anyInt());
		verify(moveListener, never()).onGain(any(), anyInt(), anyInt());
		verify(moveListener, never()).onLoss(any(), anyInt(), anyInt());
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
	public final void testDashListener(){
		dashListener = mock(IDashListener.class);
		character.addDashListener(dashListener);
		
		character.setDash(4);
		verify(dashListener, times(1)).onChange(character, 0, 4);
		verify(dashListener, times(1)).onGain(character, 0, 4);
		
		reset(dashListener);
		character.gainDash(2);
		verify(dashListener, times(1)).onChange(character, 4, 6);
		verify(dashListener, times(1)).onGain(character, 4, 6);

		reset(dashListener);
		character.loseDash(2);
		verify(dashListener, times(1)).onChange(character, 6, 4);
		verify(dashListener, times(1)).onLoss(character, 6, 4);
		
		reset(dashListener);
		character.removeDashListener(dashListener);
		character.setDash(8);
		character.gainDash(2);
		character.loseDash(2);
		verify(dashListener, never()).onChange(any(), anyInt(), anyInt());
		verify(dashListener, never()).onGain(any(), anyInt(), anyInt());
		verify(dashListener, never()).onLoss(any(), anyInt(), anyInt());
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
	public final void testRangeListener(){
		rangeListener = mock(IRangeListener.class);
		character.addRangeListener(rangeListener);
		
		character.setRange(10);
		verify(rangeListener, times(1)).onChange(character, 7, 10);
		verify(rangeListener, times(1)).onGain(character, 7, 10);
		
		reset(rangeListener);
		character.gainRange(2);
		verify(rangeListener, times(1)).onChange(character, 10, 12);
		verify(rangeListener, times(1)).onGain(character, 10, 12);

		reset(rangeListener);
		character.loseRange(2);
		verify(rangeListener, times(1)).onChange(character, 12, 10);
		verify(rangeListener, times(1)).onLoss(character, 12, 10);
		
		reset(rangeListener);
		character.removeRangeListener(rangeListener);
		character.setRange(8);
		character.gainRange(2);
		character.loseRange(2);
		verify(rangeListener, never()).onChange(any(), anyInt(), anyInt());
		verify(rangeListener, never()).onGain(any(), anyInt(), anyInt());
		verify(rangeListener, never()).onLoss(any(), anyInt(), anyInt());
	}
	
	
	@Test
	public final void testIsFreeze() {
		boolean expected = false;
		boolean result = character.isFreeze();
		assertEquals(expected, result);
	}

	@Test
	public final void testSetFreeze() {
		freezeListener = mock(IFreezeListener.class);
		character.addFreezeListener(freezeListener);
		
		character.setFreeze(true);
		boolean expected = true;
		boolean result = character.isFreeze();
		assertEquals(expected, result);
		verify(freezeListener, times(1)).onChange(character, true);
		
		reset(freezeListener);
		character.setFreeze(false);
		expected = false;
		result = character.isFreeze();
		assertEquals(expected, result);
		verify(freezeListener, times(1)).onChange(character, false);
	}

	@Test
	public final void testResetFreeze() {
		character.resetFreeze();
		boolean expected = false;
		boolean result = character.isFreeze();
		assertEquals(expected, result);
	}
	
	@Test
	public final void testContainsWordAndAddWord() {
		boolean expected = true;
		boolean result;
		
		character.addWord(Word.LIFELINK);
		result = character.containsWord(Word.LIFELINK);
		assertEquals(expected, result);
		
		expected = false;
		result = character.containsWord(Word.ACID);
		assertEquals(expected, result);		
	}
	
	@Test
	public final void testClearWord() {
		character.addWord(Word.LIFELINK);
		character.clearWords();
		boolean expected = false;
		boolean result = character.containsWord(Word.LIFELINK);
		assertEquals(expected, result);
	}
	
	

}