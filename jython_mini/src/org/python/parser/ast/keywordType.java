package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class keywordType extends SimpleNode
{
	public String arg;
	public exprType value;

	public keywordType(String arg, exprType value)
	{
		this.arg = arg;
		this.value = value;
	}

	public keywordType(String arg, exprType value, SimpleNode parent)
	{
		this(arg, value);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("keyword[");
		sb.append("arg=");
		sb.append(dumpThis(this.arg));
		sb.append(", ");
		sb.append("value=");
		sb.append(dumpThis(this.value));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(51, ostream);
		pickleThis(this.arg, ostream);
		pickleThis(this.value, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		traverse(visitor);
		return null;
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (value != null)
		{
			value.accept(visitor);
		}
	}
}
