package org.scribble.ast;

import org.scribble.sesstype.kind.ProtocolKind;


// Make a compound interaction subclass for choice/parallel etc?
public interface InteractionNode<K extends ProtocolKind> extends ScribNode
{
	default boolean isGlobal()
	{
		return false;
	}

	default boolean isLocal()
	{
		return false;
	}
}
