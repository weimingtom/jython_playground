package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class excepthandlerType extends SimpleNode
{
	public exprType type;
	public exprType name;
	public stmtType[] body;

	public excepthandlerType(exprType type, exprType name, stmtType[] body)
	{
		this.type = type;
		this.name = name;
		this.body = body;
	}

	public excepthandlerType(exprType type, exprType name, stmtType[] body, SimpleNode parent)
	{
		this(type, name, body);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("excepthandler[");
		sb.append("type=");
		sb.append(dumpThis(this.type));
		sb.append(", ");
		sb.append("name=");
		sb.append(dumpThis(this.name));
		sb.append(", ");
		sb.append("body=");
		sb.append(dumpThis(this.body));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(49, ostream);
		pickleThis(this.type, ostream);
		pickleThis(this.name, ostream);
		pickleThis(this.body, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		traverse(visitor);
		return null;
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (type != null)
		{
			type.accept(visitor);
		}
		if (name != null)
		{
			name.accept(visitor);
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
