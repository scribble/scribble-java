package org.scribble.ast.del;

import org.scribble.ast.Do;
import org.scribble.ast.ModelNode;
import org.scribble.ast.visit.ContextBuilder;
import org.scribble.util.ScribbleException;

@Deprecated 
public class DoDel extends SimpleInteractionNodeDel
{
	@Override
	public Do leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		//builder.addProtocolDependency(getTargetFullProtocolName(builder.getModuleContext()));
		return (Do) visited;
	}
}
