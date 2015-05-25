package org.scribble2.model.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble2.model.ModelNode;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.sesstype.name.MessageId;
import org.scribble2.util.ScribbleException;

public class MessageIdCollector extends SubprotocolVisitor
{
	private Set<MessageId> mids = new HashSet<>();
	
	public MessageIdCollector(Job job, ModuleContext mcontext)
	{
		super(job);
		setModuleContext(mcontext);
	}
	
	public void addMessageId(MessageId mid)
	{
		this.mids.add(mid);
	}
	
	public Set<MessageId> getMessageIds()
	{
		return this.mids;
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
