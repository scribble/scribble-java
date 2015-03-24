package org.scribble2.model.visit;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.util.ScribbleException;

public class WellFormedChoiceChecker extends EnvVisitor
{
	public WellFormedChoiceChecker(Job job)
	{
		super(job);
	}

	@Override
	protected WellFormedChoiceEnv makeRootProtocolDeclEnv(
			ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		return new WellFormedChoiceEnv(getJobContext(), getModuleDelegate());
	}
	
	@Override
	protected WellFormedChoiceChecker envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//return this;
		return (WellFormedChoiceChecker) child.del().enterWFChoiceCheck(parent, child, this);
	}
	
	@Override
	protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor nv, ModelNode visited) throws ScribbleException
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

	@Override
	public WellFormedChoiceEnv peekEnv()
	//public Env getEnv()
	{
		return (WellFormedChoiceEnv) super.peekEnv();
	}

	@Override
	public WellFormedChoiceEnv peekParentEnv()
	{
		return (WellFormedChoiceEnv) super.peekParentEnv();
	}
	
	@Override
	public WellFormedChoiceEnv popEnv()
	{
		return (WellFormedChoiceEnv) super.popEnv();
	}
}
