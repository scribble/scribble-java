package org.scribble2.sesstype.name;

import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.ScopedMessage;


// A "Scribble generics" parameter -- FIXME: deprecate, parameter is a syntactic category, wrt. typing it should just be a message signature, payload, etc.
public class Parameter extends SimpleName implements Message, PayloadTypeOrParameter
{
	private static final long serialVersionUID = 1L;

	/*public Argument.Kind kind;  // Do as subclasses instead?

	public Parameter(Argument.Kind kind, String text)
	{
		super(text);
		this.kind = kind;
	}*/
	
	//public final ParameterDecl.Kind kind;

	//public Parameter(ParameterDecl.Kind kind, String text)
	public Parameter(Kind kind, String text)
	{
		super(kind, text);
		//this.kind = kind;
	}

	@Override
	public ScopedMessage toScopedMessage(Scope scope)
	{
		if (this.kind != Kind.SIG)
		{
			throw new RuntimeException("Not a message signature parameter: " + this.kind);
		}
		return new ScopedMessageParameter(scope, this.kind, toString());
	}
	
	@Override
	public boolean isParameter()
	{
		return true;
	}
}
