package org.scribble.ast.local;

import org.scribble.ast.ScribNode;


public interface LNode extends ScribNode
{
	//LocalNode project(Projector proj) throws ScribbleException;

	default boolean isLocal()
	{
		return true;
	}
}
