package org.scribble.util;

import java.util.concurrent.Callable;

public class ScribbleUtil
{
	public static <T> T handleLambdaScribbleException(Callable<T> c)
	{
		try
		{
			return c.call();
		}
		catch (Exception se)
		{
			throw new RuntimeScribbleException(se);  // Maybe this hack is not worth it?  Better to throw directly as ScribbleException from a regular foreach
		}
	}
}
