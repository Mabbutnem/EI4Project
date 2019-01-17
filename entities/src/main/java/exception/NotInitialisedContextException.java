package exception;

public class NotInitialisedContextException extends RuntimeException
{

	private static final long serialVersionUID = -7676244711561794290L;

	private final String message;
	
	public NotInitialisedContextException(String message)
	{
		this.message = message;
	}

	@Override
	public String getMessage()
	{
		return message;
	}
	
}
