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
						"Deck",
						"Void",
						"Banish",
				};
		
		String[] result = new String[]
				{
						ZoneType.DECK.toString(),
						ZoneType.VOID.toString(),
						ZoneType.BANISH.toString(),
				};
		assertArrayEquals(expected, result);
	}

}
