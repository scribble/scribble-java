package org.scribble2.model.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble2.model.ModelNode;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.sesstype.name.Op;
import org.scribble2.util.ScribbleException;

public class OpCollector extends SubprotocolVisitor
{
	private Set<Op> ops = new HashSet<>();
	
	public OpCollector(Job job, ModuleContext mcontext)
	{
		super(job);
		setModuleContext(mcontext);
	}
	
	public void addOp(Op op)
	{
		this.ops.add(op);
	}
	
	public Set<Op> getOps()
	{
		return this.ops;
	}

	@Override
	protected final void subprotocolEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterOpCollection(parent, child, this);
	}
	
	@Override
	protected ModelNode subprotocolLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		visited = visited.del().leaveOpCollection(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}
}
