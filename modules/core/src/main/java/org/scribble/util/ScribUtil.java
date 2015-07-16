package org.scribble.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.scribble.ast.ScribNode;
import org.scribble.main.RuntimeScribbleException;

public class ScribUtil
{
	// Strict class equality, cf. ScribNodeBase#visitChildWithClassCheck
	// C is expected to be a ground class type
	// Maybe pointless (in terms of formal guarantees) to use equality instead of assignable
	public static <C extends ScribNode> C checkNodeClassEquality(C c, ScribNode n)
	{
		if (!c.getClass().equals(n.getClass()))
		{
			throw new RuntimeException("Node class not equal: " + c.getClass() + ", " + n.getClass());
		}
		@SuppressWarnings("unchecked")
		C tmp = (C) n;
		return tmp;
	}

	// C is expected to be a ground class type
	public static <C extends ScribNode> C castNodeByClass(C cast, ScribNode n)
	{
		if (!cast.getClass().isAssignableFrom(n.getClass()))
		{
			throw new RuntimeException("Node class cast error: " + cast.getClass() + ", " + n.getClass());
		}
		@SuppressWarnings("unchecked")
		C tmp = (C) n;
		return tmp;
	}
	
	public static <N extends ScribNode> List<N> cloneList(List<N> ns)
	{
		return ns.stream().map((n) -> checkNodeClassEquality(n, n.clone())).collect(Collectors.toList());
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
