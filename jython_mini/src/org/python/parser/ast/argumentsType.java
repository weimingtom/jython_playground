package org.python.parser.ast;

import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class argumentsType extends SimpleNode
{
	public exprType[] args;
	public String vararg;
	public String kwarg;
	public exprType[] defaults;

	public argumentsType(exprType[] args, String vararg, String kwarg, exprType[] defaults)
	{
		this.args = args;
		this.vararg = vararg;
		this.kwarg = kwarg;
		this.defaults = defaults;
	}

	public argumentsType(exprType[] args, String vararg, String kwarg, exprType[] defaults, SimpleNode parent)
	{
		this(args, vararg, kwarg, defaults);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("arguments[");
		sb.append("args=");
		sb.append(dumpThis(this.args));
		sb.append(", ");
		sb.append("vararg=");
		sb.append(dumpThis(this.vararg));
		sb.append(", ");
		sb.append("kwarg=");
		sb.append(dumpThis(this.kwarg));
		sb.append(", ");
		sb.append("defaults=");
		sb.append(dumpThis(this.defaults));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException
	{
		pickleThis(50, ostream);
		pickleThis(this.args, ostream);
		pickleThis(this.vararg, ostream);
		pickleThis(this.kwarg, ostream);
		pickleThis(this.defaults, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception
	{
		traverse(visitor);
		return null;
	}

	public void traverse(VisitorIF visitor) throws Exception
	{
		if (args != null)
		{
			for (int i = 0; i < args.length; i++)
			{
				if (args[i] != null)
				{
					args[i].accept(visitor);
				}
			}
		}
		if (defaults != null)
		{
			for (int i = 0; i < defaults.length; i++)
			{
				if (defaults[i] != null)
				{
					defaults[i].accept(visitor);
				}
			}
		}
	}
}
