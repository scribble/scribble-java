/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactory;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ProtocolDefDel;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;
import org.scribble.visit.env.UnfoldingEnv;

// Statically unfolds unguarded recursions and continues "directly under" choices
// N.B. cf. UnfoldingVisitor "lazily" unfolds every rec once on demand
public class InlinedProtocolUnfolder extends InlinedProtocolVisitor<UnfoldingEnv>
{
	public static final String DUMMY_REC_LABEL = "__";
	
	// NOTE: assumes unique recvars up to this point, i.e. no recvar shadowing (treated internally by protocoldef-inlining) -- unfolding of unguardeds will, however, result in "unfolding shadowing"
	protected Map<RecVar, Recursion<?>> recs = new HashMap<>();  // Could parameterise recvars to be global/local
	private Set<RecVar> recsToUnfold = new HashSet<>();
	
	public InlinedProtocolUnfolder(Job job)
	{
		super(job);
	}
	
	public boolean shouldUnfoldForUnguardedRec(RecVar rv)
	{
		return this.recsToUnfold.contains(rv);
	}
	
	// Unguarded continues directly under choices need to be unfolded to make current graph building work (continue side effects GraphBuilder state to re-set entry to rec state -- which makes output dependent on choice block order, and can attach subsequent choice paths onto the rec state instead of the original choice state)
	// Maybe fix this problem inside graph building rather than unfolding here (this unfolding is conservative -- not always needed for every unguarded continue) -- depends if it helps any other inlined passes?
	public boolean isContinueUnguarded(RecVar rv)
	{
		return peekEnv().shouldUnfold();
	}
	
	@Override
	protected UnfoldingEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new UnfoldingEnv();
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Recursion)
		{
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
		else
		{
			ScribNode visited = super.visit(parent, child);
			if (visited instanceof ProtocolDecl<?>)
			{
				ProtocolDecl<?> pd = (ProtocolDecl<?>) visited;
				this.job.debugPrintln("\n[DEBUG] Unfolded inlined protocol "
							+ pd.getFullMemberName(this.job.getContext().getModule(getModuleContext().root)) + ":\n"
							+ ((ProtocolDefDel) pd.def.del()).getInlinedProtocolDef());
			}
			return visited;
		}
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
	protected void inlinedEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.inlinedEnter(parent, child);
		child.del().enterInlinedProtocolUnfolding(parent, child, this);
	}
	
	@Override
	protected ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveInlinedProtocolUnfolding(parent, child, this, visited);
		return super.inlinedLeave(parent, child, visited);
	}
	
	public Recursion<?> getRecVar(RecVar recvar)
	{
		return this.recs.get(recvar);
	}

	// Maybe possible to revise this algorithm to handle shadowed recs, but currently requires unique recvar names
	public void setRecVar(AstFactory af, RecVar recvar, Recursion<?> rec) throws ScribbleException
	{
		ProtocolBlock<?> block = (ProtocolBlock<?>) rec.getBlock().accept(this);
		RecVarNode rv = rec.recvar.clone(af);
		Recursion<?> unfolded;
		if (rec.getKind() == Global.KIND)
		{
			unfolded = ((GRecursion) rec).reconstruct(rv, (GProtocolBlock) block);
		}
		else
		{
			unfolded = ((LRecursion) rec).reconstruct(rv, (LProtocolBlock) block);
			/*LRecursion clone = (LRecursion) rec.clone(af);  // Try to be more compatible with extensions that have different reconstructs  // FIXME: reconstruct should be "protected"?
			clone.block = (LProtocolBlock) block;             // No: cannot set new block
			unfolded = (LRecursion) clone.del(rec.del());*/
		}
		this.recs.put(recvar, unfolded);
	}

	public void removeRecVar(RecVar recvar)
	{
		this.recs.remove(recvar);
	}
}
