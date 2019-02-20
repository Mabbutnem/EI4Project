package test_utility;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.INamedObject;
import utility.MapConverter;

public class TestMapConverter {

	private INamedObject A;
	private INamedObject B;
	private INamedObject C;
	private INamedObject D;
	private INamedObject E;
	private INamedObject copieDeA;
	private INamedObject copieDeB;
	private INamedObject copieDeC;
	private INamedObject copieDeD;
	private INamedObject copieDeE;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception
	{
		copieDeA = mock(INamedObject.class);
		when(copieDeA.getName()).thenReturn("a");
		copieDeB = mock(INamedObject.class);
		when(copieDeB.getName()).thenReturn("b");
		copieDeC = mock(INamedObject.class);
		when(copieDeC.getName()).thenReturn("c");
		copieDeD = mock(INamedObject.class);
		when(copieDeD.getName()).thenReturn("d");
		copieDeE = mock(INamedObject.class);
		when(copieDeE.getName()).thenReturn("e");
		
		A = mock(INamedObject.class);
		when(A.getName()).thenReturn("a");
		when(A.cloneObject()).thenReturn(copieDeA);
		B = mock(INamedObject.class);
		when(B.getName()).thenReturn("b");
		when(B.cloneObject()).thenReturn(copieDeB);
		C = mock(INamedObject.class);
		when(C.getName()).thenReturn("c");
		when(C.cloneObject()).thenReturn(copieDeC);
		D = mock(INamedObject.class);
		when(D.getName()).thenReturn("d");
		when(D.cloneObject()).thenReturn(copieDeD);
		E = mock(INamedObject.class);
		when(E.getName()).thenReturn("e");
		when(E.cloneObject()).thenReturn(copieDeE);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetObjectsFromMapNamesFrequencies() {
		int[] expected = new int[] { 1, 1, 1};
		int[] result = new int[] { 0, 0, 0};
		Map<String, Integer> mapNamesFrequencies = new HashMap<String, Integer>();
		mapNamesFrequencies.put("a", 100);
		mapNamesFrequencies.put("b", 50);
		mapNamesFrequencies.put("d", 100);
		
		INamedObject[] objects = new INamedObject[] {A, B, C, D, E};
		
		INamedObject[] copieObjects = MapConverter.getObjectsFromMapNamesFrequencies(mapNamesFrequencies, objects);
		
		for(int i=0; i<copieObjects.length; i++) {
			if(copieObjects[i]==copieDeA) {
				result[0] += 1;
			}
			if(copieObjects[i]==copieDeB) {
				result[1] += 1;
			}
			if(copieObjects[i]==copieDeD) {
				result[2] += 1;
			}
		}
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetObjectsFromMapNamesFrequenciesException() {
		Map<String, Integer> mapNamesFrequencies = new HashMap<String, Integer>();
		mapNamesFrequencies.put("f", 100);
		
		INamedObject[] objects = new INamedObject[] {A, B, C, D, E};
		
		MapConverter.getObjectsFromMapNamesFrequencies(mapNamesFrequencies, objects);
	}
	
	@Test
	public void testGetFrequenciesFromMapNamesFrequencies() {
		int[] expected = new int[] { 50, 100, 20};
		int[] result = new int[] { 0, 0, 0};
		Map<String, Integer> mapNamesFrequencies = new HashMap<String, Integer>();
		mapNamesFrequencies.put("a", 100);
		mapNamesFrequencies.put("b", 50);
		mapNamesFrequencies.put("d", 20);
		
		INamedObject[] copieObjects = new INamedObject[] {copieDeB, copieDeA, copieDeD};
		
		result = MapConverter.getFrequenciesFromMapNamesFrequencies(mapNamesFrequencies, copieObjects);
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetFrequenciesFromMapNamesFrequenciesException() {
		Map<String, Integer> mapNamesFrequencies = new HashMap<String, Integer>();
		mapNamesFrequencies.put("a", 100);
		mapNamesFrequencies.put("d", 100);
		
		INamedObject[] copieObjects = new INamedObject[] {copieDeB, copieDeA, copieDeD};
		
		MapConverter.getFrequenciesFromMapNamesFrequencies(mapNamesFrequencies, copieObjects);
	}
	
	@Test
	public void testGetObjectsFromMapNamesQuantities() {
		int[] expected = new int[] { 2, 1, 3};
		int[] result = new int[] { 0, 0, 0};
		Map<String, Integer> mapNamesQuantities = new HashMap<String, Integer>();
		mapNamesQuantities.put("a", 2);
		mapNamesQuantities.put("b", 1);
		mapNamesQuantities.put("d", 3);
		
		INamedObject[] objects = new INamedObject[] {A, B, C, D, E};
		
		INamedObject[] copieObjects = MapConverter.getObjectsFromMapNamesQuantities(mapNamesQuantities, objects);
		
		for(int i=0; i<copieObjects.length; i++) {
			if(copieObjects[i]==copieDeA) {
				result[0] += 1;
			}
			if(copieObjects[i]==copieDeB) {
				result[1] += 1;
			}
			if(copieObjects[i]==copieDeD) {
				result[2] += 1;
			}
		}
		assertArrayEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetObjectsFromMapNamesQuantitiesException() {
		Map<String, Integer> mapNamesQuantities = new HashMap<String, Integer>();
		mapNamesQuantities.put("f", 2);
		
		INamedObject[] objects = new INamedObject[] {A, B, C, D, E};
		
		MapConverter.getObjectsFromMapNamesQuantities(mapNamesQuantities, objects);
	}

}
