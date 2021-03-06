package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Raise extends stmtType
{
	public exprType type;
	public exprType inst;
	public exprType tback;

	public Raise(exprType type, exprType inst, exprType tback)
	{
		this.type = type;
		this.inst = inst;
		this.tback = tback;
	}

	public Raise(exprType type, exprType inst, exprType tback, SimpleNode parent)
	{
		this(type, inst, tback);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Raise[");
		sb.append("type=");
		sb.append(dumpThis(this.type));
		sb.append(", ");
		sb.append("inst=");
		sb.append(dumpThis(this.inst));
		sb.append(", ");
		sb.append("tback=");
		sb.append(dumpThis(this.tback));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(16, ostream);
		pickleThis(this.type, ostream);
		pickleThis(this.inst, ostream);
		pickleThis(this.tback, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitRaise(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (type != null)
		{
			type.accept(visitor);
		}
		if (inst != null)
		{
			inst.accept(visitor);
		}
		if (tback != null)
		{
			tback.accept(visitor);
		}
	}
}
