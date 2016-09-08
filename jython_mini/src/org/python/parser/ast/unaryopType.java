package org.python.parser.ast;

public interface unaryopType
{
	public static final int Invert = 1;
	public static final int Not = 2;
	public static final int UAdd = 3;
	public static final int USub = 4;

	public static final String[] unaryopTypeNames = new String[] { 
		"<undef>", 
		"Invert", 
		"Not", 
		"UAdd", 
		"USub", 
	};
}
