package org.scribble2.sesstype.name;

import scribble2.sesstype.ScopedMessage;

// A "Scribble generics" parameter -- FIXME: deprecate, parameter is a syntactic category, wrt. typing it should just be a message signature, payload, etc.
public class ScopedMessageSignatureName extends MessageSignatureName implements ScopedMessage
{
	private static final long serialVersionUID = 1L;

	public final Scope scope;

	public ScopedMessageSignatureName(Scope scope, String text)
	{
		super(text);
		this.scope = scope;
	}

	@Override
	public Scope getScope()
	{
		return this.scope;
	}
}
