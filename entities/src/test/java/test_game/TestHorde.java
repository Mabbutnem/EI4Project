package test_game;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.Horde;

public class TestHorde {

	private Horde h;
	private Map<String, Integer> monsters;
	
	@Before
	public void setUp() throws Exception
	{
		
		monsters = new HashMap<>();
		monsters.put("monstre1", 2);
		monsters.put("monstre2", 3);
		monsters.put("monstre3", 1);
		
	}
	
	@Test
	public void testHorde() {
		h = new Horde("horde", 5, monsters);
		
		String expected = "horde";
		String result = h.getName();
		assertEquals(expected, result);
		
		int expectedI = 5;
		int resultI = h.getCost();
		assertEquals(expectedI, resultI);
		
		Map<String, Integer> expectedM = monsters;
		Map<String, Integer> resultM = h.getMonsters();
		assertEquals(expectedM, resultM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testHordeName() {
		h = new Horde("", 5, monsters);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testHordeCost() {
		h = new Horde("horde", -5, monsters);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testHordeMonsters() {
		monsters.put("monstre3", -1);
		h = new Horde("horde", 5, monsters);
	}

}
