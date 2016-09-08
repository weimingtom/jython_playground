package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Return extends stmtType
{
	public exprType value;

	public Return(exprType value)
	{
		this.value = value;
	}

	public Return(exprType value, SimpleNode parent)
	{
		this(value);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Return[");
		sb.append("value=");
		sb.append(dumpThis(this.value));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(7, ostream);
		pickleThis(this.value, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitReturn(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (value != null)
		{
			value.accept(visitor);
		}
	}
}
