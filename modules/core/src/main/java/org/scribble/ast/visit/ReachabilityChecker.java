package org.scribble.ast.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.visit.env.ReachabilityEnv;
import org.scribble.del.local.LInteractionSeqDel;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.util.ScribbleException;

public class ReachabilityChecker extends EnvVisitor<ReachabilityEnv>
{
	public ReachabilityChecker(Job job)
	{
		super(job);
	}

	@Override
	protected ReachabilityEnv makeRootProtocolDeclEnv(
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
			ProtocolDecl<? extends ProtocolKind> pd)
	{
		//ReachabilityEnv env = new ReachabilityEnv(this.getJobContext(), getModuleDelegate());
		ReachabilityEnv env = new ReachabilityEnv();
		/*for (Role role : pd.roledecls.getRoles())
		{
			env = env.enableRoleForRootProtocolDecl(role);
		}*/
		return env;
	}

	// Following Projector visit pattern -- for overriding base enter/visit/leave pattern
	@Override
	protected ScribNode visitForSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LInteractionSeq)
		{
			return visitOverrideForLInteractionSequence((LProtocolBlock) parent, (LInteractionSeq) child);
		}
		else
		{
			return super.visitForSubprotocols(parent, child);
		}
	}

	protected LInteractionSeq visitOverrideForLInteractionSequence(LProtocolBlock parent, LInteractionSeq child) throws ScribbleException
	{
		/*Projector proj = (Projector) enter(parent, child);
		ModelNode visited = visitForProjection((Module) parent, (GlobalProtocolDecl) child);
		return leave(parent, child, proj, visited);*/
		/*ReachabilityChecker checker = (ReachabilityChecker) enter(parent, child);
		ModelNode visited = ((LocalInteractionSequenceDelegate) child.del()).visitForReachabilityChecking(checker, (LocalInteractionSequence) child);
		return (LocalInteractionSequence) leave(parent, child, checker, visited);*/
		/*enter(parent, child);
		ModelNode visited = ((LocalInteractionSequenceDelegate) child.del()).visitForReachabilityChecking(this, (LocalInteractionSequence) child);
		return (LocalInteractionSequence) leave(parent, child, visited);*/
		return ((LInteractionSeqDel) child.del()).visitForReachabilityChecking(this, (LInteractionSeq) child);
	}
	

	/*// Following Projector visit pattern -- for overriding base enter/visit/leave pattern
	@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		if (child instanceof LocalInteractionSequence)
		{
			return visitOverrideForLocalInteractionSequence((LocalProtocolBlock) parent, (LocalInteractionSequence) child);
		}
		else
		{
			return super.visit(parent, child);
		}
	}
	
	private LocalInteractionSequence visitOverrideForLocalInteractionSequence(LocalProtocolBlock parent, LocalInteractionSequence child) throws ScribbleException
	{
		/*Projector proj = (Projector) enter(parent, child);
		ModelNode visited = visitForProjection((Module) parent, (GlobalProtocolDecl) child);
		return leave(parent, child, proj, visited);*/
		/*ReachabilityChecker checker = (ReachabilityChecker) enter(parent, child);
		ModelNode visited = ((LocalInteractionSequenceDelegate) child.del()).visitForReachabilityChecking(checker, (LocalInteractionSequence) child);
		return (LocalInteractionSequence) leave(parent, child, checker, visited);* /
		enter(parent, child);
		ModelNode visited = ((LocalInteractionSequenceDelegate) child.del()).visitForReachabilityChecking(this, (LocalInteractionSequence) child);
		return (LocalInteractionSequence) leave(parent, child, visited);
	}*/

	@Override
	//protected ReachabilityChecker envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	protected void envEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//return child.enterReachabilityCheck(this);
		/*ReachabilityChecker checker = (ReachabilityChecker) super.envEnter(parent, child);
		return (ReachabilityChecker) child.del().enterReachabilityCheck(parent, child, checker);*/
		super.envEnter(parent, child);
		child.del().enterReachabilityCheck(parent, child, this);
	}
	
	@Override
	//protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor<ReachabilityEnv> nv, ModelNode visited) throws ScribbleException
	protected ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		//return visited.leaveReachabilityCheck(this);
		/*visited = visited.del().leaveReachabilityCheck(parent, child, (ReachabilityChecker) nv, visited);
		return super.envLeave(parent, child, nv, visited);*/
		visited = visited.del().leaveReachabilityCheck(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}
}
