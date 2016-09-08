package org.python.parser.ast;

public interface operatorType
{
	public static final int Add = 1;
	public static final int Sub = 2;
	public static final int Mult = 3;
	public static final int Div = 4;
	public static final int Mod = 5;
	public static final int Pow = 6;
	public static final int LShift = 7;
	public static final int RShift = 8;
	public static final int BitOr = 9;
	public static final int BitXor = 10;
	public static final int BitAnd = 11;
	public static final int FloorDiv = 12;

	public static final String[] operatorTypeNames = new String[] { 
		"<undef>", 
		"Add", 
		"Sub", 
		"Mult", 
		"Div", 
		"Mod", 
		"Pow", 
		"LShift", 
		"RShift", 
		"BitOr", 
		"BitXor", 
		"BitAnd", 
		"FloorDiv", 
	};
}
