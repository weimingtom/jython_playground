/*
 * Copyright 1998 Finn Bock.
 * Permission to use, copy and distribute this software is hereby granted, 
 * provided that the above copyright notice appear in all copies and that 
 * both that copyright notice and this permission notice appear.
 * 
 * No Warranty
 * The software is provided "as is" without warranty of any kind.
 * 
 * If you have questions regarding this software, contact:
 *    Finn Bock, bckfnn@pipmail.dknet.dk
 * 
 * This program contains material copyrighted by:
 * Copyright � 1991-1995 by Stichting Mathematisch Centrum, Amsterdam, 
 * The Netherlands. 
 */

package org.python.modules;

import java.util.*;

import org.python.core.*;

/**
 * 
 * From the python documentation:
 * <p>
 * The <tt>cPickle.java</tt> module implements a basic but powerful algorithm for
 * ``pickling'' (a.k.a. serializing, marshalling or flattening) nearly
 * arbitrary Python objects.  This is the act of converting objects to a
 * stream of bytes (and back: ``unpickling'').
 * This is a more primitive notion than
 * persistency -- although <tt>cPickle.java</tt> reads and writes file objects,
 * it does not handle the issue of naming persistent objects, nor the
 * (even more complicated) area of concurrent access to persistent
 * objects.  The <tt>cPickle.java</tt> module can transform a complex object into
 * a byte stream and it can transform the byte stream into an object with
 * the same internal structure.  The most obvious thing to do with these
 * byte streams is to write them onto a file, but it is also conceivable
 * to send them across a network or store them in a database.  The module
 * <tt>shelve</tt> provides a simple interface to pickle and unpickle
 * objects on ``dbm''-style database files.
 * <P>
 * <b>Note:</b> The <tt>cPickle.java</tt> have the same interface as the 
 * standard module <tt>pickle</tt>except that <tt>Pickler</tt> and
 * <tt>Unpickler</tt> are factory functions, not classes (so they cannot be
 * used as base classes for inheritance).
 * This limitation is similar for the original cPickle.c version.
 * 
 * <P>
 * Unlike the built-in module <tt>marshal</tt>, <tt>cPickle.java</tt> handles
 * the following correctly:
 * <P>
 * 
 * <UL><LI>recursive objects (objects containing references to themselves)
 * 
 * <P>
 * 
 * <LI>object sharing (references to the same object in different places)
 * 
 * <P>
 * 
 * <LI>user-defined classes and their instances
 * 
 * <P>
 * 
 * </UL>
 * 
 * <P>
 * The data format used by <tt>cPickle.java</tt> is Python-specific.  This has
 * the advantage that there are no restrictions imposed by external
 * standards such as XDR (which can't represent pointer sharing); however
 * it means that non-Python programs may not be able to reconstruct
 * pickled Python objects.
 * 
 * <P>
 * By default, the <tt>cPickle.java</tt> data format uses a printable ASCII
 * representation.  This is slightly more voluminous than a binary
 * representation.  The big advantage of using printable ASCII (and of
 * some other characteristics of <tt>cPickle.java</tt>'s representation) is that
 * for debugging or recovery purposes it is possible for a human to read
 * the pickled file with a standard text editor.
 * 
 * <P>
 * A binary format, which is slightly more efficient, can be chosen by
 * specifying a nonzero (true) value for the <i>bin</i> argument to the
 * <tt>Pickler</tt> constructor or the <tt>dump()</tt> and <tt>dumps()</tt>
 * functions.  The binary format is not the default because of backwards
 * compatibility with the Python 1.4 pickle module.  In a future version,
 * the default may change to binary.
 * 
 * <P>
 * The <tt>cPickle.java</tt> module doesn't handle code objects.
 * <P>
 * For the benefit of persistency modules written using <tt>cPickle.java</tt>, it
 * supports the notion of a reference to an object outside the pickled
 * data stream.  Such objects are referenced by a name, which is an
 * arbitrary string of printable ASCII characters.  The resolution of
 * such names is not defined by the <tt>cPickle.java</tt> module -- the
 * persistent object module will have to implement a method
 * <tt>persistent_load()</tt>.  To write references to persistent objects,
 * the persistent module must define a method <tt>persistent_id()</tt> which
 * returns either <tt>None</tt> or the persistent ID of the object.
 * 
 * <P>
 * There are some restrictions on the pickling of class instances.
 * 
 * <P>
 * First of all, the class must be defined at the top level in a module.
 * Furthermore, all its instance variables must be picklable.
 * 
 * <P>
 * 
 * <P>
 * When a pickled class instance is unpickled, its <tt>__init__()</tt> method
 * is normally <i>not</i> invoked.  <b>Note:</b> This is a deviation
 * from previous versions of this module; the change was introduced in
 * Python 1.5b2.  The reason for the change is that in many cases it is
 * desirable to have a constructor that requires arguments; it is a
 * (minor) nuisance to have to provide a <tt>__getinitargs__()</tt> method.
 * 
 * <P>
 * If it is desirable that the <tt>__init__()</tt> method be called on
 * unpickling, a class can define a method <tt>__getinitargs__()</tt>,
 * which should return a <i>tuple</i> containing the arguments to be
 * passed to the class constructor (<tt>__init__()</tt>).  This method is
 * called at pickle time; the tuple it returns is incorporated in the
 * pickle for the instance.
 * <P>
 * Classes can further influence how their instances are pickled -- if the class
 * defines the method <tt>__getstate__()</tt>, it is called and the return
 * state is pickled as the contents for the instance, and if the class
 * defines the method <tt>__setstate__()</tt>, it is called with the
 * unpickled state.  (Note that these methods can also be used to
 * implement copying class instances.)  If there is no
 * <tt>__getstate__()</tt> method, the instance's <tt>__dict__</tt> is
 * pickled.  If there is no <tt>__setstate__()</tt> method, the pickled
 * object must be a dictionary and its items are assigned to the new
 * instance's dictionary.  (If a class defines both <tt>__getstate__()</tt>
 * and <tt>__setstate__()</tt>, the state object needn't be a dictionary
 * -- these methods can do what they want.)  This protocol is also used
 * by the shallow and deep copying operations defined in the <tt>copy</tt>
 * module.
 * <P>
 * Note that when class instances are pickled, their class's code and
 * data are not pickled along with them.  Only the instance data are
 * pickled.  This is done on purpose, so you can fix bugs in a class or
 * add methods and still load objects that were created with an earlier
 * version of the class.  If you plan to have long-lived objects that
 * will see many versions of a class, it may be worthwhile to put a version
 * number in the objects so that suitable conversions can be made by the
 * class's <tt>__setstate__()</tt> method.
 * 
 * <P>
 * When a class itself is pickled, only its name is pickled -- the class
 * definition is not pickled, but re-imported by the unpickling process.
 * Therefore, the restriction that the class must be defined at the top
 * level in a module applies to pickled classes as well.
 * 
 * <P>
 * 
 * <P>
 * The interface can be summarized as follows.
 * 
 * <P>
 * To pickle an object <tt>x</tt> onto a file <tt>f</tt>, open for writing:
 * 
 * <P>
 * <dl><dd><pre>
 * p = pickle.Pickler(f)
 * p.dump(x)
 * </pre></dl>
 * 
 * <P>
 * A shorthand for this is:
 * 
 * <P>
 * <dl><dd><pre>
 * pickle.dump(x, f)
 * </pre></dl>
 * 
 * <P>
 * To unpickle an object <tt>x</tt> from a file <tt>f</tt>, open for reading:
 * 
 * <P>
 * <dl><dd><pre>
 * u = pickle.Unpickler(f)
 * x = u.load()
 * </pre></dl>
 * 
 * <P>
 * A shorthand is:
 * 
 * <P>
 * <dl><dd><pre>
 * x = pickle.load(f)
 * </pre></dl>
 * 
 * <P>
 * The <tt>Pickler</tt> class only calls the method <tt>f.write()</tt> with a
 * string argument.  The <tt>Unpickler</tt> calls the methods <tt>f.read()</tt>
 * (with an integer argument) and <tt>f.readline()</tt> (without argument),
 * both returning a string.  It is explicitly allowed to pass non-file
 * objects here, as long as they have the right methods.
 * 
 * <P>
 * The constructor for the <tt>Pickler</tt> class has an optional second
 * argument, <i>bin</i>.  If this is present and nonzero, the binary
 * pickle format is used; if it is zero or absent, the (less efficient,
 * but backwards compatible) text pickle format is used.  The
 * <tt>Unpickler</tt> class does not have an argument to distinguish
 * between binary and text pickle formats; it accepts either format.
 * 
 * <P>
 * The following types can be pickled:
 * 
 * <UL><LI><tt>None</tt>
 * 
 * <P>
 * 
 * <LI>integers, long integers, floating point numbers
 * 
 * <P>
 * 
 * <LI>strings
 * 
 * <P>
 * 
 * <LI>tuples, lists and dictionaries containing only picklable objects
 * 
 * <P>
 * 
 * <LI>classes that are defined at the top level in a module
 * 
 * <P>
 * 
 * <LI>instances of such classes whose <tt>__dict__</tt> or
 * <tt>__setstate__()</tt> is picklable
 * 
 * <P>
 * 
 * </UL>
 * 
 * <P>
 * Attempts to pickle unpicklable objects will raise the
 * <tt>PicklingError</tt> exception; when this happens, an unspecified
 * number of bytes may have been written to the file.
 * 
 * <P>
 * It is possible to make multiple calls to the <tt>dump()</tt> method of
 * the same <tt>Pickler</tt> instance.  These must then be matched to the
 * same number of calls to the <tt>load()</tt> method of the
 * corresponding <tt>Unpickler</tt> instance.  If the same object is
 * pickled by multiple <tt>dump()</tt> calls, the <tt>load()</tt> will all
 * yield references to the same object.  <i>Warning</i>: this is intended
 * for pickling multiple objects without intervening modifications to the
 * objects or their parts.  If you modify an object and then pickle it
 * again using the same <tt>Pickler</tt> instance, the object is not
 * pickled again -- a reference to it is pickled and the
 * <tt>Unpickler</tt> will return the old value, not the modified one.
 * (There are two problems here: (a) detecting changes, and (b)
 * marshalling a minimal set of changes.  I have no answers.  Garbage
 * Collection may also become a problem here.)
 * 
 * <P>
 * Apart from the <tt>Pickler</tt> and <tt>Unpickler</tt> classes, the
 * module defines the following functions, and an exception:
 * 
 * <P>
 * <dl><dt><b><tt>dump</tt></a></b> (<var>object, file</var><big>[</big><var>, bin</var><big>]</big>)
 * <dd>
 * Write a pickled representation of <i>obect</i> to the open file object
 * <i>file</i>.  This is equivalent to
 * "<tt>Pickler(<i>file</i>, <i>bin</i>).dump(<i>object</i>)</tt>".
 * If the optional <i>bin</i> argument is present and nonzero, the binary
 * pickle format is used; if it is zero or absent, the (less efficient)
 * text pickle format is used.
 * </dl>
 * 
 * <P>
 * <dl><dt><b><tt>load</tt></a></b> (<var>file</var>)
 * <dd>
 * Read a pickled object from the open file object <i>file</i>.  This is
 * equivalent to "<tt>Unpickler(<i>file</i>).load()</tt>".
 * </dl>
 * 
 * <P>
 * <dl><dt><b><tt>dumps</tt></a></b> (<var>object</var><big>[</big><var>, bin</var><big>]</big>)
 * <dd>
 * Return the pickled representation of the object as a string, instead
 * of writing it to a file.  If the optional <i>bin</i> argument is
 * present and nonzero, the binary pickle format is used; if it is zero
 * or absent, the (less efficient) text pickle format is used.
 * </dl>
 * 
 * <P>
 * <dl><dt><b><tt>loads</tt></a></b> (<var>string</var>)
 * <dd>
 * Read a pickled object from a string instead of a file.  Characters in
 * the string past the pickled object's representation are ignored.
 * </dl>
 * 
 * <P>
 * <dl><dt><b><a name="l2h-3763"><tt>PicklingError</tt></a></b>
 * <dd>
 * This exception is raised when an unpicklable object is passed to
 * <tt>Pickler.dump()</tt>.
 * </dl>
 * 
 * 
 * <p>
 * For the complete documentation on the pickle module, please see the 
 * "Python Library Reference"
 * <p><hr><p>
 * 
 * The module is based on both original pickle.py and the cPickle.c
 * version, except that all mistakes and errors are my own.
 * <p>
 * @author Finn Bock, bckfnn@pipmail.dknet.dk
 * @version $Id$
 */
public class cPickle implements InitModule {
    private static cPickle m;
    private static PyStringMap ns;

    /**
     * The program version.
     */
    public static String __version__ = "$Revision$";

    /**
     * File format version we write.
     */
    public static final String format_version = "1.3";  

    /**
     * Old format versions we can read.
     */
    public static final String[] compatible_formats = 
		new String[] { "1.0", "1.1", "1.2" };

    /**
     * This exception is raised when an unpicklable object is 
     * passed to Pickler.dump(). 
     */
    public static final PyString PicklingError = 
		new PyString("pickle.PicklingError");


    /**
     * This exception if the input file does not contain a valid pickled object
     * or it is unsafe to unpickle the object.
     */
    public static final PyString UnpicklingError =
		new PyString("pickle.UnpicklingError");


    final static char MARK            = '(';
    final static char STOP            = '.';
    final static char POP             = '0';
    final static char POP_MARK        = '1';
    final static char DUP             = '2';
    final static char FLOAT           = 'F';
    final static char INT             = 'I';
    final static char BININT          = 'J';
    final static char BININT1         = 'K';
    final static char LONG            = 'L';
    final static char BININT2         = 'M';
    final static char NONE            = 'N';
    final static char PERSID          = 'P';
    final static char BINPERSID       = 'Q';
    final static char REDUCE          = 'R';
    final static char STRING          = 'S';
    final static char BINSTRING       = 'T';
    final static char SHORT_BINSTRING = 'U';
    final static char APPEND          = 'a';
    final static char BUILD           = 'b';
    final static char GLOBAL          = 'c';
    final static char DICT            = 'd';
    final static char EMPTY_DICT      = '}';
    final static char APPENDS         = 'e';
    final static char GET             = 'g';
    final static char BINGET          = 'h';
    final static char INST            = 'i';
    final static char LONG_BINGET     = 'j';
    final static char LIST            = 'l';
    final static char EMPTY_LIST      = ']';
    final static char OBJ             = 'o';
    final static char PUT             = 'p';
    final static char BINPUT          = 'q';
    final static char LONG_BINPUT     = 'r';
    final static char SETITEM         = 's';
    final static char TUPLE           = 't';
    final static char EMPTY_TUPLE     = ')';
    final static char SETITEMS        = 'u';
    final static char BINFLOAT        = 'G';

    private static PyDictionary dispatch_table = null;
    private static PyDictionary safe_constructors = null;


    private static PyClass BuiltinFunctionType = PyJavaClass.lookup(PyReflectedFunction.class);
    private static PyClass BuiltinMethodType = PyJavaClass.lookup(PyMethod.class);
    private static PyClass ClassType = PyJavaClass.lookup(PyClass.class);
    private static PyClass DictionaryType = PyJavaClass.lookup(PyDictionary.class);
    private static PyClass StringMapType = PyJavaClass.lookup(PyStringMap.class);
    private static PyClass FloatType = PyJavaClass.lookup(PyFloat.class);
    private static PyClass FunctionType = PyJavaClass.lookup(PyFunction.class);
    private static PyClass InstanceType = PyJavaClass.lookup(PyInstance.class);
    private static PyClass IntType = PyJavaClass.lookup(PyInteger.class);
    private static PyClass ListType = PyJavaClass.lookup(PyList.class);
    private static PyClass LongType = PyJavaClass.lookup(PyLong.class);
    private static PyClass NoneType = PyJavaClass.lookup(PyNone.class);
    private static PyClass StringType = PyJavaClass.lookup(PyString.class);
    private static PyClass TupleType = PyJavaClass.lookup(PyTuple.class);
    private static PyClass FileType = PyJavaClass.lookup(PyFile.class);
 

    /**
     * Initialization when module is imported.
     */
    public void initModule(PyObject dict) {

	ns = (PyStringMap)dict;

	// XXX: Hack for JPython 1.0.1. By default __builtin__ is not in
	// sys.modules.
	imp.importName("__builtin__", true);

	PyModule copyreg = (PyModule)imp.importName("copy_reg", true);

	dispatch_table = (PyDictionary)copyreg.__getattr__("dispatch_table");
	safe_constructors = (PyDictionary)copyreg.__getattr__("safe_constructors");
    }

    public cPickle() {
	m = this;
    }


    /**
     * Returns a pickler instance.
     * @param file	a file-like object, can be a cStringIO.StringIO, 
     *			a PyFile or any python object which implements a
     * 			<i>write</i> method. The data will be written as text.
     * @returns a new Pickler instance.
     */
    public static Pickler Pickler(PyObject file) {
	return m.new Pickler(file, false);
    }


    /**
     * Returns a pickler instance.
     * @param file	a file-like object, can be a cStringIO.StringIO, 
     *			a PyFile or any python object which implements a 
     *			<i>write</i> method.
     * @param bin	when true, the output will be written as binary data.
     * @returns		a new Pickler instance.
     */
    public static Pickler Pickler(PyObject file, boolean bin) {
	return m.new Pickler(file, bin);
    }


    /**
     * Returns a unpickler instance.
     * @param file	a file-like object, can be a cStringIO.StringIO, 
     *			a PyFile or any python object which implements a 
     *			<i>read</i> and <i>readline</i> method. 
     * @returns		a new Unpickler instance.
     */
    public static Unpickler Unpickler(PyObject file) {
	return m.new Unpickler(file);
    }


    /**
     * Shorthand function which pickles the object on the file.
     * @param object	a data object which should be pickled.
     * @param file	a file-like object, can be a cStringIO.StringIO, 
     *			a PyFile or any python object which implements a <i>write</i>
     *			method. The data will be written as text.
     * @returns		a new Unpickler instance.
     */
    public static void dump(PyObject object, PyObject file) {
	dump(object, file, false);
    }

    /**
     * Shorthand function which pickles the object on the file.
     * @param object	a data object which should be pickled.
     * @param file	a file-like object, can be a cStringIO.StringIO, 
     *			a PyFile or any python object which implements a <i>write</i>
     *			method. The data will be written as text.
     * @param bin 	when true, the output will be written as binary data.
     * @returns		a new Unpickler instance.
     */
    public static void dump(PyObject object, PyObject file, boolean bin) {
	m.new Pickler(file, bin).dump(object);
    }


    /**
     * Shorthand function which pickles and returns the string representation.
     * @param object	a data object which should be pickled.
     * @returns		a string representing the pickled object.
     */
    public static String dumps(PyObject object) {
	return dumps(object, false);
    }


    /**
     * Shorthand function which pickles and returns the string representation.
     * @param object	a data object which should be pickled.
     * @param bin 	when true, the output will be written as binary data.
     * @returns		a string representing the pickled object.
     */
    public static String dumps(PyObject object, boolean bin) {
	cStringIO.StringIO file = cStringIO.StringIO();
	dump(object, file, bin);
	return file.getvalue();
    }


    /**
     * Shorthand function which unpickles a object from the file and returns 
     * the new object.
     * @param file	a file-like object, can be a cStringIO.StringIO, 
     *			a PyFile or any python object which implements a 
     *			<i>read</i> and <i>readline</i> method. 
     * @returns		a new object.
     */
    public static Object load(PyObject file) {
	return m.new Unpickler(file).load();
    }


    /**
     * Shorthand function which unpickles a object from the string and returns 
     * the new object.
     * @param str	a strings which must contain a pciked object representation.
     * @returns		a new object.
     */
    public static Object loads(PyObject str) {
	cStringIO.StringIO file = cStringIO.StringIO(str.toString());
	return m.new Unpickler(file).load();
    }
    


    // Factory for creating IOFile representation.
    private static IOFile createIOFile(PyObject file) {
	Object f = file.__tojava__(cStringIO.StringIO.class);
	if (f != Py.NoConversion)
	    return m.new cStringIOFile((cStringIO.StringIO)file);
	else if (__builtin__.isinstance(file, FileType))
	    return m.new FileIOFile(file);
	else 
	    return m.new ObjectIOFile(file);
    }


    // IOFiles encapsulates and optimise access to the different file
    // representation.
    interface IOFile {
	public abstract void write(String str);
	// Usefull optimization since most data written are chars.
	public abstract void write(char str);
	public abstract void flush();
	public abstract String read(int len);
	// Usefull optimization since all readlines removes the trainling newline.
	public abstract String readlineNoNl();

    }


    // Use a cStringIO as a file.
    class cStringIOFile implements IOFile {
	cStringIO.StringIO file;

	cStringIOFile(PyObject file) {
	    this.file = (cStringIO.StringIO)file.__tojava__(Object.class);
	}

	public void write(String str) {
	    file.write(str);
	}

	public void write(char ch) {
	    file.writeChar(ch);
	}

	public void flush() {}

	public String read(int len) {
	    return file.read(len);
	}

	public String readlineNoNl() {
	    return file.readlineNoNl();
	}
    }


    // Use a PyFile as a file.
    class FileIOFile implements IOFile {
	PyFile file;

	FileIOFile(PyObject file) {
	    this.file = (PyFile)file.__tojava__(PyFile.class);
	}

	public void write(String str) {
	    file.write(str);
	}

	public void write(char ch) {
	    file.write(cStringIO.getString(ch));
	}

	public void flush() {}

	public String read(int len) {
	    return file.read(len).toString();
	}

	public String readlineNoNl() {
	    String line = file.readline().toString();
	    return line.substring(0, line.length()-1);
	}
    }


    // Use any python object as a file.
    class ObjectIOFile implements IOFile {
	char[] charr = new char[1];
	StringBuffer buff = new StringBuffer();
	PyObject write;
	PyObject read;
	PyObject readline;
	final int BUF_SIZE = 256;

	ObjectIOFile(PyObject file) {
//	    this.file = file;
	    write = file.__findattr__("write");
	    read = file.__findattr__("read");
	    readline = file.__findattr__("readline");
	}

	public void write(String str) {
	    buff.append(str);
	    if (buff.length() > BUF_SIZE)
		flush();
	}

	public void write(char ch) {
	    buff.append(ch);
	    if (buff.length() > BUF_SIZE)
		flush();
	}

	public void flush() {
	    write.__call__(new PyString(buff.toString()));
	    buff.setLength(0);
	}

	public String read(int len) {
	    return read.__call__(new PyInteger(len)).toString();
	}

	public String readlineNoNl() {
	    String line = readline.__call__().toString();
	    return line.substring(0, line.length()-1);
	}
    }


    /**
     * The Pickler object
     * @see cPickle#Pickler(PyObject)
     * @see cPickle#Pickler(PyObject,boolean)
     */
    public class Pickler {
	private IOFile file;
	private boolean bin;

	/**
	 * Hmm, not documented, perhaps it shouldn't be public? XXX: fixme.
	 */
	private PickleMemo memo = new PickleMemo();

	/**
	 * To write references to persistent objects, the persistent module 
	 * must assign a method to persistent_id which returns either None 
	 * or the persistent ID of the object.
	 * For the benefit of persistency modules written using pickle,
	 * it supports the notion of a reference to an object outside 
	 * the pickled data stream.
	 * Such objects are referenced by a name, which is an arbitrary 
	 * string of printable ASCII characters. 
	 */
	public PyObject persistent_id = null;

	/**
	 * Hmm, not documented, perhaps it shouldn't be public? XXX: fixme.
	 */
	public PyObject inst_persistent_id = null;


	Pickler(PyObject file, boolean bin) {
	    this.file = createIOFile(file);
	    this.bin = bin;
	}


	/**
	 * Write a pickled representation of the object.
	 * @param object	The object which will be pickled.
	 */
	public void dump(PyObject object) {
	    save(object);
	    file.write(STOP);
	    file.flush();
	}


	// Save name as in pickle.py but semantics are slightly changed.
	private void put(int i) {
	    if (bin) {
		if (i < 256) {
		    file.write(BINPUT);
		    file.write((char)i);
		    return;
		}
	 	file.write(LONG_BINPUT);
		file.write((char)( i         & 0xFF));
		file.write((char)((i >>>  8) & 0xFF));
		file.write((char)((i >>> 16) & 0xFF));
		file.write((char)((i >>> 24) & 0xFF));
		return;
	    }
	    file.write(PUT);
	    file.write(String.valueOf(i));
	    file.write("\n");
	}


	// Same name as in pickle.py but semantics are slightly changed.
	private void get(int i) {
	    if (bin) {
		if (i < 256) {
		    file.write(BINGET);
		    file.write((char)i);
		    return;
		}
	 	file.write(LONG_BINGET);
		file.write((char)( i         & 0xFF));
		file.write((char)((i >>>  8) & 0xFF));
		file.write((char)((i >>> 16) & 0xFF));
		file.write((char)((i >>> 24) & 0xFF));
		return;
	    }
	    file.write(GET);
	    file.write(String.valueOf(i));
	    file.write("\n");
	}


	private void save(PyObject object) {
	    save(object, false);
	}


	private void save(PyObject object, boolean pers_save) {
	    if (!pers_save) {
		if (persistent_id != null) {
		    PyObject pid = persistent_id.__call__(object);
		    if (pid != Py.None) {
			save_pers(pid);
			return;
		    }
		}
	    }

	    int d = Py.id(object);

	    PyClass t = __builtin__.type(object);

	    if (t == TupleType && object.__len__() == 0) {
		if (bin) 
		    save_empty_tuple(object);
		else
                    save_tuple(object);
		return;
	    }

	    int m = getMemoPosition(d);
	    if (m >= 0) {
		get(m);
		return;
	    }

	    if (save_type(object, t))
		return;

	    if (inst_persistent_id != null) {
		PyObject pid = inst_persistent_id.__call__(object);
		if (pid != Py.None) {
		    save_pers(pid);
		    return;
		}
	    }

	    PyObject tup = null;
	    PyObject reduce = dispatch_table.__finditem__(t);
	    if (reduce == null) {
		reduce = object.__findattr__("__reduce__");
		if (reduce == null)
		    throw new PyException(PicklingError, 
			    "can't pickle " + t.__name__ + " objects");
		tup = reduce.__call__();
	    } else {
		tup = reduce.__call__(object);
	    }

	    if (tup instanceof PyString) {
		save_global(object, tup);
		return;
	    }

	    if (!(tup instanceof PyTuple)) {
		throw new PyException(PicklingError,
			    "Value returned by " + reduce.__repr__() + 
			    " must be a tuple");
	    }

	    int l = tup.__len__();
	    if (l != 2 && l != 3) {
		throw new PyException(PicklingError,
			    "tuple returned by " + reduce.__repr__() + 
			    " must contain only two or three elements");
	    }

	    PyObject callable = tup.__finditem__(0);
	    PyObject arg_tup = tup.__finditem__(1);
	    PyObject state = (l > 2) ? tup.__finditem__(2) : Py.None;

	    if (!(arg_tup instanceof PyTuple) && arg_tup != Py.None) {
		throw new PyException(PicklingError,
			    "Second element of tupe returned by " + reduce.__repr__() + 
			    " must be a tuple");
	    }

	    save_reduce(callable, arg_tup, state);

	    put(putMemo(d, object));
	}


	final private void save_pers(PyObject pid) {
	    if (!bin) {
		file.write(PERSID);
		file.write(pid.toString());
		file.write("\n");
	    } else {
		save(pid, true);
		file.write(BINPERSID);
	    }
	}

	final private void save_reduce(PyObject callable, PyObject arg_tup, PyObject state)
	{
	    save(callable);
	    save(arg_tup);
	    file.write(REDUCE);
	    if (state != Py.None) {
		save(state);
		file.write(BUILD);
	    }
	}



	final private boolean save_type(PyObject object, PyClass cls) {
	    //System.out.println("save_type " + object + " " + cls);
	    if (cls == NoneType)
		save_none(object);
	    else if (cls == StringType)
		save_string(object);
	    else if (cls == IntType)
		save_int(object);
	    else if (cls == LongType)
		save_long(object);
	    else if (cls == FloatType)
		save_float(object);
	    else if (cls == TupleType)
		save_tuple(object);
	    else if (cls == ListType)
		save_list(object);
	    else if (cls == DictionaryType || cls == StringMapType)
		save_dict(object);
	    else if (cls == InstanceType)
		save_inst((PyInstance)object);
	    else if (cls == ClassType)
		save_global(object);
	    else if (cls == FunctionType)
		save_global(object);
	    else if (cls == BuiltinFunctionType)
		save_global(object);
	    else
		return false;
	    return true;
	}



	final private void save_none(PyObject object) {
	    file.write(NONE);
	}

	final private void save_int(PyObject object) {
	    if (bin) {
		int l = ((PyInteger)object).getValue();
		char i1 = (char)( l         & 0xFF);
		char i2 = (char)((l >>> 8 ) & 0xFF);
		char i3 = (char)((l >>> 16) & 0xFF);
		char i4 = (char)((l >>> 24) & 0xFF);

		if (i3 == '\0' && i4 == '\0') {
		    if (i2 == '\0') {
			file.write(BININT1);
			file.write(i1);
			return;
		    }
		    file.write(BININT2);
		    file.write(i1);
		    file.write(i2);
		    return;
		}
		file.write(BININT);
		file.write(i1);
		file.write(i2);
		file.write(i3);
		file.write(i4);
	    } else {
		file.write(INT);
		file.write(object.toString());
		file.write("\n");
	    }
	}


	final private void save_long(PyObject object) {
	    file.write(LONG);
	    file.write(object.toString());
	    file.write("\n");
	}


	final private void save_float(PyObject object) {
	    if (bin) {
		file.write(BINFLOAT);
		double value= ((PyFloat) object).getValue();
		// It seems that struct.pack('>d', ..) and doubleToLongBits
		// are the same. Good for me :-)
		long bits = Double.doubleToLongBits(value);
		file.write((char)((bits >>> 56) & 0xFF));
		file.write((char)((bits >>> 48) & 0xFF));
		file.write((char)((bits >>> 40) & 0xFF));
		file.write((char)((bits >>> 32) & 0xFF));
		file.write((char)((bits >>> 24) & 0xFF));
		file.write((char)((bits >>> 16) & 0xFF));
		file.write((char)((bits >>>  8) & 0xFF));
		file.write((char)((bits >>>  0) & 0xFF));
	    } else {
		file.write(FLOAT);
		file.write(object.toString());
		file.write("\n");
	    }
	}


	final private void save_string(PyObject object) {
	    if (bin) {
		int l = object.__len__();
		if (l < 256) {
		    file.write(SHORT_BINSTRING);
		    file.write((char)l);
		} else {
		    file.write(BINSTRING);
		    file.write((char)( l         & 0xFF));
		    file.write((char)((l >>> 8 ) & 0xFF));
		    file.write((char)((l >>> 16) & 0xFF));
		    file.write((char)((l >>> 24) & 0xFF));
		}
		file.write(object.toString());
	    } else {
		file.write(STRING);
		file.write(object.__repr__().toString());
		file.write("\n");
	    }
	    put(putMemo(Py.id(object), object));
	}


	final private void save_tuple(PyObject object) {
	    int d = Py.id(object);

	    file.write(MARK);

	    int len = object.__len__();

	    for (int i = 0; i < len; i++)
		save(object.__finditem__(i));

	    if (len > 0) {
		int m = getMemoPosition(d);
		if (m >= 0) {
		    if (bin) {
			file.write(POP_MARK);
			get(m);
			return;
		    }
		    for (int i = 0; i < len+1; i++)
			file.write(POP);
		    get(m);
		    return;
		}
	    }
	    file.write(TUPLE);
	    put(putMemo(d, object));
	}


	final private void save_empty_tuple(PyObject object) {
	    file.write(EMPTY_TUPLE);
	}

	final private void save_list(PyObject object) {
	    if (bin)
		file.write(EMPTY_LIST);
	    else {
		file.write(MARK);
		file.write(LIST);
	    }

	    put(putMemo(Py.id(object), object));

	    int len = object.__len__();
	    boolean using_appends = bin && len > 1;

	    if (using_appends)
		file.write(MARK);

	    for (int i = 0; i < len; i++) {
		save(object.__finditem__(i));
		if (!using_appends)
		    file.write(APPEND);
	    }
	    if (using_appends)
		file.write(APPENDS);
	}


	final private void save_dict(PyObject object) {
	    if (bin)
		file.write(EMPTY_DICT);
	    else {
		file.write(MARK);
		file.write(DICT);
	    }

	    put(putMemo(Py.id(object), object));

	    PyObject list = object.invoke("keys");
	    int len = list.__len__();

	    boolean using_setitems = (bin && len > 1);

            if (using_setitems)
		file.write(MARK);

	    for (int i = 0; i < len; i++) {
		PyObject key = list.__finditem__(i);
		PyObject value = object.__finditem__(key);
		save(key);
		save(value);
	
	        if (!using_setitems)
		     file.write(SETITEM);
	    }
	    if (using_setitems)
		file.write(SETITEMS);
	}
	

	final private void save_inst(PyInstance object) {
	    PyClass cls = object.__class__;

	    PySequence args = null;
	    PyObject getinitargs = object.__findattr__("__getinitargs__");
	    if (getinitargs != null) {
		args = (PySequence)getinitargs.__call__();
		// XXX Assert it's a sequence
	        keep_alive(args);
	    }

	    file.write(MARK);
	    if (bin)
		save(cls);

	    if (args != null) {
		int len = args.__len__();
		for (int i = 0; i < len; i++)
		    save(args.__finditem__(i));
	    }

	    int mid = putMemo(Py.id(object), object);
	    if (bin) {
		file.write(OBJ);
		put(mid);
	    } else {
		file.write(INST);
		file.write(cls.__findattr__("__module__").toString());
		file.write("\n");
		file.write(cls.__name__);
		file.write("\n");
		put(mid);
	    }

	    PyObject stuff = null;
	    PyObject getstate = object.__findattr__("__getstate__");
	    if (getstate == null) {
		stuff = object.__dict__;
	    } else {
		stuff = getstate.__call__();
		keep_alive(stuff);
	    }
	    save(stuff);
	    file.write(BUILD);
	}


	final private void save_global(PyObject object) {
	    save_global(object, null);
	}


	final private void save_global(PyObject object, PyObject name) {
	    if (name == null)
		name = object.__findattr__("__name__");

	    PyObject module = object.__findattr__("__module__");
	    if (module == null || module == Py.None) 
		module = whichmodule(object, name);

	    file.write(GLOBAL);
	    file.write(module.toString());
	    file.write("\n");
	    file.write(name.toString());
	    file.write("\n");
	    put(putMemo(Py.id(object), object));
	}


	final private int getMemoPosition(int id) {
	    return memo.findPosition(id);
	}

	final private int putMemo(int id, PyObject object) {
	    int memo_len = memo.size();
	    memo.put(id, memo_len, object);
	    return memo_len;
	}


	/**
	 * Keeps a reference to the object x in the memo.
	 * 
	 * Because we remember objects by their id, we have
	 * to assure that possibly temporary objects are kept
	 * alive by referencing them.
	 * We store a reference at the id of the memo, which should
	 * normally not be used unless someone tries to deepcopy
	 * the memo itself...
	 */
	final private void keep_alive(PyObject obj) {
	    int id = System.identityHashCode(memo);
	    PyList list = (PyList) memo.findValue(id);
	    if (list == null) {
		list = new PyList();
		memo.put(id, -1, list);
	    }
	    list.append(obj);
	}
    }




    private static Hashtable classmap = new Hashtable();

    final private static PyObject whichmodule(PyObject cls, PyObject clsname) {
	PyObject name = (PyObject)classmap.get(cls);
	if (name != null)
	    return name;

	name = new PyString("__main__");

	// For use with JPython1.0.x
	//PyObject modules = sys.modules;

	// For use with JPython1.1.x
	//PyObject modules = Py.getSystemState().modules;

	PyObject sys = imp.importName("sys", true);
	PyObject modules = sys.__findattr__("modules");
	PyObject keylist = modules.invoke("keys");

	int len = keylist.__len__();
	for (int i = 0; i < len; i++) {
	    PyObject key = keylist.__finditem__(i);
	    PyObject value = modules.__finditem__(key);

	    if (!key.equals("__main__") && 
			value.__findattr__(clsname.toString()) == cls) {
		name = key;
		break;
	    }
	}

	classmap.put(cls, name);
	//System.out.println(name);
	return name;
    }


    /*
     * A very specialized and simplified version of PyStringMap. It can only use
     * integers as keys and stores both an integer and an object as value.
     * It is very private!
     */
    private class PickleMemo {
	//Table of primes to cycle through
	private final int[] primes = {
	    13, 61, 251, 1021, 4093,
	    5987, 9551, 15683, 19609, 31397,
	    65521, 131071, 262139, 524287, 1048573, 2097143,
	    4194301, 8388593, 16777213, 33554393, 67108859,
	    134217689, 268435399, 536870909, 1073741789,};

	private transient int[] keys;
	private transient int[] position;
	private transient Object[] values;

	private int size;
	private transient int filled;
	private transient int prime;

	// Not actually used, since there is no delete methods.
	private String DELETEDKEY = "<deleted key>";

	public PickleMemo(int capacity) {
	    prime = 0;
	    keys = null;
	    values = null;
	    resize(capacity);
	}

	public PickleMemo() {
	    this(4);
	}
   
	public synchronized int size() {
	    return size;
	}

	private int findIndex(int key) {
	    int[] table = keys;
	    int maxindex = table.length;
	    int index = (key & 0x7fffffff) % maxindex;

	    // Fairly aribtrary choice for stepsize...
	    int stepsize = maxindex / 5;

	    // Cycle through possible positions for the key;
	    //int collisions = 0;
	    while (true) {
		int tkey = table[index];
		if (tkey == key) {
		    return index;
		}
		if (values[index] == null) return -1;
		index = (index+stepsize) % maxindex;
	    }
	}

	public int findPosition(int key) {
	    int idx = findIndex(key);
	    if (idx < 0) return -1;
	    return position[idx];
	}


	public Object findValue(int key) {
	    int idx = findIndex(key);
	    if (idx < 0) return null;
	    return values[idx];
	}


	private final void insertkey(int key, int pos, Object value) {
	    int[] table = keys;
	    int maxindex = table.length;
	    int index = (key & 0x7fffffff) % maxindex;

	    // Fairly aribtrary choice for stepsize...
	    int stepsize = maxindex / 5;

	    // Cycle through possible positions for the key;
	    while (true) {
		int tkey = table[index];
		if (values[index] == null) {
		    table[index] = key;
		    position[index] = pos;
		    values[index] = value;
		    filled++;
		    size++;
		    break;
		} else if (tkey == key) {
		    position[index] = pos;
		    values[index] = value;
		    break;
		} else if (values[index] == DELETEDKEY) {
		    table[index] = key;
		    position[index] = pos;
		    values[index] = value;
		    size++;
		    break;
		}
		index = (index+stepsize) % maxindex;
	    }
	}


	private synchronized final void resize(int capacity) {
	    int p = prime;
	    for(; p<primes.length; p++) {
		if (primes[p] >= capacity) break;
	    }
	    if (primes[p] < capacity) {
		throw Py.ValueError("can't make hashtable of size: "+capacity);
	    }
	    capacity = primes[p];
	    prime = p;

	    int[] oldKeys = keys;
	    int[] oldPositions = position;
	    Object[] oldValues = values;

	    keys = new int[capacity];
	    position = new int[capacity];
	    values = new Object[capacity];
	    size = 0;
	    filled = 0;

	    if (oldValues != null) {
		int n = oldValues.length;

		for(int i=0; i<n; i++) {
		    Object value = oldValues[i];
		    if (value == null || value == DELETEDKEY) continue;
		    insertkey(oldKeys[i], oldPositions[i], value);
		}
	    }
	}

	public void put(int key, int pos, Object value) {
	    if (2*filled > keys.length) resize(keys.length+1);
	    insertkey(key, pos, value);
	}
    }






    /**
     * The Unpickler object. Unpickler instances are create by the factory methods
     * Unpickler.
     * @see cPickle#Unpickler(PyObject)
     */
    public class Unpickler {

	private IOFile file;

	public Hashtable memo = new Hashtable();

	/**
	 * For the benefit of persistency modules written using pickle,
	 * it supports the notion of a reference to an object outside 
	 * the pickled data stream.
	 * Such objects are referenced by a name, which is an arbitrary 
	 * string of printable ASCII characters. 
	 * The resolution of such names is not defined by the pickle module 
	 * -- the persistent object module will have to add a method 
	 * persistent_load().
	 */
	public PyObject persistent_load = null;

	private PyObject mark = new PyString("spam");

	private int stackTop;
	private PyObject[] stack;


	Unpickler(PyObject file) {
	    this.file = createIOFile(file);
	}


	/**
	 * Unpickle and return an instance of the object represented by the file.
	 */
	public PyObject load() {
	    stackTop = 0;
	    stack = new PyObject[10];

	    while (true) {
		String s = file.read(1);
//		System.out.println("load:" + s);
//		for (int i = 0; i < stackTop; i++) 
//		    System.out.println("   " + stack[i]);
		if (s.length() < 1)
		    load_eof();
		char key = s.charAt(0);
		switch (key) {
		case PERSID:          load_persid(); break;
		case BINPERSID:       load_binpersid(); break;
		case NONE:            load_none(); break;
		case INT:             load_int(); break;
		case BININT:          load_binint(); break;
		case BININT1:         load_binint1(); break;
		case BININT2:         load_binint2(); break;
		case LONG:            load_long(); break;
		case FLOAT:           load_float(); break;
		case BINFLOAT:        load_binfloat(); break;
		case STRING:          load_string(); break;
		case BINSTRING:       load_binstring(); break;
		case SHORT_BINSTRING: load_short_binstring(); break;
		case TUPLE:           load_tuple(); break;
		case EMPTY_TUPLE:     load_empty_tuple(); break;
		case EMPTY_LIST:      load_empty_list(); break;
		case EMPTY_DICT:      load_empty_dictionary(); break;
		case LIST:            load_list(); break;
		case DICT:            load_dict(); break;
		case INST:            load_inst(); break;
		case OBJ:             load_obj(); break;
		case GLOBAL:          load_global(); break;
		case REDUCE:          load_reduce(); break;
		case POP:             load_pop(); break;
		case POP_MARK:        load_pop_mark(); break;
		case DUP:             load_dup(); break;
		case GET:             load_get(); break;
		case BINGET:          load_binget(); break;
		case LONG_BINGET:     load_long_binget(); break;
		case PUT:             load_put(); break;
		case BINPUT:          load_binput(); break;
		case LONG_BINPUT:     load_long_binput(); break;
		case APPEND:          load_append(); break;
		case APPENDS:         load_appends(); break;
		case SETITEM:         load_setitem(); break;
		case SETITEMS:        load_setitems(); break;
		case BUILD:           load_build(); break;
		case MARK:            load_mark(); break;
		case STOP:            
		    return load_stop(); 
		}
	    }
	}


	final private int marker() {
	    for (int k = stackTop-1; k >= 0; k--)
		if (stack[k] == mark)
		    return stackTop-k-1;
	    throw new PyException(UnpicklingError, 
			"Inputstream corrupt, marker not found");
	}


	final private void load_eof() {
	    throw new PyException(Py.EOFError);
	}


	final private void load_persid() {
	    String pid = file.readlineNoNl();
	    push(persistent_load.__call__(new PyString(pid)));
	}


	final private void load_binpersid() {
	    PyObject pid = pop();
	    push(persistent_load.__call__(pid));
	}


	final private void load_none() {
	    push(Py.None);
	}


	final private void load_int() {
	    String line = file.readlineNoNl();
	    // XXX: use strop instead?
	    push(new PyInteger(Integer.parseInt(line)));
	}


	final private void load_binint() {
	    String s = file.read(4);
	    int x = s.charAt(0) |
		   (s.charAt(1)<<8) | 
		   (s.charAt(2)<<16) |
		   (s.charAt(3)<<24);
	    push(new PyInteger(x));
	}


	final private void load_binint1() {
	    int val = (int)file.read(1).charAt(0);
	    push(new PyInteger(val));
	}

	final private void load_binint2() {
	    String s = file.read(2);
	    int val = ((int)s.charAt(1)) << 8 | ((int)s.charAt(0));
	    push(new PyInteger(val));
	}


	final private void load_long() {
	    String line = file.readlineNoNl();
	    push(new PyLong(line.substring(0, line.length()-1)));
	}
 
	final private void load_float() {
	    String line = file.readlineNoNl();
	    push(new PyFloat(Double.valueOf(line).doubleValue()));
	}

	final private void load_binfloat() {
	    String s = file.read(8);
	    long bits = (long)s.charAt(7) |
			((long)s.charAt(6) << 8) | 
			((long)s.charAt(5) << 16) |
			((long)s.charAt(4) << 24) |
			((long)s.charAt(3) << 32) |
			((long)s.charAt(2) << 40) |
			((long)s.charAt(1) << 48) |
			((long)s.charAt(0) << 56);
	    push(new PyFloat(Double.longBitsToDouble(bits)));
	}

	final private void load_string() {
	    String line = file.readlineNoNl();

	    String value;
	    char quote = line.charAt(0);
	    if (quote != '"' && quote != '\'') { // Evil string!?
		value = line;
	    } else {
		value = setString(line, 1);
	    }
	    push(new PyString(value));
	}


    // Brutally stolen from org.python.parser.SimpleNode.java
    private String setString(String s, int quotes) {
        //System.out.println("string: "+s);
        char quoteChar = s.charAt(0);
        if (quoteChar == 'r' || quoteChar == 'R') {
            return s.substring(quotes+1, s.length()-quotes);
        } else {
            StringBuffer sb = new StringBuffer(s.length());
            char[] ca = s.toCharArray();
            int n = ca.length-quotes;
            int i=quotes;
            int last_i=i;

            while (i<n) {
                if (ca[i] == '\r') {
                    sb.append(ca, last_i, i-last_i);
                    sb.append('\n');
                    i++;
                    if (ca[i] == '\n') i++;
                    last_i = i;
                    continue;
                }
                if (ca[i++] != '\\' || i >= n) continue;
                sb.append(ca, last_i, i-last_i-1);
                switch(ca[i++]) {
                case '\r':
                    if (ca[i] == '\n') i++;
                case '\n': break;
                case 'b': sb.append('\b'); break;
                case 't': sb.append('\t'); break;
                case 'n': sb.append('\n'); break;
                case 'f': sb.append('\f'); break;
                case 'r': sb.append('\r'); break;
                case '\"':
                case '\'':
                    sb.append(ca[i-1]);
                    break;
                case '\\': sb.append('\\'); break;
                //Special Python escapes
                case 'a': sb.append('\007'); break;
                case 'v': sb.append('\013'); break;

                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    int c = ca[i-1]-'0';
                    if (i<n && '0' <= ca[i] && ca[i] <= '7') {
                        c = (c<<3) + (ca[i++] -'0');
                        if (i<n && '0' <= ca[i] && ca[i] <= '7') {
                            c = (c<<3) + (ca[i++] -'0');
                        }
                    }
                    sb.append((char)c);
                    break;
                case 'x':
                    if (Character.digit(ca[i], 16) != -1) {
                        int digit;
                        char x=0;
                        while (i<n && (digit = Character.digit(ca[i++], 16)) != -1) {
                            x = (char)(x*16 + digit);
                        }
                        if (i<n) i-=1;
                        sb.append(x);
                        break;
                    }
                    // If illegal hex digit, just fall through
                default:
                    sb.append('\\');
                    sb.append(ca[i-1]);
                }
                last_i = i;
            }
            sb.append(ca, last_i, i-last_i);
            return sb.toString();
        }
    }

 

	final private void load_binstring() {
	    String d = file.read(4);
	    int len = d.charAt(0) |
		     (d.charAt(1)<<8) | 
		     (d.charAt(2)<<16) |
		     (d.charAt(3)<<24);
	    push(new PyString(file.read(len)));
	}


	final private void load_short_binstring() {
	    int len = (int)file.read(1).charAt(0);
	    push(new PyString(file.read(len)));
	}


	final private void load_tuple() {
	    PyObject[] arr = new PyObject[marker()];
	    pop(arr);
	    pop();
	    push(new PyTuple(arr));
	}

	final private void load_empty_tuple() {
	    push(new PyTuple(Py.EmptyObjects));
	}

	final private void load_empty_list() {
	    push(new PyList(Py.EmptyObjects));
	}

	final private void load_empty_dictionary() {
	    push(new PyDictionary());
	}


	final private void load_list() {
	    PyObject[] arr = new PyObject[marker()];
	    pop(arr);
	    pop();
	    push(new PyList(arr));
	}


	final private void load_dict() {
	    int k = marker();
	    PyDictionary d = new PyDictionary();
	    for (int i = 0; i < k; i += 2) {
		PyObject value = pop();
		PyObject key = pop();
		d.__setitem__(key, value);
	    }
	    pop();
	    push(d);
	}

	    
	final private void load_inst() {
	    PyObject[] args = new PyObject[marker()];
	    pop(args);
	    pop();

	    String module = file.readlineNoNl();
	    String name = file.readlineNoNl();
	    PyObject klass = find_class(module, name);

	    PyObject value = null;
	    if (args.length == 0 && klass instanceof PyClass &&
			klass.__findattr__("__getinitargs__") == null) {
		value = new PyInstance((PyClass)klass);
	    } else {
		value = klass.__call__(args);
	    }
	    push(value);
	}


	final private void load_obj() {
	    PyObject[] args = new PyObject[marker()-1];
	    pop(args);
	    PyObject klass = pop();
	    pop();

	    PyObject value = null;
	    if (args.length == 0 && klass instanceof PyClass && 
			klass.__findattr__("__getinitargs__") == null) {
		value = new PyInstance((PyClass)klass);
	    } else {
	        value = klass.__call__(args);
	    }
	    push(value);
	}

	final private void load_global() {
	    String module = file.readlineNoNl();
	    String name = file.readlineNoNl();
	    PyObject klass = find_class(module, name);
	    push(klass);
	}


	final private PyObject find_class(String module, String name) {
	    PyObject env = null;
	    try {
		env = imp.importName(module.intern(), true);
	    } catch (PyException ex) {
		ex.printStackTrace();
		throw new PyException(Py.SystemError,
	                  "Failed to import class " + name + " from module " + module);
	    }
	    return env.__getattr__(name.intern());
	}


	final private void load_reduce() {
	    PyObject arg_tup = pop();
	    PyObject callable = pop();
	    if (!(callable instanceof PyClass)) {
		if (safe_constructors.__finditem__(callable) == null) {
		    if (callable.__findattr__("__safe_for_unpickling__") == null)
			throw new PyException(UnpicklingError,
				callable + " is not safe for unpickling");
		}
	    }

	    PyObject value = null;
	    if (arg_tup == Py.None) {
		// XXX __basicnew__ ?
		value = callable.__findattr__("__basicnew__").__call__();
	    } else {
		value = callable.__call__(make_array(arg_tup));
	    }
	    push(value);
	}

	final private PyObject[] make_array(PyObject seq) {
	    int n = seq.__len__();
	    PyObject[] objs= new PyObject[n];

	    for(int i=0; i<n; i++)
		objs[i] = seq.__finditem__(i);
	    return objs;
	}

	final private void load_pop() {
	    pop();
	}


	final private void load_pop_mark() {
	    pop(marker());
	}

	final private void load_dup() {
	    push(peek());
	}

	final private void load_get() {
	    push((PyObject)memo.get(file.readlineNoNl()));
	}

	final private void load_binget() {
	    int i = (int)file.read(1).charAt(0);
	    push((PyObject)memo.get(String.valueOf(i)));
	}

	final private void load_long_binget() {
	    String d = file.read(4);
	    int i = d.charAt(0) |
		   (d.charAt(1)<<8) | 
		   (d.charAt(2)<<16) |
		   (d.charAt(3)<<24);
	    push((PyObject)memo.get(String.valueOf(i)));
	}


	final private void load_put() {
	    memo.put(file.readlineNoNl(), peek());
	}


	final private void load_binput() {
	    int i = (int)file.read(1).charAt(0);
	    memo.put(String.valueOf(i), peek());
	}


	final private void load_long_binput() {
	    String d = file.read(4);
	    int i = d.charAt(0) |
		   (d.charAt(1)<<8) | 
		   (d.charAt(2)<<16) |
		   (d.charAt(3)<<24);
	    memo.put(String.valueOf(i), peek());
	}


	final private void load_append() {
	    PyObject value = pop();
	    PyList list = (PyList)peek();
	    list.append(value);
	}

	final private void load_appends() {
	    int mark = marker();
	    PyList list = (PyList)peek(mark+1);
	    for (int i = mark-1; i >= 0; i--)
		list.append(peek(i));
	    pop(mark+1);
	}

	final private void load_setitem() {
	    PyObject value = pop();
	    PyObject key   = pop();
	    PyDictionary dict = (PyDictionary)peek();
	    dict.__setitem__(key, value);
	}


	final private void load_setitems() {
	    int mark = marker();
	    PyDictionary dict = (PyDictionary)peek(mark+1);
	    for (int i = 0; i < mark; i += 2) {
		PyObject key   = peek(i+1);
		PyObject value = peek(i);
		dict.__setitem__(key, value);
	    }
	    pop(mark+1);
	}

	final private void load_build() {
	    PyObject value = pop();
	    PyInstance inst  = (PyInstance)peek();
	    PyObject setstate = inst.__findattr__("__setstate__");
	    if (setstate == null) {
		inst.__dict__.__findattr__("update").__call__(value);
	    } else {
		setstate.__call__(value);
	    }
	}

	final private void load_mark() {
	    push(mark);
	}

	final private PyObject load_stop() {
	    return pop();
	}



	final private PyObject peek() {
	    return stack[stackTop-1];
	}

	final private PyObject peek(int count) {
	    return stack[stackTop-count-1];
	}


	final private PyObject pop() {
	    PyObject val = stack[--stackTop];
	    stack[stackTop] = null;
	    return val;
	}

	final private void pop(int count) {
	    for (int i = 0; i < count; i++) 
		stack[--stackTop] = null;
	}


	final private void pop(PyObject[] arr) {
	    int len = arr.length;
	    System.arraycopy(stack, stackTop - len, arr, 0, len);
	    stackTop -= len;
	}

	final private void push(PyObject val) {
	    if (stackTop >= stack.length) {
		PyObject[] newStack = new PyObject[(stackTop+1) * 2];
		System.arraycopy(stack, 0, newStack, 0, stack.length);
		stack = newStack;
	    }
	    stack[stackTop++] = val;
	}
    }
}
