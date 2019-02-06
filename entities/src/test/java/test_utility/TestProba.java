package test_utility;

import static org.junit.Assert.*;

import org.junit.Test;

import utility.Proba;

public class TestProba
{

	@Test
	public final void testConvertFrequencyToProbability()
	{
		int[] frequencies = new int[] { 100, 20, 50, 100, 200};
		
		float[] expected = new float[] { 100/470, 20/470, 50/470, 100/470, 200/470};
		float[] result = Proba.convertFrequencyToProbability(frequencies);
		/*for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}*/
	}

	@Test
	public final void testGetRandomIndexFrom()
	{
		fail("Not yet implemented");
	}

}
