package org.scribble.visit;

import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.env.InlineProtocolEnv;

@Deprecated
public class InlinedProtocolUnfolder extends InlinedProtocolVisitor<InlineProtocolEnv>
{
	private Map<RecVar, ProtocolBlock<? extends ProtocolKind>> recvars = new HashMap<>();
	
	public InlinedProtocolUnfolder(Job job)
	{
		super(job);
	}
	
	public ProtocolBlock<?> getRecVar(RecVar recvar)
	{
		return this.recvars.get(recvar);
	}

	public void setRecVar(RecVar recvar, ProtocolBlock<? extends ProtocolKind> pb)
	{
		this.recvars.put(recvar, pb);
	}

	public void removeRecVar(RecVar recvar)
	{
		this.recvars.remove(recvar);
	}
	
	@Override
	protected InlineProtocolEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new InlineProtocolEnv();
	}
	
	@Override
	protected void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedProtocolEnter(parent, child);
		child.del().enterInlinedProtocolUnfolding(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveInlinedProtocolUnfolding(parent, child, this, visited);
		return super.inlinedProtocolLeave(parent, child, visited);
	}
}
