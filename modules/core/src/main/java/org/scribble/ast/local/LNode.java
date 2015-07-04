package org.scribble.ast.local;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.sesstype.kind.Local;


public interface LNode extends ProtocolKindNode<Local>
{
	//LocalNode project(Projector proj) throws ScribbleException;

	/*default boolean isGlobal()
	{
		return false;
	}*/

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
