package bsh;

/**
 * SignatureKey serves as a hash of a method signature on a class for fast
 * lookup of overloaded and general resolved Java methods.
 * <p>
 */
/*
 * Note: is using SignatureKey in this way dangerous? In the pathological
 * case a user could eat up memory caching every possible combination of
 * argument types to an untyped method. Maybe we could be smarter about it
 * by ignoring the types of untyped parameter positions? The method resolver
 * could return a set of "hints" for the signature key caching?
 * 
 * There is also the overhead of creating one of these for every method
 * dispatched. What is the alternative?
 */
class SignatureKey
{
	Class clas;
	Class[] types;
	String methodName;
	int hashCode = 0;

	SignatureKey(Class clas, String methodName, Class[] types)
	{
		this.clas = clas;
		this.methodName = methodName;
		this.types = types;
	}

	public int hashCode()
	{
		if (hashCode == 0)
		{
			hashCode = clas.hashCode() * methodName.hashCode();
			if (types == null) // no args method
			{
				return hashCode;
			}
			for (int i = 0; i < types.length; i++)
			{
				int hc = types[i] == null ? 21 : types[i].hashCode();
				hashCode = hashCode * (i + 1) + hc;
			}
		}
		return hashCode;
	}

	public boolean equals(Object o)
	{
		SignatureKey target = (SignatureKey) o;
		if (types == null)
		{
			return target.types == null;
		}
		if (clas != target.clas)
		{
			return false;
		}
		if (!methodName.equals(target.methodName))
		{
			return false;
		}
		if (types.length != target.types.length)
		{
			return false;
		}
		for (int i = 0; i < types.length; i++)
		{
			if (types[i] == null)
			{
				if (!(target.types[i] == null))
				{
					return false;
				}
			} 
			else if (!types[i].equals(target.types[i]))
			{
				return false;
			}
		}
		return true;
	}
}
