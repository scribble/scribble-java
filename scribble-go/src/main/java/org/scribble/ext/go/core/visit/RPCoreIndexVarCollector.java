package org.scribble.ext.go.core.visit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.RPDel;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.visit.NoEnvInlinedProtocolVisitor;

// FIXME: move out of Core to main RP -- currently not actually used (due to RPCoreType), but eventually should be used on locals
// Currently done for globals (e.g., RPGMessageTransferDel), but should be on locals
// Duplicated from EGraphBuilder
public class RPCoreIndexVarCollector extends NoEnvInlinedProtocolVisitor
{
	private final Set<RPIndexVar> ivars = new HashSet<>();
	
	public RPCoreIndexVarCollector(Job job)
	{
		super(job);
	}
	
	public void clear()
	{
		this.ivars.clear();
	}
	
	public void addIndexVars(Collection<RPIndexVar> ivars)
	{
		this.ivars.addAll(ivars);
	}

	public Set<RPIndexVar> getIndexVars()
	{
		return new HashSet<>(this.ivars);
	}

	/*// Override visitInlinedProtocol -- not visit, or else enter/exit is lost
	@Override
	public ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSeq((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else if (child instanceof LChoice)
		{
			return visitOverrideForLChoice((LInteractionSeq) parent, (LChoice) child);
		}
		else
		{
			return super.visitInlinedProtocol(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSeq(LProtocolBlock parent, LInteractionSeq child) throws ScribbleException
	{
		return ((LInteractionSeqDel) child.del()).visitForFsmConversion(this, child);
	}

	protected LChoice visitOverrideForLChoice(LInteractionSeq parent, LChoice child)
	{
		return ((LChoiceDel) child.del()).visitForFsmConversion(this, child);
	}*/

	@Override
	protected final void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedEnter(parent, child);
		ScribDel del = child.del();
		if (del instanceof RPDel)
		{
			((RPDel) child.del()).enterIndexVarCollection(parent, child, this);
		}
	}
	
	@Override
	protected ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribDel del = visited.del();
		if (del instanceof RPDel)
		{
			visited = ((RPDel) visited.del()).leaveIndexVarCollection(parent, child, this, visited);
		}
		return super.inlinedLeave(parent, child, visited);
	}
}
