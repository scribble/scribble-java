package org.scribble.ast.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.sesstype.name.MessageId;
import org.scribble.util.ScribbleException;

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
	protected final void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterOpCollection(parent, child, this);
	}
	
	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveOpCollection(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}
}
