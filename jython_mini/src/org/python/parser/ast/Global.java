package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Global extends stmtType
{
	public String[] names;

	public Global(String[] names)
	{
		this.names = names;
	}

	public Global(String[] names, SimpleNode parent)
	{
		this(names);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Global[");
		sb.append("names=");
		sb.append(dumpThis(this.names));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(23, ostream);
		pickleThis(this.names, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitGlobal(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}
