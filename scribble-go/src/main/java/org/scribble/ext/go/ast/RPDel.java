package org.scribble.ext.go.ast;

import org.scribble.ast.ScribNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.core.visit.RPCoreIndexVarCollector;
import org.scribble.main.ScribbleException;

public interface RPDel extends ScribDel
{

	default void enterIndexVarCollection(ScribNode parent, ScribNode child, RPCoreIndexVarCollector coll)
	{
		
	}

	default ScribNode leaveIndexVarCollection(ScribNode parent, ScribNode child, RPCoreIndexVarCollector coll, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
