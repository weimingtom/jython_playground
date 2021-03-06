package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class listcompType extends SimpleNode
{
	public exprType target;
	public exprType iter;
	public exprType[] ifs;

	public listcompType(exprType target, exprType iter, exprType[] ifs)
	{
		this.target = target;
		this.iter = iter;
		this.ifs = ifs;
	}

	public listcompType(exprType target, exprType iter, exprType[] ifs, SimpleNode parent)
	{
		this(target, iter, ifs);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("listcomp[");
		sb.append("target=");
		sb.append(dumpThis(this.target));
		sb.append(", ");
		sb.append("iter=");
		sb.append(dumpThis(this.iter));
		sb.append(", ");
		sb.append("ifs=");
		sb.append(dumpThis(this.ifs));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(48, ostream);
		pickleThis(this.target, ostream);
		pickleThis(this.iter, ostream);
		pickleThis(this.ifs, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		traverse(visitor);
		return null;
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (target != null)
		{
			target.accept(visitor);
		}
		if (iter != null)
		{
			iter.accept(visitor);
		}
		if (ifs != null)
		{
			for (int i = 0; i < ifs.length; i++)
			{
				if (ifs[i] != null)
				{
					ifs[i].accept(visitor);
				}
			}
		}
	}
}
