package org.scribble.codegen.java;

public class AbstractMethodBuilder extends MethodBuilder
{
	protected AbstractMethodBuilder()
	{
		
	}

	@Override
	public void addBodyLine(String ln)
	{
		throw new RuntimeException("Invalid for abstract method: " + ln);
	}

	@Override
	public void addBodyLine(int i, String ln)
	{
		throw new RuntimeException("Invalid for abstract method: " + ln);
	}
	
	public String generate()
	{
		String meth = super.generate();
		return meth.substring(0, meth.indexOf('{')) + ";";
	}
}