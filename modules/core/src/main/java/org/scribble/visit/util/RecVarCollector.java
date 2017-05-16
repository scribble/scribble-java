package org.scribble.visit.util;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;

// Collects continues (not recs)
public class RecVarCollector extends NameCollector<RecVar>
{
	public RecVarCollector(Job job)
	{
		super(job);
	}

	public RecVarCollector(Job job, ModuleContext mcontext)
	{
		super(job, mcontext);
	}

	@Override
	protected final void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.offsetSubprotocolEnter(parent, child);
		if (child instanceof ProtocolDecl<?>)
		{
			super.clear();
		}
		child.del().enterRecVarCollection(parent, child, this);
	}
	
	@Override
	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveRecVarCollection(parent, child, this, visited);
		return super.offsetSubprotocolLeave(parent, child, visited);
	}
}
