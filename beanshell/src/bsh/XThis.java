/*****************************************************************************
 *                                                                           *
 *  This file is part of the BeanShell Java Scripting distribution.          *
 *  Documentation and updates may be found at http://www.beanshell.org/      *
 *                                                                           *
 *  Sun Public License Notice:                                               *
 *                                                                           *
 *  The contents of this file are subject to the Sun Public License Version  *
 *  1.0 (the "License"); you may not use this file except in compliance with *
 *  the License. A copy of the License is available at http://www.sun.com    * 
 *                                                                           *
 *  The Original Code is BeanShell. The Initial Developer of the Original    *
 *  Code is Pat Niemeyer. Portions created by Pat Niemeyer are Copyright     *
 *  (C) 2000.  All Rights Reserved.                                          *
 *                                                                           *
 *  GNU Public License Notice:                                               *
 *                                                                           *
 *  Alternatively, the contents of this file may be used under the terms of  *
 *  the GNU Lesser General Public License (the "LGPL"), in which case the    *
 *  provisions of LGPL are applicable instead of those above. If you wish to *
 *  allow use of your version of this file only under the  terms of the LGPL *
 *  and not to allow others to use your version of this file under the SPL,  *
 *  indicate your decision by deleting the provisions above and replace      *
 *  them with the notice and other provisions required by the LGPL.  If you  *
 *  do not delete the provisions above, a recipient may use your version of  *
 *  this file under either the SPL or the LGPL.                              *
 *                                                                           *
 *  Patrick Niemeyer (pat@pat.net)                                           *
 *  Author of Learning Java, O'Reilly & Associates                           *
 *  http://www.pat.net/~pat/                                                 *
 *                                                                           *
 *****************************************************************************/

package bsh;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Hashtable;

/**
 * XThis is a dynamically loaded extension which extends This.java and adds
 * support for the generalized interface proxy mechanism introduced in JDK1.3.
 * XThis allows bsh scripted objects to implement arbitrary interfaces (be
 * arbitrary event listener types).
 * 
 * Note: This module relies on new features of JDK1.3 and will not compile with
 * JDK1.2 or lower. For those environments simply do not compile this class.
 * 
 * Eventually XThis should become simply This, but for backward compatability we
 * will maintain This without requiring support for the proxy mechanism.
 * 
 * XThis stands for "eXtended This" (I had to call it something).
 * 
 * @see JThis See also JThis with explicit JFC support for compatability.
 * @see This
 */
public class XThis extends This
{
	/**
	 * A cache of proxy interface handlers. Currently just one per interface.
	 */
	Hashtable interfaces;

	InvocationHandler invocationHandler;

	public XThis(NameSpace namespace, Interpreter declaringInterp)
	{
		super(namespace, declaringInterp);
		this.invocationHandler = new Handler(this);
	}

	public String toString()
	{
		return "'this' reference (XThis) to Bsh object: " + namespace;
	}

	/**
	 * Get dynamic proxy for interface, caching those it creates.
	 */
	public Object getInterface(Class clas)
	{
		return getInterface_2(new Class[] { clas });
	}

	/**
	 * Get dynamic proxy for interface, caching those it creates.
	 */
	public Object getInterface_2(Class[] ca)
	{
		if (interfaces == null)
		{
			interfaces = new Hashtable();
		}
		// Make a hash of the interface hashcodes in order to cache them
		int hash = 21;
		for (int i = 0; i < ca.length; i++)
		{
			hash *= ca[i].hashCode() + 3;
		}
		Object hashKey = new Integer(hash);
		Object interf = interfaces.get(hashKey);
		if (interf == null)
		{
			ClassLoader classLoader = ca[0].getClassLoader(); // ?
			interf = Proxy.newProxyInstance(classLoader, ca, invocationHandler);
			interfaces.put(hashKey, interf);
		}
		return interf;
	}
}
