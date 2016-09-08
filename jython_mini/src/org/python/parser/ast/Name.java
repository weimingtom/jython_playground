package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Name extends exprType implements expr_contextType
{
	public String id;
	public int ctx;

	public Name(String id, int ctx)
	{
		this.id = id;
		this.ctx = ctx;
	}

	public Name(String id, int ctx, SimpleNode parent)
	{
		this(id, ctx);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Name[");
		sb.append("id=");
		sb.append(dumpThis(this.id));
		sb.append(", ");
		sb.append("ctx=");
		sb.append(dumpThis(this.ctx, expr_contextType.expr_contextTypeNames));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(41, ostream);
		pickleThis(this.id, ostream);
		pickleThis(this.ctx, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitName(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{

	}
}