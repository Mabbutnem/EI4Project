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
		
		float[] expected = new float[] { 100f/470f, 120f/470f, 170f/470f, 270f/470f, 470f/470f};
		float[] result = Proba.convertFrequencyToProbability(frequencies);
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i], 0.000001f);
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testConvertFrequencyToProbabilityException()
	{
		int[] frequencies = new int[] { 0, 0, 0, 0, 0};
		Proba.convertFrequencyToProbability(frequencies);
	}

	@Test
	public final void testGetRandomIndexFromPredictable()
	{
		int expected = 2;
		int result = Proba.getRandomIndexFrom(new float[] {0f, 0f, 1f, 1f});
		assertEquals(expected, result);
	}
	
	@Test
	public final void testGetRandomIndexFromProportion()
	{
		int delta = 200;
		float[] probabilities = new float[] {0.25f, 0.43f, 1f, 1f};
		
		int[] expected = new int[] {2500, 1800, 5700, 0};
		int[] result = new int[] {0, 0, 0, 0};
		
		for(int i = 0; i < 10000; i++)
		{
			result[Proba.getRandomIndexFrom(probabilities)]++;
		}

		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i], delta);
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGetRandomIndexFromException1()
	{
		Proba.getRandomIndexFrom(new float[] {6f, 0.3f, 1f});
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGetRandomIndexFromException2()
	{
		Proba.getRandomIndexFrom(new float[] {0.6f, 0.3f, 1f});
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGetRandomIndexFromException3()
	{
		Proba.getRandomIndexFrom(new float[] {0f, 0.8f, 0.9f});
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGetRandomIndexFromException4()
	{
		Proba.getRandomIndexFrom(new float[0]);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGetRandomIndexFromException5()
	{
		Proba.getRandomIndexFrom(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testGetRandomIndexFromException6()
	{
		Proba.getRandomIndexFrom(new float[] {-0.1f, 0.1f, 1f});
	}
	
	@Test
	public final void testWillHappenPredictable()
	{
		boolean expected = false;
		boolean result = Proba.willHappen(0f);
		assertEquals(expected, result);
		
		expected = true;
		result = Proba.willHappen(1f);
		assertEquals(expected, result);
	}
	
	@Test 
	public final void testWillHappenProportion()
	{
		int delta = 200;
		
		float probability = 0.5f;
		int expected = 5000;
		int result = 0;
		for(int i = 0; i < 10000; i++)
		{
			if(Proba.willHappen(probability)) { result++; }
		}
		assertEquals(expected, result, delta);
		
		probability = 0.9f;
		expected = 9000;
		result = 0;
		for(int i = 0; i < 10000; i++)
		{
			if(Proba.willHappen(probability)) { result++; }
		}
		assertEquals(expected, result, delta);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testWillHappenException1()
	{
		Proba.willHappen(-0.01f);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testWillHappenException2()
	{
		Proba.willHappen(1.01f);
	}
	
	@Test
	public final void testNextInt1() {
		boolean expected = true;
		int number = Proba.nextInt(2, 6);
		boolean result = number >= 2 && number <= 6;
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testNextInt1Exception1() {
		Proba.nextInt(-2, 6);
	}

	@Test (expected = IllegalArgumentException.class)
	public final void testNextInt1Exception2() {
		Proba.nextInt(2, 1);
	}
	
	@Test
	public final void testNextInt2() {
		boolean expected = true;
		int number = Proba.nextInt(6);
		boolean result = number >= 0 && number <= 6;
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testNextInt2Exception2() {
		Proba.nextInt(-4);
	}
	
}
