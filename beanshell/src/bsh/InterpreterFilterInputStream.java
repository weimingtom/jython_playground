package bsh;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InterpreterFilterInputStream extends FilterInputStream
{
	protected InterpreterFilterInputStream(InputStream in)
	{
		super(in);
	}

	@Override
	public int available() throws IOException
	{
		return 0;
	}
}
