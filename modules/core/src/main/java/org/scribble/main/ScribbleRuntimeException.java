package org.scribble.main;

// Note: not a runtime ScribbleException
public class ScribbleRuntimeException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ScribbleRuntimeException(String s)
	{
		super(s);
	}
	
	public ScribbleRuntimeException(Throwable t)
	{
		super(t);
	}
}
