package test_boardelement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boardelement.Character;
import boardelement.IBoardElement;
import boardelement.Monster;
import boardelement.MonsterFactory;
import listener.IGameListener;
import spell.Incantation;
import utility.Proba;

public class TestMonster {

	private class MockGameListener implements IGameListener{

		@Override
		public void clearBoard(IBoardElement boardElement) {}

		@Override
		public void refreshRange(Character character) {			
		}

	}
	private Monster m;
	private MockGameListener gameListener;
	
	private MonsterFactory mFactory;
	
	private float[] proba;
	private Incantation[] incantations;
	private Incantation inc1;
	private Incantation inc2;
	private Incantation inc3;
	
	private Map<String, Integer> MapFreq;
	
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
		
		proba = new float[]
				{
						1/13f, 3/13f, 13/13f,
				};
		
		incantations = new Incantation[]
				{
						inc1 = mock(Incantation.class),
						inc2 = mock(Incantation.class),
						inc3 = mock(Incantation.class),
						mock(Incantation.class),
						mock(Incantation.class),
				};
		when(inc1.getName()).thenReturn("inc1");
		when(inc2.getName()).thenReturn("inc2");
		when(inc3.getName()).thenReturn("inc3");
		
		MapFreq = new HashMap<>();
		MapFreq.put("inc1", 1);
		MapFreq.put("inc2", 2);
		MapFreq.put("inc3", 10);
		
		mFactory = mock(MonsterFactory.class);
		when(mFactory.getName()).thenReturn("monstre");
		when(mFactory.getMaxHealth()).thenReturn(70);
		when(mFactory.getInitArmor()).thenReturn(30);
		when(mFactory.getBaseMove()).thenReturn(4);
		when(mFactory.getBaseRange()).thenReturn(6);
		when(mFactory.getMapIncantationsFrequencies()).thenReturn(MapFreq);
		when(mFactory.getRebornProbability()).thenReturn(0.2f);
		
		m = new Monster(mFactory, incantations);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testMonster() {
		m = new Monster(mFactory, incantations);
		
		int expected = 70;
		int result = m.getMaxHealth();
		assertEquals(expected, result);
		
		result = m.getHealth();
		assertEquals(expected, result);
		
		expected = 30;
		result = m.getInitArmor();
		assertEquals(expected, result);
		
		result = m.getArmor();
		assertEquals(expected, result);
		
		expected = 4;
		result = m.getBaseMove();
		assertEquals(expected, result);
		
		result = m.getMove();
		assertEquals(expected, result);
		
		expected = 6;
		result = m.getBaseRange();
		assertEquals(expected, result);
		
		result = m.getRange();
		assertEquals(expected, result);
		
		expected = 0;
		result = m.getDash();
		assertEquals(expected, result);
		
		String expectedS = "monstre";
		String resultS = m.getName();
		assertEquals(expectedS, resultS);
		
		float expectedF = 0.2f;
		float resultF = m.getRebornProbability();
		assertEquals(expectedF, resultF, 0.000001f);
		
		String[] expectedI = new String[] {"inc1", "inc2", "inc3"};
		Incantation[] resultI = m.getIncantations();
		for(Incantation i : resultI) {
			assertTrue(Arrays.asList(expectedI).contains(i.getName()));
		}
		
		int[] frequencies = new int[3];
		for(int i = 0; i<resultI.length;i++) { frequencies[i] = MapFreq.get(resultI[i].getName()); }
		float[] expectedFF = Proba.convertFrequencyToProbability(frequencies);
		float[] resultFF = m.getIncantationProbabilities();
		for(int i = 0; i < expectedFF.length; i++)
		{
			assertEquals(expectedFF[i], resultFF[i], 0.000001f);
		}
		
		boolean expectedB = false;
		boolean resultB = m.hasPlayed();
		assertEquals(expectedB, resultB);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void TestMonsterException1() {
		m = new Monster(null, incantations);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void TestMonsterException2() {
		m = new Monster(mFactory, null);
	}
	
	@Test
	public void TestSetPlayed() {
		boolean expected = true;
		boolean result;
		
		m.setPlayed(true);
		result = m.hasPlayed();
		assertEquals(expected, result);
	}
	
	@Test
	public void TestGetRandomIncantationPredictable()
	{
		MapFreq.clear();
		MapFreq.put("inc1", 0);
		MapFreq.put("inc2", 1);
		MapFreq.put("inc3", 0);
		m = new Monster(mFactory, incantations);

		String expected = "inc2";
		String result = m.getRandomIncantation().getName();
		assertEquals(expected, result);
	}
	
	@Test
	public void TestGetRandomIncantationProportion() {
		fail();
	}

}