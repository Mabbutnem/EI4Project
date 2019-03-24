package test_zone;

import static org.junit.Assert.*;

import org.junit.Test;

import zone.ZonePick;

public class TestZonePick
{

	@Test
	public final void testToString()
	{
		String[] expected = new String[]
				{
						"Choice",
						"Default",
						"Bottom",
				};
		
		String[] result = new String[]
				{
						ZonePick.CHOICE.toString(),
						ZonePick.DEFAULT.toString(),
						ZonePick.BOTTOM.toString(),
				};
		assertArrayEquals(expected, result);
	}

}
