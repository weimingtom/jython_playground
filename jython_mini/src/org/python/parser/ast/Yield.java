package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Yield extends stmtType
{
	public exprType value;

	public Yield(exprType value)
	{
		this.value = value;
	}

	public Yield(exprType value, SimpleNode parent)
	{
		this(value);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Yield[");
		sb.append("value=");
		sb.append(dumpThis(this.value));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(8, ostream);
		pickleThis(this.value, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitYield(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (value != null)
		{
			value.accept(visitor);
		}
	}
}
