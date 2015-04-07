package org.scribble2.model.visit;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.local.LocalInteractionNode;
import org.scribble2.model.local.LocalInteractionSequence;
import org.scribble2.model.local.LocalProtocolBlock;
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
		// FIXME: move to LocalInteractionSequence
		if (child instanceof LocalInteractionSequence)
		{
			/*Projector proj = (Projector) enter(parent, child);
			ModelNode visited = visitForProjection((Module) parent, (GlobalProtocolDecl) child);
			return leave(parent, child, proj, visited);*/
			ReachabilityChecker checker = (ReachabilityChecker) enter(parent, child);
			ModelNode visited = visitForReachabilityChecking((LocalProtocolBlock) parent, (LocalInteractionSequence) child);
			return leave(parent, child, checker, visited);
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	// Replaces visitChildrenInSubprotocols for the LocalInteractionSequence 
	private LocalInteractionSequence visitForReachabilityChecking(LocalProtocolBlock parent, LocalInteractionSequence child) throws ScribbleException
	{
		//List<T> actions = visitChildListWithClassCheck(this, this.actions, nv);  // OK to require all nodes to keep the same class? Maybe better to leave abstract and implement in the global/local subclasses
		List<LocalInteractionNode> visited = new LinkedList<>();
		for (LocalInteractionNode li : child.actions)
		{
			ReachabilityEnv re = peekEnv();
			if (!re.isExitable())
			{
				throw new ScribbleException("Bad sequence to: " + li);
			}
			//visited.add((LocalInteractionNode) li.visitChildrenInSubprotocols(this));
			visited.add((LocalInteractionNode) li.visit(this));
		}
		//return reconstruct(this.ct, actions);
		return child;
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
