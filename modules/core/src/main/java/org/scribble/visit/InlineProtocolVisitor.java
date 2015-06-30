package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.main.ScribbleException;

public class InlineProtocolVisitor extends ModuleVisitor
{
	public InlineProtocolVisitor(Job job)
	{
		super(job);
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		enter(parent, child);
		ScribNode visited = visitInlinedProtocol(parent, child);
		return leave(parent, child, visited);
	}

	// Subclasses can override this to disable subprotocol visiting
	protected ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof GProtocolDecl)
		{
			return visitOverrideForGProtocolDef(parent, (GProtocolDef) child);	// parent is InteractionSequence
		}
		else
		{
			return child.visitChildren(this);  // The base (super) behaviour (could factor it out in ModelVisitor as its own visitor method)
		}
	}
	
	private ScribNode visitOverrideForGProtocolDef(ScribNode parent, GProtocolDef gpd) throws ScribbleException
	{
		return ((GProtocolDefDel) gpd.del()).getInlinedGProtocolDef().visitChildren(this);
	}

	@Override
	protected final void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		inlinedProtocolEnter(parent, child);
	}

	@Override
	protected final ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribNode n = inlinedProtocolLeave(parent, child, visited);
		return super.leave(parent, child, n);
	}

	protected void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{

	}

	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
