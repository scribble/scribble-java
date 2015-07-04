package org.scribble.ast;

import org.scribble.sesstype.kind.ProtocolKind;

public interface ProtocolKindNode<K extends ProtocolKind> extends ScribNode
{
	default boolean isGlobal()
	{
		return false;
	}

	default boolean isLocal()
	{
		return false;
	}
	
	K getKind(); 
}
