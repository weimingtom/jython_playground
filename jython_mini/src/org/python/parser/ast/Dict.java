package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Dict extends exprType
{
	public exprType[] keys;
	public exprType[] values;

	public Dict(exprType[] keys, exprType[] values)
	{
		this.keys = keys;
		this.values = values;
	}

	public Dict(exprType[] keys, exprType[] values, SimpleNode parent)
	{
		this(keys, values);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Dict[");
		sb.append("keys=");
		sb.append(dumpThis(this.keys));
		sb.append(", ");
		sb.append("values=");
		sb.append(dumpThis(this.values));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(32, ostream);
		pickleThis(this.keys, ostream);
		pickleThis(this.values, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitDict(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (keys != null)
		{
			for (int i = 0; i < keys.length; i++)
			{
				if (keys[i] != null)
				{
					keys[i].accept(visitor);
				}
			}
		}
		if (values != null)
		{
			for (int i = 0; i < values.length; i++)
			{
				if (values[i] != null)
				{
					values[i].accept(visitor);
				}
			}
		}
	}
}
