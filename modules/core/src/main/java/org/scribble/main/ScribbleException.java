package org.scribble.main;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.antlr.runtime.tree.CommonTree;

public class ScribbleException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScribbleException()
	{
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(CommonTree blame, String arg0)
	{
		super(foo(blame) + "(" + blame.getLine() + ":" + blame.getCharPositionInLine() + ") " + arg0);
	}
	
	// Cf., AntlrModule/AntlrModuleDecl -- but can't access parser classes from core (Maven dependencies)
	private static String foo(CommonTree blame)
	{
		CommonTree root = blame;
		while (root.parent != null)
		{
			root = root.parent;
		}
		CommonTree moddecl = (CommonTree) root.getChild(0).getChild(0);
		int count = moddecl.getChildCount();
		return IntStream.range(0, count).mapToObj((i) -> moddecl.getChild(i).getText()).collect(Collectors.joining(", "));
	}

	public ScribbleException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ScribbleException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
}
