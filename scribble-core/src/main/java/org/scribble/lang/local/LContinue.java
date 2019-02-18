package org.scribble.lang.local;

import org.scribble.ast.ProtocolKindNode;
import org.scribble.lang.Continue;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

public class LContinue extends Continue<Local> implements LType
{
	public LContinue(//org.scribble.ast.Continue<Local> source, 
			ProtocolKindNode<Local> source,  // Due to inlining, do -> continue
			RecVar recvar)
	{
		super(source, recvar);
	}
	
	@Override
	public LContinue reconstruct(org.scribble.ast.ProtocolKindNode<Local> source,
			RecVar recvar)
	{
		return new LContinue(source, recvar);
	}

	@Override
	public LContinue substitute(Substitutions<Role> subs)
	{
		return (LContinue) super.substitute(subs);
	}

	@Override
	public LContinue getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (LContinue) super.getInlined(i);
	} 

	@Override
	public LRecursion unfoldAllOnce(STypeUnfolder<Local> u)
	{
		return new LRecursion(getSource(), this.recvar,  // CHECKME: Continue (not Recursion) as the source of the unfolding
				(LSeq) u.getRec(this.recvar));  
			
	}
	
	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		//if (b.isUnguardedInChoice())
		{
			b.addContinueEdge(b.getEntry(), this.recvar);
			return;
		}

		/*// ** "Overwrites" previous edge built by send/receive(s) leading to this continue
		Iterator<EState> preds = b.getPredecessors().iterator();
		Iterator<EAction> prevs = b.getPreviousActions().iterator();
		EState entry = b.getEntry();

		Set<List<Object>> removed = new HashSet<>();  
				// HACK: for identical edges, i.e. same pred/prev/succ (e.g. rec X { choice at A { A->B:1 } or { A->B:1 } continue X; })  
				// FIXME: do here, or refactor into GraphBuilder?
				//
				// Because duplicate edges preemptively pruned by ModelState.addEdge, but corresponding predecessors not pruned  
				// FIXME: make uniform
		while (preds.hasNext())
		{
			EState pred = preds.next();
			EAction prev = prevs.next();
			List<Object> tmp = Arrays.asList(pred, prev, entry);
			if (!removed.contains(tmp))
			{
				removed.add(tmp);
				b.removeEdgeFromPredecessor(pred, prev);  // Assumes pred is a predecessor, and removes pred from current predecessors..
			}
			b.addRecursionEdge(pred, prev,
					b.getRecursionEntry(this.recvar));
					// May be repeated for non-det, but OK  // Combine with removeEdgeFromPredecessor?
		}*/
	}
 
	@Override
	public int hashCode()
	{
		int hash = 3457;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LContinue))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LContinue;
	}
}
