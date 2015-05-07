package org.scribble2.sesstype.name;

import org.scribble2.sesstype.ScopedMessage;

// A "Scribble generics" parameter -- FIXME: deprecate, parameter is a syntactic category, wrt. typing it should just be a message signature, payload, etc.
public class ScopedMessageParameter extends Parameter implements ScopedMessage
{
	private static final long serialVersionUID = 1L;

	public final Scope scope;

	public ScopedMessageParameter(Scope scope, KindEnum kind, String text)
	{
		super(kind, text);
		this.scope = scope;
	}

	@Override
	public Scope getScope()
	{
		return this.scope;
	}
}
