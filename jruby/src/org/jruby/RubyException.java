/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2001 Alan Moore <alan_moore@gmx.net>
 * Copyright (C) 2001-2004 Jan Arne Petersen <jpetersen@uni-bonn.de>
 * Copyright (C) 2002 Benoit Cerrina <b.cerrina@wanadoo.fr>
 * Copyright (C) 2002-2004 Anders Bengtsson <ndrsbngtssn@yahoo.se>
 * Copyright (C) 2002-2004 Thomas E Enebo <enebo@acm.org>
 * Copyright (C) 2004 Joey Gibson <joey@joeygibson.com>
 * Copyright (C) 2004-2005 Charles O Nutter <headius@headius.com>
 * Copyright (C) 2004 Stefan Matthias Aust <sma@3plus4.de>
 * Copyright (C) 2005 David Corbin <dcorbin@users.sf.net>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby;

import java.io.PrintStream;

import org.jruby.runtime.CallbackFactory;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * 
 * @author jpetersen
 */
public class RubyException extends RubyObject
{

	private RubyArray backtrace;
	public IRubyObject message;
	public static final int TRACE_HEAD = 8;
	public static final int TRACE_TAIL = 4;
	public static final int TRACE_MAX = TRACE_HEAD + TRACE_TAIL + 6;

	protected RubyException(IRuby runtime, RubyClass rubyClass)
	{
		this(runtime, rubyClass, null);
	}

	public RubyException(IRuby runtime, RubyClass rubyClass, String message)
	{
		super(runtime, rubyClass);
		if (message == null)
		{
			this.message = runtime.getNil();
		} else
		{
			this.message = runtime.newString(message);
		}
	}

	public static RubyClass createExceptionClass(IRuby runtime)
	{
		RubyClass exceptionClass = runtime.defineClass("Exception", runtime.getObject());

		CallbackFactory callbackFactory = runtime.callbackFactory(RubyException.class);

		exceptionClass.defineSingletonMethod("new", callbackFactory.getOptSingletonMethod("newInstance"));
		exceptionClass.defineSingletonMethod("exception", callbackFactory.getOptSingletonMethod("newInstance"));
		exceptionClass.defineMethod("initialize", callbackFactory.getOptMethod("initialize"));
		exceptionClass.defineMethod("exception", callbackFactory.getOptMethod("exception"));
		exceptionClass.defineMethod("to_s", callbackFactory.getMethod("to_s"));
		exceptionClass.defineMethod("to_str", callbackFactory.getMethod("to_s"));
		exceptionClass.defineMethod("message", callbackFactory.getMethod("to_s"));
		exceptionClass.defineMethod("inspect", callbackFactory.getMethod("inspect"));
		exceptionClass.defineMethod("backtrace", callbackFactory.getMethod("backtrace"));
		exceptionClass.defineMethod("set_backtrace", callbackFactory.getMethod("set_backtrace", IRubyObject.class));

		return exceptionClass;
	}

	public static RubyException newException(IRuby runtime, RubyClass excptnClass, String msg)
	{
		return new RubyException(runtime, excptnClass, msg);
	}

	// Exception methods

	public static RubyException newInstance(IRubyObject recv, IRubyObject[] args)
	{
		RubyException newException = new RubyException(recv.getRuntime(), (RubyClass) recv);

		newException.callInit(args);

		return newException;
	}

	public IRubyObject initialize(IRubyObject[] args)
	{
		if (args.length > 0)
		{
			message = args[0];
		}
		return this;
	}

	public IRubyObject backtrace()
	{
		if (backtrace == null)
		{
			return getRuntime().getNil();
		}
		return backtrace;
	}

	public RubyArray set_backtrace(IRubyObject obj)
	{
		backtrace = obj.convertToArray();

		return backtrace;
	}

	public RubyException exception(IRubyObject[] args)
	{
		switch (args.length)
		{
			case 0:
				return this;
			case 1:
				if (args[0] == this)
				{
					return this;
				}
				return newInstance(getMetaClass(), args);
			default:
				throw getRuntime().newArgumentError("Wrong argument count");
		}

	}

	public IRubyObject to_s()
	{
		if (message.isNil())
		{
			return getRuntime().newString(getMetaClass().getName());
		}
		message.setTaint(isTaint());
		return (RubyString) message.callMethod("to_s");
	}

	/**
	 * inspects an object and return a kind of debug information
	 * 
	 *@return A RubyString containing the debug information.
	 */
	public IRubyObject inspect()
	{
		RubyModule rubyClass = getMetaClass();

		RubyString exception = RubyString.stringValue(this);

		if (exception.getValue().length() == 0)
		{
			return getRuntime().newString(rubyClass.getName());
		}
		StringBuffer sb = new StringBuffer();
		sb.append("#<");
		sb.append(rubyClass.getName());
		sb.append(": ");
		sb.append(exception.getValue());
		sb.append(">");
		return getRuntime().newString(sb.toString());
	}

	public void printBacktrace(PrintStream errorStream)
	{
		IRubyObject[] elements = ((RubyArray) backtrace()).toJavaArray();

		for (int i = 1; i < elements.length; i++)
		{
			IRubyObject stackTraceLine = elements[i];
			if (stackTraceLine instanceof RubyString)
			{
				printStackTraceLine(errorStream, stackTraceLine);
			}

			if (i == RubyException.TRACE_HEAD && elements.length > RubyException.TRACE_MAX)
			{
				int hiddenLevels = elements.length - RubyException.TRACE_HEAD - RubyException.TRACE_TAIL;
				errorStream.print("\t ... " + hiddenLevels + " levels...\n");
				i = elements.length - RubyException.TRACE_TAIL;
			}
		}
	}

	private void printStackTraceLine(PrintStream errorStream, IRubyObject stackTraceLine)
	{
		errorStream.print("\tfrom " + stackTraceLine + '\n');
	}
}
