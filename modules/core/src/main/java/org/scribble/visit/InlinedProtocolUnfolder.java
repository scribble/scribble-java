package org.scribble.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.InteractionSeqDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.env.InlineProtocolEnv;

public class InlinedProtocolUnfolder extends InlinedProtocolVisitor<InlineProtocolEnv>
{
	private Stack<Boolean> choiceParents = new Stack<>();

	//private Map<RecVar, ProtocolBlock<?>> recs = new HashMap<>();
	private Map<RecVar, Recursion<?>> recs = new HashMap<>();
	private Set<RecVar> todo = new HashSet<>();
	
	public void pushChoiceParent()
	{
		this.choiceParents.push(true);
	}
	
	public void unsetChoiceParent()
	{	
		if (!this.choiceParents.isEmpty())
		{
			this.choiceParents.pop();
			this.choiceParents.push(false);
		}
	}

	public void popChoiceParent()
	{
		this.choiceParents.pop();
	}
	
	public boolean isTodo(RecVar rv)  // FIXME: rename
	{
		return this.todo.contains(rv);
	}
	
	public InlinedProtocolUnfolder(Job job)
	{
		super(job);
	}
	
	@Override
	protected InlineProtocolEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new InlineProtocolEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Recursion)
		{
			if (!this.choiceParents.isEmpty() && this.choiceParents.peek())
			{
				enter(parent, child);
				ScribNode visited = foo(child);
				return leave(parent, child, visited);
			}
			else
			{
				return super.visit(parent, child);
			}
		}
		else if (child instanceof InteractionSeq)
		{
			enter(child, parent);
			ScribNode n = overrideForInteractionSeq((ProtocolBlock<?>) parent, (InteractionSeq<?>) child);
			return leave(parent, child, n);
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	private <K extends ProtocolKind> ScribNode foo(ScribNode child) throws ScribbleException
	{
		Recursion<K> gr = (Recursion<K>) child;
		RecVar rv = gr.recvar.toName();
		//ProtocolBlock<?> pb = getRecVar(rv);
		ProtocolBlock<K> pb = gr.block;
		this.todo.add(rv);
		RecVarNode dummy = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND,"DUMMY");
		//ScribNode n = gr.reconstruct(dummy, (ProtocolBlock<K>) pb.accept(this));  // FIXME: returning block -- need to make a dummy global/local recursion (or choice etc) to contain it
		ScribNode n = gr.reconstruct(dummy, (ProtocolBlock<K>) pb.visitChildren(this));  // FIXME: returning block -- need to make a dummy global/local recursion (or choice etc) to contain it
		this.todo.remove(rv);
		return n;
	}
	
	private ScribNode overrideForInteractionSeq(ProtocolBlock<?> parent, InteractionSeq<?> child) throws ScribbleException
	{
		return ((InteractionSeqDel) child.del()).visitForUnfolding(this, child);
	}
	
	@Override
	protected void inlinedProtocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedProtocolEnter(parent, child);
		child.del().enterInlinedProtocolUnfolding(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedProtocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveInlinedProtocolUnfolding(parent, child, this, visited);
		return super.inlinedProtocolLeave(parent, child, visited);
	}
	
	//public ProtocolBlock<?> getRecVar(RecVar recvar)
	public Recursion<?> getRecVar(RecVar recvar)
	{
		return this.recs.get(recvar);
	}

	//public void setRecVar(RecVar recvar, ProtocolBlock<? extends ProtocolKind> pb)
	public void setRecVar(RecVar recvar, Recursion<?> pb)
	{
		this.recs.put(recvar, pb);
	}

	public void removeRecVar(RecVar recvar)
	{
		this.recs.remove(recvar);
	}
}
