package test_exception;

import static org.junit.Assert.*;

import org.junit.Test;

import exception.NotInitialisedContextException;

public class TestNotInitialisedContextException
{
	
	@Test
	public final void testGetMessage()
	{
		String expected = "Mon message d'erreur";
		
		try
		{
			throw new NotInitialisedContextException(expected);
		}
		catch(NotInitialisedContextException e)
		{
			String result = e.getMessage();
			assertEquals(expected, result);
		}
	}

}
