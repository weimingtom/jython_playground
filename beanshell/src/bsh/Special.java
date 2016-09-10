package bsh;

import java.io.Serializable;

//import bsh.Primitive.Special;

public class Special implements Serializable
{
	private Special()
	{
		
	}
	
	public static final Special NULL_VALUE = new Special();
	public static final Special VOID_TYPE = new Special();
}
