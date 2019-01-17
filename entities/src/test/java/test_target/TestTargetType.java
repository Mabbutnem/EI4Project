package test_target;

import static org.junit.Assert.*;

import org.junit.Test;

import target.TargetType;

public class TestTargetType {

	@Test
	public final void testToString()
	{
		String[] expected = new String[]
				{
						"random",
						"area",
						"you",
				};
		
		String[] result = new String[]
				{
						TargetType.RANDOM.toString(),
						TargetType.AREA.toString(),
						TargetType.YOU.toString(),
				};
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

}
