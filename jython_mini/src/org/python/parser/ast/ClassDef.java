package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class ClassDef extends stmtType
{
	public String name;
	public exprType[] bases;
	public stmtType[] body;

	public ClassDef(String name, exprType[] bases, stmtType[] body)
	{
		this.name = name;
		this.bases = bases;
		this.body = body;
	}

	public ClassDef(String name, exprType[] bases, stmtType[] body, SimpleNode parent)
	{
		this(name, bases, body);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("ClassDef[");
		sb.append("name=");
		sb.append(dumpThis(this.name));
		sb.append(", ");
		sb.append("bases=");
		sb.append(dumpThis(this.bases));
		sb.append(", ");
		sb.append("body=");
		sb.append(dumpThis(this.body));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(6, ostream);
		pickleThis(this.name, ostream);
		pickleThis(this.bases, ostream);
		pickleThis(this.body, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitClassDef(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (bases != null)
		{
			for (int i = 0; i < bases.length; i++)
			{
				if (bases[i] != null)
				{
					bases[i].accept(visitor);
				}
			}
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
	}
}
