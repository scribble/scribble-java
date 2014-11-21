package org.scribble2.model.visit;

import org.scribble2.model.ModelNode;
import org.scribble2.util.ScribbleException;

public class WellFormedChoiceChecker extends SubprotocolVisitor//EnvVisitor
{
	public WellFormedChoiceChecker(Job job)
	{
		super(job);
	}

	@Override
	protected WellFormedChoiceChecker subprotocolEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//return this;
		return (WellFormedChoiceChecker) child.del().enterWFChoiceCheck(parent, child, (WellFormedChoiceChecker) this);
	}
	
	@Override
	protected ModelNode subprotocolLeave(ModelNode parent, ModelNode child, ModelVisitor nv, ModelNode visited) throws ScribbleException
	{
		//return visited;
		return visited.del().leaveWFChoiceCheck(parent, child, (WellFormedChoiceChecker) nv, visited);
	}

	/*@Override
	protected WellFormedChoiceEnv makeRootProtocolDeclEnv(
			ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		WellFormedChoiceEnv env = new WellFormedChoiceEnv(this.getJobContext(), getModuleContext());
		//pd.roledecls.getRoles().forEach((r) -> env.enableRoleForRootProtocolDecl(r));
		for (Role role : pd.roledecls.getRoles())
		{
			env = env.enableRoleForRootProtocolDecl(role);
		}
		return env;
	}
	
	@Override
	public WellFormedChoiceEnv getEnv()
	{
		return (WellFormedChoiceEnv) super.getEnv();
	}*/
}
