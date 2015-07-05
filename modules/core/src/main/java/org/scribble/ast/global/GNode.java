package org.scribble.ast.global;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.sesstype.kind.Global;

public interface GNode extends ProtocolKindNode<Global>
{
	@Override
	default boolean isGlobal()
	{
		return true;
	}
	
	@Override
	default Global getKind()
	{
		return Global.KIND;
	}
}
