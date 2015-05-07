package org.scribble2.model.visit;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.AbstractProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.util.ScribbleException;

public class WellFormedChoiceChecker extends EnvVisitor<WellFormedChoiceEnv>
{
	public WellFormedChoiceChecker(Job job)
	{
		super(job);
	}

	@Override
	protected WellFormedChoiceEnv makeRootProtocolDeclEnv(
			AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		//return new WellFormedChoiceEnv(getJobContext(), getModuleDelegate());
		return new WellFormedChoiceEnv();
	}
	
	@Override
	//protected WellFormedChoiceChecker envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	protected void envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//return this;
		/*WellFormedChoiceChecker checker = (WellFormedChoiceChecker) super.envEnter(parent, child);
		return (WellFormedChoiceChecker) child.del().enterWFChoiceCheck(parent, child, checker);*/
		super.envEnter(parent, child);
		child.del().enterWFChoiceCheck(parent, child, this);
	}
	
	@Override
	//protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor<WellFormedChoiceEnv> nv, ModelNode visited) throws ScribbleException
	protected ModelNode envLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		//return visited;
		/*visited = visited.del().leaveWFChoiceCheck(parent, child, (WellFormedChoiceChecker) nv, visited);
		visited = super.envLeave(parent, child, nv, visited);
		return visited;*/
		visited = visited.del().leaveWFChoiceCheck(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}
}
