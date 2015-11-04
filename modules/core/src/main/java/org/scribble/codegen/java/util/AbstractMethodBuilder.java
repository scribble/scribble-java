package org.scribble.codegen.java.util;

public class AbstractMethodBuilder extends MethodBuilder
{
	protected AbstractMethodBuilder()
	{
		addModifiers(Builder.ABSTRACT);  // Redundant for interfaces, but OK
	}

	@Override
	public void addBodyLine(String ln)
	{
		throw new RuntimeException("Invalid for abstract method: " + ln);
	}
	
	@Override
	protected String buildBody(String meth)
	{
		return meth + ";";
	}
}
