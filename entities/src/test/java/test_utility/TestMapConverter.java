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
		copieDeB = mock(INamedObject.class);
		copieDeC = mock(INamedObject.class);
		copieDeD = mock(INamedObject.class);
		copieDeE = mock(INamedObject.class);
		A = mock(INamedObject.class);
		when(A.cloneObject()).thenReturn(copieDeA);
		B = mock(INamedObject.class);
		when(B.cloneObject()).thenReturn(copieDeB);
		C = mock(INamedObject.class);
		when(C.cloneObject()).thenReturn(copieDeC);
		D = mock(INamedObject.class);
		when(D.cloneObject()).thenReturn(copieDeD);
		E = mock(INamedObject.class);
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

}
