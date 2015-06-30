package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.env.InlinedWFChoiceEnv;

public class InlinedWFChoiceChecker extends InlinedProtocolVisitor<InlinedWFChoiceEnv>
{
	public InlinedWFChoiceChecker(Job job)
	{
		super(job);
	}

	@Override
	protected InlinedWFChoiceEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new InlinedWFChoiceEnv();
	}
	
	@Override
	protected void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedProtocolEnter(parent, child);
		child.del().enterInlinedWFChoiceCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveInlinedWFChoiceCheck(parent, child, this, visited);
		return super.inlinedProtocolLeave(parent, child, visited);
	}
}
