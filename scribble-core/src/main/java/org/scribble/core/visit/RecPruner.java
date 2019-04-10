package org.scribble.core.visit;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;

// Assumes no shadowing (e.g., use after inlining recvar disamb)
public class RecPruner<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeVisitorNoThrow<K, B>
{
	@Override
	public SType<K, B> visitRecursion(Recursion<K, B> n)
	{
		// Assumes no shadowing (e.g., use after SType#getInlined recvar disamb)
		Set<RecVar> rvs = n.body.gather(new RecVarGatherer<K, B>()::visit)
				.collect(Collectors.toSet());
		return rvs.contains(n.recvar)
				? n
				: n.body;  // i.e., return a Seq, to be "inlined" by Seq.pruneRecs -- N.B. must handle empty Seq case
	}

	@Override
	public B visitSeq(B n)
	{
		List<SType<K, B>> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			SType<K, B> e1 = (SType<K, B>) e.visitNoThrow(this);
			if (e1 instanceof Seq<?, ?>)  // cf. visitRecursion
			{
				elems.addAll(((Seq<K, B>) e1).getElements());  // Handles empty Seq case
			}
			else
			{
				elems.add(e1);
			}
		}
		return n.reconstruct(n.getSource(), elems);
	}
}
