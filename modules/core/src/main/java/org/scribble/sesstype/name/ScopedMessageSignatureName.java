package org.scribble.sesstype.name;

import org.scribble.sesstype.ScopedMessage;

// A "Scribble generics" parameter -- FIXME: deprecate, parameter is a syntactic category, wrt. typing it should just be a message signature, payload, etc.
@Deprecated
public class ScopedMessageSignatureName extends MessageSigName //implements ScopedMessage
{
	private static final long serialVersionUID = 1L;

	public final Scope scope;

	public ScopedMessageSignatureName(Scope scope, String text)
	{
		super(text);
		//super(scope, text);
		this.scope = scope;
	}

	/*@Override
	public Scope getScope()
	{
		return this.scope;
	}*/
}