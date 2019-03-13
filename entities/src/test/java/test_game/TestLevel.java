package test_game;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.Level;

public class TestLevel {

	private Level l;
	private Map<String, Integer> hordes;
	
	@Before
	public void setUp() throws Exception
	{
		hordes = new HashMap<>();
		hordes.put("horde1", 2);
		hordes.put("horde2", 3);
		hordes.put("horde3", 1);
		
	}
	
	@Test
	public void testLevel() {
		l = new Level(5, hordes);
		
		int expected = 5;
		int result = l.getDifficulty();
		assertEquals(expected, result);
		
		Map<String, Integer> expectedM = hordes;
		Map<String, Integer> resultM = l.getMapHordesFrequencies();
		assertEquals(expectedM, resultM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testLevelDifficulty() {
		l = new Level(-5, hordes);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testLevelHordes() {
		hordes.put("horde4", -1);
		l = new Level(5, hordes);
	}

}
