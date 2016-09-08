package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class For extends stmtType
{
	public exprType target;
	public exprType iter;
	public stmtType[] body;
	public stmtType[] orelse;

	public For(exprType target, exprType iter, stmtType[] body, stmtType[] orelse)
	{
		this.target = target;
		this.iter = iter;
		this.body = body;
		this.orelse = orelse;
	}

	public For(exprType target, exprType iter, stmtType[] body, stmtType[] orelse, SimpleNode parent)
	{
		this(target, iter, body, orelse);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("For[");
		sb.append("target=");
		sb.append(dumpThis(this.target));
		sb.append(", ");
		sb.append("iter=");
		sb.append(dumpThis(this.iter));
		sb.append(", ");
		sb.append("body=");
		sb.append(dumpThis(this.body));
		sb.append(", ");
		sb.append("orelse=");
		sb.append(dumpThis(this.orelse));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(13, ostream);
		pickleThis(this.target, ostream);
		pickleThis(this.iter, ostream);
		pickleThis(this.body, ostream);
		pickleThis(this.orelse, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitFor(this);
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
		if (body != null)
		{
			for (int i = 0; i < body.length; i++)
			{
				if (body[i] != null)
				{
					body[i].accept(visitor);
				}
			}
		}
		if (orelse != null)
		{
			for (int i = 0; i < orelse.length; i++)
			{
				if (orelse[i] != null)
				{
					orelse[i].accept(visitor);
				}
			}
		}
	}
}
