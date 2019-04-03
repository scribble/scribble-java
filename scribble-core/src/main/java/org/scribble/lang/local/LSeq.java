package org.scribble.lang.local;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.job.ScribbleException;
import org.scribble.lang.SType;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Seq;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.model.endpoint.EState;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;

public class LSeq extends Seq<Local> implements LType
{
	// GInteractionSeq or GBlock better as source?
	public LSeq(InteractionSeq<Local> source, List<? extends SType<Local>> elems)
	{
		super(source, elems);
	}

	@Override
	public LSeq reconstruct(InteractionSeq<Local> source,
			List<? extends SType<Local>> elems)
	{
		return new LSeq(source, elems);
	}

	@Override
	public RecVar isSingleCont()
	{
		RecVar rv = ((LType) this.elems.get(0)).isSingleCont();
		return (this.elems.size() == 1) ? rv : null;
	}

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		return this.elems.size() == 1
				&& ((LType) this.elems.get(0)).isSingleConts(rvs);
	}

	@Override
	public LSeq substitute(Substitutions subs)
	{
		return (LSeq) super.substitute(subs);
	}

	@Override
	public LSeq pruneRecs()
	{
		List<LType> elems = new LinkedList<>();
		for (SType<Local> e : this.elems)
		{
			LType e1 = (LType) e.pruneRecs();
			if (e1 instanceof LSeq)  // cf. Recursion::pruneRecs
			{
				elems.addAll(((LSeq) e1).getElements());  // Handles empty Seq case
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(getSource(), elems);
	}

	@Override
	public LSeq getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		LInteractionSeq source = getSource();  // CHECKME: or empty source?
		List<SType<Local>> elems = new LinkedList<>();
		for (SType<Local> e : this.elems)
		{
			SType<Local> e1 = e.getInlined(i);//, stack);
			if (e1 instanceof LSeq)
			{
				elems.addAll(((LSeq) e1).elems);  // Inline GSeq's returned by GDo::getInlined
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public LSeq unfoldAllOnce(STypeUnfolder<Local> u)
	{
		LInteractionSeq source = getSource();
		List<SType<Local>> elems = new LinkedList<>();
		for (SType<Local> e : this.elems)
		{
			SType<Local> e1 = e.unfoldAllOnce(u);
			if (e1 instanceof Seq<?>)
			{
				elems.addAll(((Seq<Local>) e1).elems);
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		EState entry = b.getEntry();
		EState exit = b.getExit();
		List<LType> elems = getElements();
		if (elems.isEmpty())
		{
			throw new RuntimeException("Shouldn't get here: " + this);
		}
		for (Iterator<LType> i = getElements().iterator(); i.hasNext(); )
		{
			LType next = i.next();
			if (!i.hasNext())
			{
				b.setExit(exit);
				next.buildGraph(b);
			}
			else
			{
				EState tmp = b.newState(Collections.emptySet());
				b.setExit(tmp);
				next.buildGraph(b);
				b.setEntry(b.getExit());
						// CHECKME: exit may not be tmp, entry/exit can be modified, e.g. continue
			}
		}
		b.setEntry(entry);
	}

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		LType prev = null; 
		LType next = null;
		for (Iterator<LType> i = getElements().iterator(); i.hasNext(); )
		{
			prev = next;
			next = i.next();
			if (!env.isSeqable())
			{
				throw new ScribbleException(
						"Illegal sequence: " + (prev == null ? "" : prev + "\n") + next);
			}
			env = next.checkReachability(env);
		}
		return env;
	}

	@Override
	public List<LType> getElements()
	{
		return this.elems.stream().map(x -> (LType) x).collect(Collectors.toList());
	}

	@Override
	public LInteractionSeq getSource()
	{
		return (LInteractionSeq) super.getSource();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 29;
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
		if (!(o instanceof LSeq))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LSeq;
	}
}
