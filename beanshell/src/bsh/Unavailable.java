package bsh;

/**
 * An attempt was made to use an unavailable capability supported by an
 * optional package. The normal operation is to test before attempting to
 * use these packages... so this is runtime exception.
 */
public class Unavailable extends UtilEvalError
{
	public Unavailable(String s)
	{
		super(s);
	}
}
