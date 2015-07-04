package org.scribble.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.util.ScribUtil;
import org.scribble.visit.env.UnfoldingEnv;


// Unfolds recursions "directly under" choices (n.b. not continues -- use UnfoldingVisitor to do that on demand)
public class InlinedProtocolUnfolder extends InlinedProtocolVisitor<UnfoldingEnv>
{
	private static final String DUMMY_REC_LABEL = "__";
	
	//private Stack<Boolean> choiceParents = new Stack<>();

	//private Map<RecVar, ProtocolBlock<?>> recs = new HashMap<>();
	private Map<RecVar, Recursion<?>> recs = new HashMap<>();  // Could parameterise recvars to be global/local
	private Set<RecVar> todo = new HashSet<>();
	
	public boolean isTodo(RecVar rv)  // FIXME: rename
	{
		return this.todo.contains(rv);
	}
	
	public InlinedProtocolUnfolder(Job job)
	{
		super(job);
	}
	
	@Override
	//protected InlineProtocolEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	protected UnfoldingEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		//return new InlineProtocolEnv();
		return new UnfoldingEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Recursion)
		{
			//if (!this.choiceParents.isEmpty() && this.choiceParents.peek())
			if (peekEnv().shouldUnfold())
			{
				enter(parent, child);
				ScribNode visited = unfold((Recursion<?>) child);
				return leave(parent, child, visited);
			}
			else
			{
				return super.visit(parent, child);
			}
		}
		/*else if (child instanceof InteractionSeq)
		{
			enter(child, parent);
			ScribNode n = overrideForInteractionSeq((ProtocolBlock<?>) parent, (InteractionSeq<?>) child);
			return leave(parent, child, n);
		}*/
		else
		{
			return super.visit(parent, child);
		}
	}

	private <K extends ProtocolKind> ScribNode unfold(Recursion<K> rec) throws ScribbleException
	{
		RecVar rv = rec.recvar.toName();
		//ProtocolBlock<K> pb = rec.block.clone();
		ProtocolBlock<K> pb = rec.block;  // Clone unnecessary: can keep the original block, apart from any continues to substitute (done in InteractionSeqDel)
		this.todo.add(rv);
		RecVarNode dummy = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, DUMMY_REC_LABEL);
		/*ProtocolBlock<K> block = ScribUtil.checkNodeClass(pb, pb.accept(this));
		Recursion<?> n = (rec.isGlobal())
				? AstFactoryImpl.FACTORY.GRecursion(dummy, (GProtocolBlock) block)
				: AstFactoryImpl.FACTORY.LRecursion(dummy, (LProtocolBlock) block);*/
		ScribNode n = rec.reconstruct(dummy, ScribUtil.checkNodeClass(pb, pb.accept(this)));  // reconstruct makes sense here, actually reconstructing this rec with new label but same block (and keep the same del etc)
		this.todo.remove(rv);
		return n;
	}
	
	/*private ScribNode overrideForInteractionSeq(ProtocolBlock<?> parent, InteractionSeq<?> child) throws ScribbleException
	{
		return ((InteractionSeqDel) child.del()).visitForUnfolding(this, child);
	}*/
	
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
