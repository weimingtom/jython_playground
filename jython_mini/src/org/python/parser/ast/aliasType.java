package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class aliasType extends SimpleNode
{
	public String name;
	public String asname;

	public aliasType(String name, String asname)
	{
		this.name = name;
		this.asname = asname;
	}

	public aliasType(String name, String asname, SimpleNode parent)
	{
		this(name, asname);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("alias[");
		sb.append("name=");
		sb.append(dumpThis(this.name));
		sb.append(", ");
		sb.append("asname=");
		sb.append(dumpThis(this.asname));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(52, ostream);
		pickleThis(this.name, ostream);
		pickleThis(this.asname, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		traverse(visitor);
		return null;
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}
