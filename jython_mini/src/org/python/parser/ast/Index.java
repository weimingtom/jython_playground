package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Index extends sliceType
{
	public exprType value;

	public Index(exprType value)
	{
		this.value = value;
	}

	public Index(exprType value, SimpleNode parent)
	{
		this(value);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Index[");
		sb.append("value=");
		sb.append(dumpThis(this.value));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(47, ostream);
		pickleThis(this.value, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitIndex(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (value != null)
		{
			value.accept(visitor);
		}
	}
}
