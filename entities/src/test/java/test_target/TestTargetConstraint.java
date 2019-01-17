package test_target;

import static org.junit.Assert.*;

import org.junit.Test;

import target.TargetConstraint;

public class TestTargetConstraint {

	@Test
	public final void testToString()
	{
		String[] expected = new String[]
				{
						"not you",
						"not ally",
						"not enemy",
				};
		
		String[] result = new String[]
				{
						TargetConstraint.NOTYOU.toString(),
						TargetConstraint.NOTALLY.toString(),
						TargetConstraint.NOTENEMY.toString(),
				};
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

}
