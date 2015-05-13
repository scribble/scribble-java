package org.scribble2.sesstype.name;

import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.ScopedMessage;
import org.scribble2.sesstype.kind.Kind;


// A "Scribble generics" parameter -- FIXME: deprecate, parameter is a syntactic category, wrt. typing it should just be a message signature, payload, etc.
@Deprecated
public class Parameter extends SimpleName implements Message//, PayloadType
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
	public Parameter(KindEnum kind, String text)
	{
		//super(kind, text);
		super(null, null);
		//this.kind = kind;
	}

	/*@Override
	public ScopedMessage toScopedMessage(Scope scope)
	{
		if (this.kindenum != KindEnum.SIG)
		{
			throw new RuntimeException("Not a message signature parameter: " + this.kindenum);
		}
		return new ScopedMessageParameter(scope, this.kindenum, toString());
	}*/
	
	//@Override
	public boolean isParameter()
	{
		return true;
	}

	@Override
	public Kind getKind()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageId getId()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
