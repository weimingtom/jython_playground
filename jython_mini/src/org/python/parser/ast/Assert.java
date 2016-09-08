package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class Assert extends stmtType
{
	public exprType test;
	public exprType msg;

	public Assert(exprType test, exprType msg)
	{
		this.test = test;
		this.msg = msg;
	}

	public Assert(exprType test, exprType msg, SimpleNode parent)
	{
		this(test, msg);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Assert[");
		sb.append("test=");
		sb.append(dumpThis(this.test));
		sb.append(", ");
		sb.append("msg=");
		sb.append(dumpThis(this.msg));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(19, ostream);
		pickleThis(this.test, ostream);
		pickleThis(this.msg, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitAssert(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (test != null)
		{
			test.accept(visitor);
		}
		if (msg != null)
		{
			msg.accept(visitor);
		}
	}
}