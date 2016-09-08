package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Import extends stmtType
{
	public aliasType[] names;

	public Import(aliasType[] names)
	{
		this.names = names;
	}

	public Import(aliasType[] names, SimpleNode parent)
	{
		this(names);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Import[");
		sb.append("names=");
		sb.append(dumpThis(this.names));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(20, ostream);
		pickleThis(this.names, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitImport(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (names != null)
		{
			for (int i = 0; i < names.length; i++)
			{
				if (names[i] != null)
				{
					names[i].accept(visitor);
				}
			}
		}
	}
}
