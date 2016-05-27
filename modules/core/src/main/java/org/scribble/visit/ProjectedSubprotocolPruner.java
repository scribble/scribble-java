package org.scribble.visit;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.DummyProjectionRoleNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ModuleDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.env.ChoiceUnguardedSubprotocolEnv;

// Cf. InlinedProtocolUnfolder
//public class ProjectedSubprotocolPruner extends SubprotocolVisitor<ProjectedSubprotocolPruningEnv>
public class ProjectedSubprotocolPruner extends ModuleContextVisitor
{
	public static final String DUMMY_REC_LABEL = "__";
	
	private Map<RecVar, Recursion<?>> recs = new HashMap<>();  // Could parameterise recvars to be global/local
	private Set<RecVar> recsToUnfold = new HashSet<>();
	
	public ProjectedSubprotocolPruner(Job job)
	{
		super(job);
	}
	
	public boolean shouldUnfoldForUnguardedRec(RecVar rv)
	{
		return this.recsToUnfold.contains(rv);
	}
	
	/*// Unguarded continues directly under choices need to be unfolded to make current graph building work (continue side effects GraphBuilder state to re-set entry to rec state -- which makes output dependent on choice block order, and can attach subsequent choice paths onto the rec state instead of the original choice state)
	// Maybe fix this problem inside graph building rather than unfolding here (this unfolding is conservative -- not always needed for every unguarded continue) -- depends if it helps any other inlined passes?
	public boolean isContinueUnguarded(RecVar rv)
	{
		return peekEnv().shouldPrune();
	}
	
	@Override
	protected ProjectedSubprotocolPruningEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ProjectedSubprotocolPruningEnv();
	}*/

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LProtocolBlock && parent instanceof LChoice)
		{
			LChoice lc = (LChoice) parent;
			LProtocolBlock lb = (LProtocolBlock) child;
			LInteractionSeq lis = lb.getInteractionSeq();
			List<LInteractionNode> ins = lis.getInteractions();
			if (ins.get(0) instanceof LDo)  // Unlike GRecursion.prune, to-prune "do" could be followed by a continuation?
			{
		LDo ld = (LDo) ins.get(0);
		LProtocolDecl lpd = ld.getTargetProtocolDecl(getJobContext(), getModuleContext());
		
		//System.out.println("111: " + ld);
		
		ChoiceUnguardedSubprotocolChecker checker = new ChoiceUnguardedSubprotocolChecker(getJob(),
				//pruner.getModuleContext());
				((ModuleDel)getJobContext().getModule(ld.proto.toName().getPrefix()).del()).getModuleContext(), lc);

		lpd.accept(checker);
		
			ChoiceUnguardedSubprotocolEnv env = (ChoiceUnguardedSubprotocolEnv) lpd.def.block.del().env();
		
				//if (checker.SHOULD_PRUNE)
				if ((lc.subj instanceof DummyProjectionRoleNode) &&  env.subjs.isEmpty())
				{
					//System.out.println("GGG: " + ld + ", " + env.subjs);
					return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Collections.emptyList()));
				}
			}
		}
		return super.visit(parent, child);
		/*}
		else
		{
			ScribNode visited = super.visit(parent, child);
			if (visited instanceof ProtocolDecl<?>)
			{
				ProtocolDecl<?> pd = (ProtocolDecl<?>) visited;
				/*getJob().debugPrintln("\n[DEBUG] Unfolded inlined protocol "
							+ pd.getFullMemberName(getJobContext().getModule(getModuleContext().root)) + ":\n"
							+ ((ProtocolDefDel) pd.def.del()).getInlinedProtocolDef());* /
			}
			return visited;
		}*/
	}

	// Not doing the actual unfolding here: replace the rec with a dummy (i.e. alpha the original rec to another unused lab) and will do any actual unfolding inside the recursive child accept (upon Continue)
	private <K extends ProtocolKind> ScribNode unfold(Recursion<K> rec) throws ScribbleException
	{
		RecVar rv = rec.recvar.toName();
		ProtocolBlock<K> pb = rec.block;
				// Clone unnecessary: can visit the original block, apart from any continues to substitute (done in InteractionSeqDel)
		this.recsToUnfold.add(rv);
		/*RecVarNode dummy = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, DUMMY_REC_LABEL);
		//RecVarNode dummy = rec.recvar.clone();
		ScribNode n = rec.reconstruct(dummy, ScribUtil.checkNodeClassEquality(pb, pb.accept(this)));  // Returning a rec because it needs to go into the InteractionSeq
				// reconstruct makes sense here, actually reconstructing this rec with new label but same block (and keep the same del etc)*/
		InteractionSeq<K> seq = pb.getInteractionSeq();
		ScribNode n = seq.accept(this); 
		this.recsToUnfold.remove(rv);
		return n;
	}
	
	@Override
	//protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//super.subprotocolEnter(parent, child);
		super.enter(parent, child);
		child.del().enterProjectedSubprotocolPruning(parent, child, this);
	}
	
	@Override
	//protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProjectedSubprotocolPruning(parent, child, this, visited);
		//return super.subprotocolLeave(parent, child, visited);
		return super.leave(parent, child, visited);
	}
	
	public Recursion<?> getRecVar(RecVar recvar)
	{
		return this.recs.get(recvar);
	}

	public void setRecVar(RecVar recvar, Recursion<?> rec) throws ScribbleException
	{
		ProtocolBlock<?> b = (ProtocolBlock<?>) rec.getBlock().accept(this);
		RecVarNode clone = rec.recvar.clone();
		if (rec.getKind() == Global.KIND)
		{
			rec = ((GRecursion) rec).reconstruct(clone, (GProtocolBlock) b);
		}
		else
		{
			rec = ((LRecursion) rec).reconstruct(clone, (LProtocolBlock) b);
		}
		this.recs.put(recvar, rec);
	}

	public void removeRecVar(RecVar recvar)
	{
		this.recs.remove(recvar);
	}
}
