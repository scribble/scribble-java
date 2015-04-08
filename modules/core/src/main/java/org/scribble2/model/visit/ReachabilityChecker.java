package org.scribble2.model.visit;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.del.local.LocalInteractionSequenceDelegate;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.visit.env.ReachabilityEnv;
import org.scribble2.util.ScribbleException;

public class ReachabilityChecker extends EnvVisitor<ReachabilityEnv>
{
	public ReachabilityChecker(Job job)
	{
		super(job);
	}

	@Override
	protected ReachabilityEnv makeRootProtocolDeclEnv(
			ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		ReachabilityEnv env = new ReachabilityEnv(this.getJobContext(), getModuleDelegate());
		/*for (Role role : pd.roledecls.getRoles())
		{
			env = env.enableRoleForRootProtocolDecl(role);
		}*/
		return env;
	}

	// Following Projector visit pattern -- for overriding base enter/visit/leave pattern
	@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		if (child instanceof LocalInteractionSequence)
		{
			/*Projector proj = (Projector) enter(parent, child);
			ModelNode visited = visitForProjection((Module) parent, (GlobalProtocolDecl) child);
			return leave(parent, child, proj, visited);*/
			ReachabilityChecker checker = (ReachabilityChecker) enter(parent, child);
			ModelNode visited = ((LocalInteractionSequenceDelegate) child.del()).visitForReachabilityChecking(this, (LocalInteractionSequence) child);
			return leave(parent, child, checker, visited);
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	@Override
	protected ReachabilityChecker envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//return child.enterReachabilityCheck(this);
		ReachabilityChecker checker = (ReachabilityChecker) super.envEnter(parent, child);
		return (ReachabilityChecker) child.del().enterReachabilityCheck(parent, child, checker);
	}
	
	@Override
	protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor<ReachabilityEnv> nv, ModelNode visited) throws ScribbleException
	{
		//return visited.leaveReachabilityCheck(this);
		visited = visited.del().leaveReachabilityCheck(parent, child, (ReachabilityChecker) nv, visited);
		return super.envLeave(parent, child, nv, visited);
	}
}
