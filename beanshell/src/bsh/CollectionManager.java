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

import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;
import java.lang.reflect.Array;

/**
 * The default CollectionManager (which remains Java 1.1 compatible) supports
 * iteration over objects of type: Enumeration, Vector, String, StringBuffer and
 * array. The dynamically loaded CollectionManagerImpl supports additional types
 * when it is present.
 * 
 * @see BshIterable.java
 */
public class CollectionManager
{
	private static CollectionManager manager;

	public synchronized static CollectionManager getCollectionManager()
	{
		if (manager == null && Capabilities.classExists("java.util.Collection"))
		{
			Class clas;
			try
			{
				clas = Class.forName("bsh.collection.CollectionManagerImpl");
				manager = (CollectionManager) clas.newInstance();
			} 
			catch (Exception e)
			{
				Interpreter.debug("unable to load CollectionManagerImpl: " + e);
			}
		}
		if (manager == null)
		{
			manager = new CollectionManager(); // default impl
		}
		return manager;
	}

	/**
	*/
	public boolean isBshIterable(Object obj)
	{
		// This could be smarter...
		try
		{
			getBshIterator(obj);
			return true;
		} 
		catch (IllegalArgumentException e)
		{
			return false;
		}
	}

	public BshIterator getBshIterator(Object obj) throws IllegalArgumentException
	{
		return new BasicBshIterator(obj);
	}

	public boolean isMap(Object obj)
	{
		return obj instanceof Hashtable;
	}

	public Object getFromMap(Object map, Object key)
	{
		return ((Hashtable) map).get(key);
	}

	public Object putInMap(Object map, Object key, Object value)
	{
		return ((Hashtable) map).put(key, value);
	}


}
