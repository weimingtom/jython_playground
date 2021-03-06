// Copyright (c) Corporation for National Research Initiatives
package org.python.modules;

import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.__builtin__;

public class os implements ClassDictInit
{
	public static String[] __depends__ = new String[] { "javaos", };
	
	// An ugly hack, but it keeps the site.py from CPython2.0 happy
	public static void classDictInit(PyObject dict)
	{
		// Fake from javaos import *
		PyTuple all = new PyTuple(new PyString[] { Py.newString('*') });
		PyObject module = __builtin__.__import__("javaos", null, null, all);
		PyObject names = module.__dir__();
		PyObject name;
		for (int i = 0; (name = names.__finditem__(i)) != null; i++)
		{
			String sname = name.toString().intern();
			dict.__setitem__(name, module.__getattr__(sname));
		}
		String prefix = Py.getSystemState().prefix;
		if (prefix != null)
		{
			String libdir = prefix + "/Lib/javaos.py";
			dict.__setitem__("__file__", new PyString(libdir));
		}
	}
}
