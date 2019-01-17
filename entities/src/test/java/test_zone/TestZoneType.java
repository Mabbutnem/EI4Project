package test_zone;

import static org.junit.Assert.*;

import org.junit.Test;

import zone.ZoneType;

public class TestZoneType
{

	@Test
	public final void testToString()
	{
		String[] expected = new String[]
				{
						"deck",
						"void",
						"banish",
				};
		
		String[] result = new String[]
				{
						ZoneType.DECK.toString(),
						ZoneType.VOID.toString(),
						ZoneType.BANISH.toString(),
				};
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

}
