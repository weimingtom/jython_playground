package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Str extends exprType
{
	public String s;

	public Str(String s)
	{
		this.s = s;
	}

	public Str(String s, SimpleNode parent)
	{
		this(s);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Str[");
		sb.append("s=");
		sb.append(dumpThis(this.s));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(38, ostream);
		pickleThis(this.s, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitStr(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}
