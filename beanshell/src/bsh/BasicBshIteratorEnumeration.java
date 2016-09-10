package bsh;

import java.lang.reflect.Array;
import java.util.Enumeration;

public class BasicBshIteratorEnumeration implements Enumeration
{
	private Object array;
	private int index = 0;
	private int length;
	
	public BasicBshIteratorEnumeration(Object array)
	{
		this.array = array;
		this.length = Array.getLength(array);
	}
	
	public Object nextElement()
	{
		return Array.get(array, index++);
	}

	public boolean hasMoreElements()
	{
		return index < length;
	}
}
