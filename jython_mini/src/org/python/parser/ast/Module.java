package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Module extends modType
{
	public stmtType[] body;

	public Module(stmtType[] body)
	{
		this.body = body;
	}

	public Module(stmtType[] body, SimpleNode parent)
	{
		this(body);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Module[");
		sb.append("body=");
		sb.append(dumpThis(this.body));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(1, ostream);
		pickleThis(this.body, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitModule(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
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
