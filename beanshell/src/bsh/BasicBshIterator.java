package bsh;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Determine dynamically if the target is an iterator by the presence of a
 * pair of next() and hasNext() methods. public static boolean isIterator()
 * { }
 */

/**
 * An implementation that works with JDK 1.1
 */
public class BasicBshIterator implements BshIterator
{
	Enumeration enumeration;

	/**
	 * Construct a basic BasicBshIterator
	 * 
	 * @param The
	 *            object over which we are iterating
	 * 
	 * @throws java.lang.IllegalArgumentException
	 *             If the argument is not a supported (i.e. iterable) type.
	 * 
	 * @throws java.lang.NullPointerException
	 *             If the argument is null
	 */
	public BasicBshIterator(Object iterateOverMe)
	{
		enumeration = createEnumeration(iterateOverMe);
	}

	/**
	 * Create an enumeration over the given object
	 * 
	 * @param iterateOverMe
	 *            Object of type Enumeration, Vector, String, StringBuffer
	 *            or an array
	 * 
	 * @return an enumeration
	 * 
	 * @throws java.lang.IllegalArgumentException
	 *             If the argument is not a supported (i.e. iterable) type.
	 * 
	 * @throws java.lang.NullPointerException
	 *             If the argument is null
	 */
	protected Enumeration createEnumeration(Object iterateOverMe)
	{
		if (iterateOverMe == null)
		{
			throw new NullPointerException("Object arguments passed to " + "the BasicBshIterator constructor cannot be null.");
		}
		if (iterateOverMe instanceof Enumeration)
		{
			return (Enumeration) iterateOverMe;
		}
		if (iterateOverMe instanceof Vector)
		{
			return ((Vector) iterateOverMe).elements();
		}
		if (iterateOverMe.getClass().isArray())
		{
			final Object array = iterateOverMe;
			return new BasicBshIteratorEnumeration(array);
		}
		if (iterateOverMe instanceof String)
		{
			return createEnumeration(((String) iterateOverMe).toCharArray());
		}
		if (iterateOverMe instanceof StringBuffer)
		{
			return createEnumeration(iterateOverMe.toString().toCharArray());
		}
		throw new IllegalArgumentException("Cannot enumerate object of type " + iterateOverMe.getClass());
	}

	/**
	 * Fetch the next object in the iteration
	 * 
	 * @return The next object
	 */
	public Object next()
	{
		return enumeration.nextElement();
	}

	/**
	 * Returns true if and only if there are more objects available via the
	 * <code>next()</code> method
	 * 
	 * @return The next object
	 */
	public boolean hasNext()
	{
		return enumeration.hasMoreElements();
	}
}
