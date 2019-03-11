package org.scribble.lang.global;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.InteractionSeq;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.job.ScribbleException;
import org.scribble.lang.SType;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Seq;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.LSeq;
import org.scribble.lang.local.LSkip;
import org.scribble.lang.local.LType;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public class GSeq extends Seq<Global> implements GType
{
	// GInteractionSeq or GBlock better as source?
	public GSeq(InteractionSeq<Global> source,
			List<? extends SType<Global>> elems)
	{
		super(source, elems);
	}

	@Override
	public GSeq reconstruct(InteractionSeq<Global> source,
			List<? extends SType<Global>> elems)
	{
		return new GSeq(source, elems);
	}

	@Override
	public GSeq substitute(Substitutions<Role> subs)
	{
		return (GSeq) super.substitute(subs);
	}

	@Override
	public GSeq getInlined(STypeInliner i)// , Deque<SubprotoSig> stack)
	{
		GInteractionSeq source = getSource(); // CHECKME: or empty source?
		List<SType<Global>> elems = new LinkedList<>();
		for (SType<Global> e : this.elems)
		{
			SType<Global> e1 = e.getInlined(i);// , stack);
			if (e1 instanceof GSeq)
			{
				elems.addAll(((GSeq) e1).elems); // Inline GSeq's returned by
																					// GDo::getInlined
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public GSeq unfoldAllOnce(STypeUnfolder<Global> u)
	{
		GInteractionSeq source = getSource();
		List<SType<Global>> elems = new LinkedList<>();
		for (SType<Global> e : this.elems)
		{
			SType<Global> e1 = e.unfoldAllOnce(u);
			if (e1 instanceof Seq<?>)
			{
				elems.addAll(((Seq<Global>) e1).elems);
			}
			else
			{
				elems.add(e1);
			}
		}
		return reconstruct(source, elems);
	}

	@Override
	public LSeq project(Role self)
	{
		List<LType> elems = this.elems.stream().map(x -> ((GType) x).project(self))
				.filter(x -> !x.equals(LSkip.SKIP))
				.collect(Collectors.toList());
		return new LSeq(null, elems);  
				// Empty seqs converted to LSkip by GChoice/Recursion projection
				// And a WF top-level protocol cannot produce empty LSeq
				// So a projection never contains an empty LSeq -- i.e., "empty choice/rec" pruning unnecessary
	}

	@Override
	public Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException
	{
		for (GType elem : getElements())
		{
			enabled = elem.checkRoleEnabling(enabled);
		}
		return enabled;
	}

	@Override
	public Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException
	{
		for (GType elem : getElements())
		{
			enablers = elem.checkExtChoiceConsistency(enablers);
		}
		return enablers;
	}

	@Override
	public List<GType> getElements()
	{
		return this.elems.stream().map(x -> (GType) x).collect(Collectors.toList());
	}

	@Override
	public GInteractionSeq getSource()
	{
		return (GInteractionSeq) super.getSource();
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
		if (!(o instanceof GSeq))
		{
			return false;
		}
		return super.equals(o); // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof GSeq;
	}
}
