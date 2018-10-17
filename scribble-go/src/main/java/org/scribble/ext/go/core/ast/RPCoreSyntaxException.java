package org.scribble.ext.go.core.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.AntlrSourceException;

// For parsing errors due to core syntax restrictions (vs. "full" Scribble) -- distinction used for JUnit testing (i.e., to indicate non core syntax that are otherwise valid protocols)
// i.e., should only be thrown by AssrtCoreGProtocolDeclTranslator
// N.B. so should not be used for actual "semantic" WF errors
public class RPCoreSyntaxException extends AntlrSourceException   // N.B. not Scribble/AssrttException -- cf. AssrtCoreTestBase::tests
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public RPCoreSyntaxException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RPCoreSyntaxException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RPCoreSyntaxException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RPCoreSyntaxException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RPCoreSyntaxException(CommonTree blame, String arg0)
	{
		super(blame, arg0);
		// TODO Auto-generated constructor stub
	}

}
