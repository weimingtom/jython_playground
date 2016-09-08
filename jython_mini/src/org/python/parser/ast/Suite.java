package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Suite extends modType
{
	public stmtType[] body;

	public Suite(stmtType[] body)
	{
		this.body = body;
	}

	public Suite(stmtType[] body, SimpleNode parent)
	{
		this(body);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Suite[");
		sb.append("body=");
		sb.append(dumpThis(this.body));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(4, ostream);
		pickleThis(this.body, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitSuite(this);
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
