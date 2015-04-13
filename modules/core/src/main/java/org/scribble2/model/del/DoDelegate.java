package org.scribble2.model.del;

import org.scribble2.model.Do;
import org.scribble2.model.ModelNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.util.ScribbleException;

@Deprecated 
public class DoDelegate extends SimpleInteractionNodeDelegate
{
	@Override
	public Do leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		//builder.addProtocolDependency(getTargetFullProtocolName(builder.getModuleContext()));
		return (Do) visited;
	}
}
