package org.scribble.ast.local;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.sesstype.kind.Local;

public interface LNode extends ProtocolKindNode<Local>
{
	@Override
	default boolean isLocal()
	{
		return true;
	}
	
	@Override
	default Local getKind()
	{
		return Local.KIND;
	}
}
