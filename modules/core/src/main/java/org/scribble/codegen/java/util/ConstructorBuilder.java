package org.scribble.codegen.java.util;

import org.scribble.main.RuntimeScribbleException;

public class ConstructorBuilder extends MethodBuilder
{
	protected ConstructorBuilder(String name)
	{
		super(name);
	}
	
	@Override
	public void setName(String name)
	{
		throw new RuntimeScribbleException("Invalid for constructor");
	}
	
	@Override
	public void setReturn(String ret)
	{
		throw new RuntimeScribbleException("Invalid for constructor");
	}
}