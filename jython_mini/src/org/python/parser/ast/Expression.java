package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Expression extends modType
{
	public exprType body;

	public Expression(exprType body)
	{
		this.body = body;
	}

	public Expression(exprType body, SimpleNode parent)
	{
		this(body);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Expression[");
		sb.append("body=");
		sb.append(dumpThis(this.body));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(3, ostream);
		pickleThis(this.body, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitExpression(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (body != null)
		{
			body.accept(visitor);
		}
	}
}
