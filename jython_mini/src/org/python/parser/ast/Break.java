package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Break extends stmtType
{
	public Break()
	{

	}

	public Break(SimpleNode parent)
	{
		this();
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Break[");
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(26, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitBreak(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}
