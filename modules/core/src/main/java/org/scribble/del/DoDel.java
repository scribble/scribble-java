package org.scribble.del;

import org.scribble.ast.Do;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ContextBuilder;

@Deprecated 
public class DoDel extends SimpleInteractionNodeDel
{
	@Override
	public Do leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		//builder.addProtocolDependency(getTargetFullProtocolName(builder.getModuleContext()));
		return (Do) visited;
	}
}
