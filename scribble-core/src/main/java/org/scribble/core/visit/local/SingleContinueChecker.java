package org.scribble.core.visit.local;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeAggNoEx;

// Return true iff this LType is "equivalent" to a single "continue X", where X is in rvs
// Would be an "InlinedAgg"
public class SingleContinueChecker extends STypeAggNoEx<Local, LSeq, Boolean>
{
	private Set<RecVar> rvs;

	public SingleContinueChecker(Set<RecVar> rvs)
	{
		this.rvs = Collections.unmodifiableSet(rvs);
	}

	@Override
	public Boolean unit(SType<Local, LSeq> n)
	{
		return false;
	}

	@Override
	public Boolean agg(SType<Local, LSeq> n, Stream<Boolean> bs)
	{
		return bs.allMatch(x -> x);
	}
	
	@Override
	public Boolean visitContinue(Continue<Local, LSeq> n)
	{
		return rvs.contains(n.recvar);  // Single continue for an "outer" recvar caught later on
	}

	@Override
	public Boolean visitChoice(Choice<Local, LSeq> n)
	{
		return agg(n, n.blocks.stream().map(x -> x.aggregate(this)));
	}

	@Override
	public <N extends ProtocolName<Local>> Boolean visitDo(Do<Local, LSeq, N> n)
	{
		//return (Boolean) InlinedVisitor1.super.visitDo(n);
		//return (Boolean) super.visitDo(n);  // CHECKME: resolves to the extends super, even with a default i/f implementation ?
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}

	@Override
	public Boolean visitRecursion(Recursion<Local, LSeq> n)
	{
		Set<RecVar> tmp = new HashSet<>(this.rvs);
		tmp.add(n.recvar);
		return n.body.aggregate(new SingleContinueChecker(tmp));
	}

	@Override
	public Boolean visitSeq(LSeq n)
	{
		return n.elems.size() == 1 && ((LType) n.elems.get(0)).aggregate(this);
	}
}
