package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class ExtSlice extends sliceType
{
	public sliceType[] dims;

	public ExtSlice(sliceType[] dims)
	{
		this.dims = dims;
	}

	public ExtSlice(sliceType[] dims, SimpleNode parent)
	{
		this(dims);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("ExtSlice[");
		sb.append("dims=");
		sb.append(dumpThis(this.dims));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(46, ostream);
		pickleThis(this.dims, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitExtSlice(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (dims != null)
		{
			for (int i = 0; i < dims.length; i++)
			{
				if (dims[i] != null)
				{
					dims[i].accept(visitor);
				}
			}
		}
	}
}