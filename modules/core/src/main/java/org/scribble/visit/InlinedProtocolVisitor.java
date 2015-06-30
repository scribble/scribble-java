package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.del.global.GProtocolDefDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.env.Env;

public abstract class InlinedProtocolVisitor<T extends Env> extends EnvVisitor<T>
{
	public InlinedProtocolVisitor(Job job)
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

	protected ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof GProtocolDef)
		{
			return visitOverrideForGProtocolDef(parent, (GProtocolDef) child);	// parent is InteractionSequence
		}
		else
		{
			return child.visitChildren(this);  // The base (super) behaviour (could factor it out in ModelVisitor as its own visitor method)
		}
	}
	
	// N.B. results of visiting inlined version are stored back to inlined field, but original AST is unaffected -- so any Env/Del or AST updates to inlined version do not reflect back onto original AST -- a motivation for the original SubprotocolVisitor approach
	private ScribNode visitOverrideForGProtocolDef(ScribNode parent, GProtocolDef gpd) throws ScribbleException
	{
		GProtocolDef inlined = ((GProtocolDefDel) gpd.del()).getInlinedGProtocolDef();
		if (inlined == null)
		{
			throw new RuntimeException("InlineProtocolVisitor error: " + gpd);
		}
		GProtocolDef visited = (GProtocolDef) inlined.visitChildren(this);

		System.out.println("2: " + visited);

		GProtocolDefDel del = (GProtocolDefDel) gpd.del();
		return gpd.del(del.setInlinedGProtocolDef(visited));
	}

	@Override
	protected final void envEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.envEnter(parent, child);
		inlinedProtocolEnter(parent, child);
	}

	@Override
	protected final ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribNode n = inlinedProtocolLeave(parent, child, visited);
		return super.envLeave(parent, child, n);
	}

	protected void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{

	}

	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
