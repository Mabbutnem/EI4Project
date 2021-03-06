package test_boardelement;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import boardelement.MonsterFactory;

public class TestMonsterFactory {

	private MonsterFactory mFactory;
	private Map<String, Integer> incantations;
	
	@Before
	public void setUp() throws Exception
	{
		incantations = new HashMap<>();
		incantations.put("inc1", 50);
		incantations.put("inc2", 100);
		incantations.put("inc3", 150);
	}
	
	@Test
	public final void testMonsterFactory() {
		mFactory = new MonsterFactory("monstre", 70, 20, 3, 4, incantations, 0.5f);
		
		String expected = "monstre";
		String result = mFactory.getName();
		assertEquals(expected, result);
		
		int expectedI = 70;
		int resultI = mFactory.getMaxHealth();
		assertEquals(expectedI, resultI);
		
		expectedI = 20;
		resultI = mFactory.getInitArmor();
		assertEquals(expectedI, resultI);
		
		expectedI = 3;
		resultI = mFactory.getBaseMove();
		assertEquals(expectedI, resultI);
		
		expectedI = 4;
		resultI = mFactory.getBaseRange();
		assertEquals(expectedI, resultI);
		
		Map<String, Integer> expectedM = incantations;
		Map<String, Integer> resultM = mFactory.getMapIncantationsFrequencies();
		assertEquals(expectedM, resultM);
		
		float expectedF = 0.5f;
		float resultF = mFactory.getRebornProbability();
		assertEquals(expectedF, resultF, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testMonsterFactoryName() {
		mFactory = new MonsterFactory("", 70, 20, 3, 4, incantations, 0.5f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testMonsterFactoryMaxHealth() {
		mFactory = new MonsterFactory("monstre", -10, 20, 3, 4, incantations, 0.5f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testMonsterFactoryInitArmor() {
		mFactory = new MonsterFactory("monstre", 70, -5, 3, 4, incantations, 0.5f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testMonsterFactoryBaseMove() {
		mFactory = new MonsterFactory("monstre", 70, 20, -5, 4, incantations, 0.5f);
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testMonsterFactoryBaseRange() {
		mFactory = new MonsterFactory("monstre", 70, 20, 3, -5, incantations, 0.5f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testMonsterFactoryIncantations() {
		incantations.put("inc4", -10);
		mFactory = new MonsterFactory("", 70, 20, 3, 4, incantations, 0.5f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testMonsterFactoryRebornProbability() {
		mFactory = new MonsterFactory("monstre", 70, 20, 3, 4, incantations, 1.5f);
	}
	
	@Test
	public void testMonsterFactoryCopie() {
		mFactory = new MonsterFactory("monstre", 70, 20, 3, 4, incantations, 0.5f);
		MonsterFactory mFactory2 = new MonsterFactory(mFactory);
		assertTrue(mFactory != mFactory2);
		
		String expected = mFactory.getName();
		String result = mFactory2.getName();
		assertEquals(expected, result);
		
		int expectedI = mFactory.getMaxHealth();
		int resultI = mFactory2.getMaxHealth();
		assertEquals(expectedI, resultI);
		
		expectedI = mFactory.getInitArmor();
		resultI = mFactory2.getInitArmor();
		assertEquals(expectedI, resultI);
		
		expectedI = mFactory.getBaseMove();
		resultI = mFactory2.getBaseMove();
		assertEquals(expectedI, resultI);
		
		expectedI = mFactory.getBaseRange();
		resultI = mFactory2.getBaseRange();
		assertEquals(expectedI, resultI);
		
		Map<String, Integer> expectedM = mFactory.getMapIncantationsFrequencies();
		Map<String, Integer> resultM = mFactory2.getMapIncantationsFrequencies();
		assertTrue(expectedM != resultM);
		assertEquals(expectedM, resultM);
		
		float expectedF = mFactory.getRebornProbability();
		float resultF = mFactory2.getRebornProbability();
		assertEquals(expectedF, resultF, 0);
	}
	
	@Test
	public void testCloneObject() {
		mFactory = new MonsterFactory("monstre", 70, 20, 3, 4, incantations, 0.5f);
		MonsterFactory mFactory2 = (MonsterFactory) mFactory.cloneObject();
		assertTrue(mFactory != mFactory2);
		
		String expected = mFactory.getName();
		String result = mFactory2.getName();
		assertEquals(expected, result);
		
		int expectedI = mFactory.getMaxHealth();
		int resultI = mFactory2.getMaxHealth();
		assertEquals(expectedI, resultI);
		
		expectedI = mFactory.getInitArmor();
		resultI = mFactory2.getInitArmor();
		assertEquals(expectedI, resultI);
		
		expectedI = mFactory.getBaseMove();
		resultI = mFactory2.getBaseMove();
		assertEquals(expectedI, resultI);
		
		expectedI = mFactory.getBaseRange();
		resultI = mFactory2.getBaseRange();
		assertEquals(expectedI, resultI);
		
		Map<String, Integer> expectedM = mFactory.getMapIncantationsFrequencies();
		Map<String, Integer> resultM = mFactory2.getMapIncantationsFrequencies();
		assertTrue(expectedM != resultM);
		assertEquals(expectedM, resultM);
		
		float expectedF = mFactory.getRebornProbability();
		float resultF = mFactory2.getRebornProbability();
		assertEquals(expectedF, resultF, 0);
	}
}
