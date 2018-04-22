package org.scribble.ext.go.core.ast.local;

import org.scribble.ext.go.core.ast.RPCoreActionKind;
import org.scribble.type.kind.Local;

public enum RPCoreLActionKind implements RPCoreActionKind<Local>
{
	CROSS_SEND,
	CROSS_RECEIVE,         // Multi-receive, but unary choice  // Cf. API gen, unary vs. non-unary choice
	MULTICHOICES_RECEIVE,  // Multi-choice receive
	DOT_SEND,
	DOT_RECEIVE;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case CROSS_SEND:           return "!";
			case CROSS_RECEIVE:        return "?";
			case MULTICHOICES_RECEIVE: return "?*";
			case DOT_SEND:             return "!=";
			case DOT_RECEIVE:          return "?=";
			default: throw new RuntimeException("[param-core] Won't get here: " + this);
		}
	}
}
