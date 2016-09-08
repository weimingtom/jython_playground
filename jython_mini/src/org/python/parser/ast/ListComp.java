package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class ListComp extends exprType
{
	public exprType elt;
	public listcompType[] generators;

	public ListComp(exprType elt, listcompType[] generators)
	{
		this.elt = elt;
		this.generators = generators;
	}

	public ListComp(exprType elt, listcompType[] generators, SimpleNode parent)
	{
		this(elt, generators);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("ListComp[");
		sb.append("elt=");
		sb.append(dumpThis(this.elt));
		sb.append(", ");
		sb.append("generators=");
		sb.append(dumpThis(this.generators));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(33, ostream);
		pickleThis(this.elt, ostream);
		pickleThis(this.generators, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitListComp(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (elt != null)
		{
			elt.accept(visitor);
		}
		if (generators != null)
		{
			for (int i = 0; i < generators.length; i++)
			{
				if (generators[i] != null)
				{
					generators[i].accept(visitor);
				}
			}
		}
	}
}
