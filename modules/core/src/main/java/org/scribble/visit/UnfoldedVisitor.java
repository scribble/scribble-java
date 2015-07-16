package org.scribble.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Continue;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.env.Env;

// "Unfolds" each recursion once (by reentering the original rec ast) on reaching a continue -- subclass should manually keep track of when to "stop" visiting, as visiting the "unfolding" will eventually reach the same continue (e.g. an unguarded choice-continue)
public abstract class UnfoldedVisitor<E extends Env<?>> extends InlinedProtocolVisitor<E>
{
	private Map<RecVar, ProtocolBlock<?>> recs = new HashMap<>();
	private Set<RecVar> unfolded = new HashSet<>();  // FIXME: recvar shadowing -- Set<Stack<RecVar>>
	
	public UnfoldedVisitor(Job job)
	{
		super(job);
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		enter(parent, child);
		ScribNode visited = visitForUnfolded(parent, child);
		return leave(parent, child, visited);
	}

	protected ScribNode visitForUnfolded(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Continue)
		{
			Continue<?> cont = (Continue<?>) child;
			RecVar rv = cont.recvar.toName();
			if (!this.unfolded.contains(rv))
			{
				this.unfolded.add(rv);
				// N.B. visiting the seq child of the block, to continue visiting under the existing env contexts; also visitChildren, not accept (so not doing enter/exit for the seq)
				// Also not returning the seq, just the original continue (cf. do visiting)
				this.recs.get(rv).seq.visitChildren(this);  // FIXME: ok to visit the same AST? any problems with dels/envs? -- maybe do proper equals/hashCode for AST classes
				this.unfolded.remove(rv);
				return cont;
			}
		}
		return super.visitInlinedProtocol(parent, child);  // Not super.visit because that does enter/exit
	}
	
	@Override
	protected final void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedProtocolEnter(parent, child);
		if (child instanceof Recursion)
		{
			Recursion<?> rec = (Recursion<?>) child;
			RecVar rv = rec.recvar.toName();
			if (!this.recs.containsKey(rv))
			{
				this.recs.put(rv, rec.block);
			}
		}
		unfoldedEnter(parent, child);
	}
	
	@Override
	protected final ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribNode n = unfoldedLeave(parent, child, visited);
		if (child instanceof Recursion)
		{
			Recursion<?> rec = (Recursion<?>) child;
			RecVar rv = rec.recvar.toName();
			if (this.recs.containsKey(rv))
			{
				this.recs.remove(rv);
			}
		}
		return super.inlinedProtocolLeave(parent, child, n);
	}

	protected void unfoldedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		
	}

	protected ScribNode unfoldedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}
}
