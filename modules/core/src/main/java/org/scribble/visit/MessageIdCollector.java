package org.scribble.visit;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.MessageId;

public class MessageIdCollector extends NoEnvOffsetSubprotocolVisitor
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
	protected final void offsetSubprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.offsetSubprotocolEnter(parent, child);
		child.del().enterOpCollection(parent, child, this);
	}
	
	@Override
	protected ScribNode offsetSubprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveOpCollection(parent, child, this, visited);
		return super.offsetSubprotocolLeave(parent, child, visited);
	}
}
