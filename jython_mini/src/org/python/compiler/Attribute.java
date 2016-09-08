// Copyright (c) Corporation for National Research Initiatives

package org.python.compiler;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Attribute
{
	public abstract void write(DataOutputStream s) throws IOException;
}
