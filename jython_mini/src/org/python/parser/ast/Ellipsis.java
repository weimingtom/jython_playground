package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Ellipsis extends sliceType
{
	public Ellipsis()
	{

	}

	public Ellipsis(SimpleNode parent)
	{
		this();
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Ellipsis[");
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(44, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitEllipsis(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}
