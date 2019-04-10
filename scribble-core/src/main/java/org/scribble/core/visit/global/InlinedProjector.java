package org.scribble.core.visit.global;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.job.Job;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.global.GConnect;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.local.LAcc;
import org.scribble.core.type.session.local.LChoice;
import org.scribble.core.type.session.local.LContinue;
import org.scribble.core.type.session.local.LDisconnect;
import org.scribble.core.type.session.local.LRcv;
import org.scribble.core.type.session.local.LRecursion;
import org.scribble.core.type.session.local.LReq;
import org.scribble.core.type.session.local.LSend;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LSkip;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeAggNoEx;
import org.scribble.core.visit.local.SingleContinueChecker;

// Pre: use on inlined (i.e., Do inlined, roles pruned)
public class InlinedProjector extends STypeAggNoEx<Global, GSeq, LType>
{
	public final Job job;
	public final Role self;

	public InlinedProjector(Job job, Role self)
	{
		this.job = job;
		this.self = self;
	}

	@Override
	protected LType unit(SType<Global, GSeq> n)
	{
		throw new RuntimeException("Disregarded for Projector: " + n);
	}

	@Override
	protected LType agg(SType<Global, GSeq> n, Stream<LType> ts)
	{
		throw new RuntimeException("Disregarded for Projector: " + n + " ,, " + ts);
	}

	@Override
	public LType visitChoice(Choice<Global, GSeq> n)
	{
		Role subj = n.subj.equals(self) ? Role.SELF : n.subj;
				// CHECKME: "self" also explcitily used for Do, but implicitly for MessageTransfer, inconsistent?
		List<LSeq> tmp = n.blocks.stream().map(x -> visitSeq(x))
				.filter(x -> !x.isEmpty()).collect(Collectors.toList());
		if (tmp.isEmpty())
		{
			return LSkip.SKIP; // CHECKME: OK, or "empty" choice at subj still important?
		}
		return new LChoice(null, subj, tmp);
	}
	
	@Override
	public LType visitContinue(Continue<Global, GSeq> n)
	{
		return new LContinue(null, n.recvar);
	}

	@Override
	public LType visitDirectedInteraction(DirectedInteraction<Global, GSeq> n)
	{
		/*if (n.src.equals(self) && n.dst.equals(self))
		{
				// CHECKME: already checked?
		}*/
		if (n instanceof GConnect)  // FIXME
		{
			return n.src.equals(self) ? new LReq(null, n.msg, n.dst)
					: n.dst.equals(self)  ? new LAcc(null, n.src, n.msg)
					: LSkip.SKIP;
		}
		else //if (n instanceof GMessageTransfer)
		{
			return n.src.equals(self) ? new LSend(null, n.msg, n.dst)
					: n.dst.equals(self)  ? new LRcv(null, n.src, n.msg)
					: LSkip.SKIP;
		}
	}

	@Override
	public LType visitDisconnect(DisconnectAction<Global, GSeq> n)
	{
		/*if (n.src.equals(self) && n.dst.equals(self))
		{
				// CHECKME: already checked?
		}*/
		return (n.left.equals(self)) ? new LDisconnect(null, n.right)
				: (n.right.equals(self)) ? new LDisconnect(null, n.left)
				: LSkip.SKIP;
	}

	@Override
	public <N extends ProtocolName<Global>> LType visitDo(Do<Global, GSeq, N> n)
	{
		throw new RuntimeException("Unsupported for Do: " + n);
	}

	@Override
	public LType visitRecursion(Recursion<Global, GSeq> n)
	{
		LSeq body = visitSeq(n.body);
		if (body.isEmpty())  // N.B. projection is doing empty-rec pruning
		{
			return LSkip.SKIP;
		}
		Set<RecVar> rvs = new HashSet<>();
		rvs.add(n.recvar);
		if (body.aggregateNoEx(new SingleContinueChecker(rvs)))
		{
			return LSkip.SKIP;
		}
		return new LRecursion(null, n.recvar, body);
	}

	// Param "hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	@Override
	public LSeq visitSeq(GSeq n)
	{
		List<LType> elems = n.elems.stream().map(x -> x.aggregateNoEx(this))
				.filter(x -> !x.equals(LSkip.SKIP)).collect(Collectors.toList());
		return new LSeq(null, elems);  
				// Empty seqs converted to LSkip by GChoice/Recursion projection
				// And a WF top-level protocol cannot produce empty LSeq
				// So a projection never contains an empty LSeq -- i.e., "empty choice/rec" pruning unnecessary
	}
}
