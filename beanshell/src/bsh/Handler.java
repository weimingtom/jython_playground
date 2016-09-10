package bsh;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * This is the invocation handler for the dynamic proxy.
 * <p>
 * 
 * Notes: Inner class for the invocation handler seems to shield this
 * unavailable interface from JDK1.2 VM...
 * 
 * I don't understand this. JThis works just fine even if those classes
 * aren't there (doesn't it?) This class shouldn't be loaded if an XThis
 * isn't instantiated in NameSpace.java, should it?
 */
class Handler implements InvocationHandler, Serializable
{
	private XThis xthis;
	public Handler(XThis xthis)
	{
		this.xthis = xthis;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		try
		{
			return invokeImpl(proxy, method, args);
		} 
		catch (TargetError te)
		{
			// Unwrap target exception. If the interface declares that
			// it throws the ex it will be delivered. If not it will be
			// wrapped in an UndeclaredThrowable
			throw te.getTarget();
		} 
		catch (EvalError ee)
		{
			// Ease debugging...
			// XThis.this refers to the enclosing class instance
			if (Interpreter.DEBUG)
			{
				Interpreter.debug("EvalError in scripted interface: " + this.xthis.toString() + ": " + ee);
			}
			throw ee;
		}
	}

	public Object invokeImpl(Object proxy, Method method, Object[] args) throws EvalError
	{
		String methodName = method.getName();
		CallStack callstack = new CallStack(this.xthis.namespace);
		/*
		 * If equals() is not explicitly defined we must override the
		 * default implemented by the This object protocol for scripted
		 * object. To support XThis equals() must test for equality with the
		 * generated proxy object, not the scripted bsh This object;
		 * otherwise callers from outside in Java will not see a the proxy
		 * object as equal to itself.
		 */
		BshMethod equalsMethod = null;
		try
		{
			equalsMethod = this.xthis.namespace.getMethod("equals", new Class[] { Object.class });
		} 
		catch (UtilEvalError e)
		{
			/* leave null */
		}
		if (methodName.equals("equals") && equalsMethod == null)
		{
			Object obj = args[0];
			return proxy == obj ? Boolean.TRUE : Boolean.FALSE;
		}
		/*
		 * If toString() is not explicitly defined override the default to
		 * show the proxy interfaces.
		 */
		BshMethod toStringMethod = null;
		try
		{
			toStringMethod = this.xthis.namespace.getMethod("toString", new Class[] {});
		} 
		catch (UtilEvalError e)
		{
			/* leave null */
		}
		if (methodName.equals("toString") && toStringMethod == null)
		{
			Class[] ints = proxy.getClass().getInterfaces();
			// XThis.this refers to the enclosing class instance
			StringBuffer sb = new StringBuffer(this.xthis.toString() + "\nimplements:");
			for (int i = 0; i < ints.length; i++)
			{
				sb.append(" " + ints[i].getName() + ((ints.length > 1) ? "," : ""));
			}
			return sb.toString();
		}
		Class[] paramTypes = method.getParameterTypes();
		return Primitive.unwrap(this.xthis.invokeMethod(methodName, Primitive.wrap_2(args, paramTypes)));
	}
}
