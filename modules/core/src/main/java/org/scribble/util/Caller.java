package org.scribble.util;

import java.util.concurrent.Callable;

public class Caller
{
	public <T> T call(Callable<T> c)
	{
		try
		{
			return c.call();
		}
		catch (Exception e)
		{
			if (e instanceof RuntimeException)
			{
				throw ((RuntimeException) e);
			}
			throw new RuntimeException(e);
		}
	}
}