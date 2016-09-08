package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Expr extends stmtType
{
	public exprType value;

	public Expr(exprType value)
	{
		this.value = value;
	}

	public Expr(exprType value, SimpleNode parent)
	{
		this(value);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Expr[");
		sb.append("value=");
		sb.append(dumpThis(this.value));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(24, ostream);
		pickleThis(this.value, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitExpr(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (value != null)
		{	
			value.accept(visitor);
		}
	}
}
