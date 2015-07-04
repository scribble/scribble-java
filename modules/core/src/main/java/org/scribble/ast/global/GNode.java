package org.scribble.ast.global;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.sesstype.kind.Global;

public interface GNode extends ProtocolKindNode<Global>
{
	//LocalNode project(Projector proj) throws ScribbleException;

	@Override
	default boolean isGlobal()
	{
		return true;
	}

	/*default boolean isLocal()
	{
		return false;
	}*/
	
	@Override
	default Global getKind()
	{
		return Global.KIND;
	}
}
