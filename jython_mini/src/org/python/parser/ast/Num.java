package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Num extends exprType
{
	public Object n;

	public Num(Object n)
	{
		this.n = n;
	}

	public Num(Object n, SimpleNode parent)
	{
		this(n);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Num[");
		sb.append("n=");
		sb.append(dumpThis(this.n));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(37, ostream);
		pickleThis(this.n, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitNum(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}