package bsh;

public class SimpleNodeJavaCode extends SimpleNode
{
	public SimpleNodeJavaCode(int i)
	{
		super(i);
		// TODO Auto-generated constructor stub
	}

	public String getSourceFile()
	{
		return "<Called from Java Code>";
	}

	public int getLineNumber()
	{
		return -1;
	}

	public String getText()
	{
		return "<Compiled Java Code>";
	}
}
