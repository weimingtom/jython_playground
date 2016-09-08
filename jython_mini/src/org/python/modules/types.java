// Copyright (c) Corporation for National Research Initiatives
package org.python.modules;

import org.python.core.ClassDictInit;
import org.python.core.PyArray;
import org.python.core.PyBuiltinFunction;
import org.python.core.PyClass;
import org.python.core.PyCode;
import org.python.core.PyComplex;
import org.python.core.PyDictionary;
import org.python.core.PyEllipsis;
import org.python.core.PyFile;
import org.python.core.PyFloat;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyGenerator;
import org.python.core.PyInstance;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyMethod;
import org.python.core.PyModule;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PySlice;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyTraceback;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.PyXRange;

public class types implements ClassDictInit
{
	public static PyString __doc__ = new PyString(
		"Define names for all type symbols known in the standard " + 
		"interpreter.\n" + 
		"\n" + 
		"Types that are part of optional modules (e.g. array) " + 
		"are not listed.\n"
	);

	// xxx change some of these
	public static void classDictInit(PyObject dict)
	{
		dict.__setitem__("ArrayType", PyType.fromClass(PyArray.class));
		dict.__setitem__("BuiltinFunctionType", PyType.fromClass(PyBuiltinFunction.class));
		dict.__setitem__("BuiltinMethodType", PyType.fromClass(PyBuiltinFunction.class));
		dict.__setitem__("ClassType", PyType.fromClass(PyClass.class));
		dict.__setitem__("CodeType", PyType.fromClass(PyCode.class));
		dict.__setitem__("ComplexType", PyType.fromClass(PyComplex.class));
		dict.__setitem__("DictType", PyType.fromClass(PyDictionary.class));
		dict.__setitem__("DictionaryType", PyType.fromClass(PyDictionary.class));
		dict.__setitem__("DictProxyType", PyType.fromClass(PyStringMap.class));
		dict.__setitem__("EllipsisType", PyType.fromClass(PyEllipsis.class));
		dict.__setitem__("FileType", PyType.fromClass(PyFile.class));
		dict.__setitem__("FloatType", PyType.fromClass(PyFloat.class));
		dict.__setitem__("FrameType", PyType.fromClass(PyFrame.class));
		dict.__setitem__("FunctionType", PyType.fromClass(PyFunction.class));
		dict.__setitem__("GeneratorType", PyType.fromClass(PyGenerator.class));
		dict.__setitem__("InstanceType", PyType.fromClass(PyInstance.class));
		dict.__setitem__("IntType", PyType.fromClass(PyInteger.class));
		dict.__setitem__("LambdaType", PyType.fromClass(PyFunction.class));
		dict.__setitem__("ListType", PyType.fromClass(PyList.class));
		dict.__setitem__("LongType", PyType.fromClass(PyLong.class));
		dict.__setitem__("MethodType", PyType.fromClass(PyMethod.class));
		dict.__setitem__("ModuleType", PyType.fromClass(PyModule.class));
		dict.__setitem__("NoneType", PyType.fromClass(PyNone.class));
		dict.__setitem__("ObjectType", PyType.fromClass(PyObject.class));
		dict.__setitem__("SliceType", PyType.fromClass(PySlice.class));
		dict.__setitem__("StringType", PyType.fromClass(PyString.class));
		dict.__setitem__("TracebackType", PyType.fromClass(PyTraceback.class));
		dict.__setitem__("TupleType", PyType.fromClass(PyTuple.class));
		dict.__setitem__("TypeType", PyType.fromClass(PyType.class));
		dict.__setitem__("UnboundMethodType", PyType.fromClass(PyMethod.class));
		dict.__setitem__("UnicodeType", PyType.fromClass(PyUnicode.class));
		dict.__setitem__("XRangeType", PyType.fromClass(PyXRange.class));
		dict.__setitem__("StringTypes", new PyTuple(new PyObject[] { PyType.fromClass(PyString.class), PyType.fromClass(PyUnicode.class) }));
	}
}
