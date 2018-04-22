package org.scribble.ext.go.core.main;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.ScribbleException;

//public class AssrtException extends Exception
public class RPCoreException extends ScribbleException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RPCoreException()
	{
		// TODO Auto-generated constructor stub
	}

	public RPCoreException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RPCoreException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RPCoreException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RPCoreException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RPCoreException(CommonTree blame, String arg0)
	{
		super(blame, arg0);
		// TODO Auto-generated constructor stub
	}

}
