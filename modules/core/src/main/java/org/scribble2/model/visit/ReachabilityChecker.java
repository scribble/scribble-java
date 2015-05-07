package org.scribble2.model.visit;

import org.scribble2.model.AbstractProtocolDecl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.del.local.LocalInteractionSequenceDelegate;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalProtocolBlock;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.util.ScribbleException;

public class ReachabilityChecker extends EnvVisitor<ReachabilityEnv>
{
	public ReachabilityChecker(Job job)
	{
		super(job);
	}

	@Override
	protected ReachabilityEnv makeRootProtocolDeclEnv(
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
			AbstractProtocolDecl<? extends ProtocolKind> pd)
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
	protected ModelNode visitForSubprotocols(ModelNode parent, ModelNode child) throws ScribbleException
	{
		if (child instanceof LocalInteractionSequence)
		{
			return visitOverrideForLocalInteractionSequence((LocalProtocolBlock) parent, (LocalInteractionSequence) child);
		}
		else
		{
			return super.visitForSubprotocols(parent, child);
		}
	}

	private LocalInteractionSequence visitOverrideForLocalInteractionSequence(LocalProtocolBlock parent, LocalInteractionSequence child) throws ScribbleException
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
		return ((LocalInteractionSequenceDelegate) child.del()).visitForReachabilityChecking(this, (LocalInteractionSequence) child);
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
	protected void envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//return child.enterReachabilityCheck(this);
		/*ReachabilityChecker checker = (ReachabilityChecker) super.envEnter(parent, child);
		return (ReachabilityChecker) child.del().enterReachabilityCheck(parent, child, checker);*/
		super.envEnter(parent, child);
		child.del().enterReachabilityCheck(parent, child, this);
	}
	
	@Override
	//protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor<ReachabilityEnv> nv, ModelNode visited) throws ScribbleException
	protected ModelNode envLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		//return visited.leaveReachabilityCheck(this);
		/*visited = visited.del().leaveReachabilityCheck(parent, child, (ReachabilityChecker) nv, visited);
		return super.envLeave(parent, child, nv, visited);*/
		visited = visited.del().leaveReachabilityCheck(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}
}
