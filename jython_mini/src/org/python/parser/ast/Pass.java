package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Pass extends stmtType
{
	public Pass()
	{

	}

	public Pass(SimpleNode parent)
	{
		this();
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Pass[");
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(25, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitPass(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}
