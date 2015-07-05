package org.scribble.del;

import org.scribble.ast.Do;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.JobContext;
import org.scribble.visit.ProtocolDefInliner;

public abstract class DoDel extends SimpleInteractionNodeDel
{
	public DoDel()
	{

	}

	@Override
	public Do<?> leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		JobContext jcontext = builder.getJobContext();
		ModuleContext mcontext = builder.getModuleContext();
		Do<?> doo = (Do<?>) visited;
		ProtocolName<?> pn = doo.getTargetFullProtocolName(builder.getModuleContext());
		doo.roles.getRoles().stream().forEach((r) -> addProtocolDependency(builder, r, pn, doo.getTargetRoleParameter(jcontext, mcontext, r)));
		return doo;
	}

	protected abstract void addProtocolDependency(ContextBuilder builder, Role self, ProtocolName<?> proto, Role target);

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		super.enterProtocolInlining(parent, child, builder);
		if (!builder.isCycle())
		{
			SubprotocolSig subsig = builder.peekStack();  // SubprotocolVisitor has already entered subprotocol
			builder.setRecVar(subsig);
		}
	}
}
