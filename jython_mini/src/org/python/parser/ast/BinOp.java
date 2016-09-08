package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class BinOp extends exprType implements operatorType
{
	public exprType left;
	public int op;
	public exprType right;

	public BinOp(exprType left, int op, exprType right)
	{
		this.left = left;
		this.op = op;
		this.right = right;
	}

	public BinOp(exprType left, int op, exprType right, SimpleNode parent)
	{
		this(left, op, right);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("BinOp[");
		sb.append("left=");
		sb.append(dumpThis(this.left));
		sb.append(", ");
		sb.append("op=");
		sb.append(dumpThis(this.op, operatorType.operatorTypeNames));
		sb.append(", ");
		sb.append("right=");
		sb.append(dumpThis(this.right));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(29, ostream);
		pickleThis(this.left, ostream);
		pickleThis(this.op, ostream);
		pickleThis(this.right, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		return visitor.visitBinOp(this);
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (left != null)
		{
			left.accept(visitor);
		}
		if (right != null)
		{
			right.accept(visitor);
		}
	}
}
