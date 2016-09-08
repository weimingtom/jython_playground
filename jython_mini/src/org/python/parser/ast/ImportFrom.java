package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class ImportFrom extends stmtType
{
	public String module;
	public aliasType[] names;

	public ImportFrom(String module, aliasType[] names)
	{
		this.module = module;
		this.names = names;
	}

	public ImportFrom(String module, aliasType[] names, SimpleNode parent)
	{
		this(module, names);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("ImportFrom[");
		sb.append("module=");
		sb.append(dumpThis(this.module));
		sb.append(", ");
		sb.append("names=");
		sb.append(dumpThis(this.names));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(21, ostream);
		pickleThis(this.module, ostream);
		pickleThis(this.names, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitImportFrom(this);
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
