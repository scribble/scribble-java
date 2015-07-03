package org.scribble.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.scribble.ast.ScribNode;
import org.scribble.main.RuntimeScribbleException;

public class ScribUtil
{
	// Strict class equality, cf. ScribNodeBase#visitChildWithClassCheck
	public static <N extends ScribNode> N checkNodeClass(N n, ScribNode sn)
	{
		if (!n.getClass().equals(sn.getClass()))
		{
			throw new RuntimeException("Node class cast error: " + n.getClass() + ", " + sn.getClass());
		}
		@SuppressWarnings("unchecked")
		N tmp = (N) sn;
		return tmp;
	}
	
	public static <N extends ScribNode> List<N> cloneList(List<N> ns)
	{
		return ns.stream().map((n) -> checkNodeClass(n, n.clone())).collect(Collectors.toList());
	}
	
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
