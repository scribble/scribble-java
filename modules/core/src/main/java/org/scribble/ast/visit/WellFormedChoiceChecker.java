package org.scribble.ast.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.visit.env.WellFormedChoiceEnv;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.util.ScribbleException;

public class WellFormedChoiceChecker extends EnvVisitor<WellFormedChoiceEnv>
{
	public WellFormedChoiceChecker(Job job)
	{
		super(job);
	}

	@Override
	protected WellFormedChoiceEnv makeRootProtocolDeclEnv(
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
			ProtocolDecl<? extends ProtocolKind> pd)
	{
		//return new WellFormedChoiceEnv(getJobContext(), getModuleDelegate());
		return new WellFormedChoiceEnv();
	}
	
	@Override
	//protected WellFormedChoiceChecker envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	protected void envEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//return this;
		/*WellFormedChoiceChecker checker = (WellFormedChoiceChecker) super.envEnter(parent, child);
		return (WellFormedChoiceChecker) child.del().enterWFChoiceCheck(parent, child, checker);*/
		super.envEnter(parent, child);
		child.del().enterWFChoiceCheck(parent, child, this);
	}
	
	@Override
	//protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor<WellFormedChoiceEnv> nv, ModelNode visited) throws ScribbleException
	protected ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		//return visited;
		/*visited = visited.del().leaveWFChoiceCheck(parent, child, (WellFormedChoiceChecker) nv, visited);
		visited = super.envLeave(parent, child, nv, visited);
		return visited;*/
		visited = visited.del().leaveWFChoiceCheck(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}
}
