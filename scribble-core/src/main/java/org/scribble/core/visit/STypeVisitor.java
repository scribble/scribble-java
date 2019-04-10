package org.scribble.core.visit;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;
import org.scribble.util.ScribException;

// "Instantiates" Agg by implicitly treating Stream as always a singleton Stream of the *reconstructed* node
// Alternative characterisations of STypeVisitor as STypeAgg?
// T = SType, unit = empty, agg = reconstruct, ns = "structural" (i.e, B) children -- issues with generic cast to B inside agg
// T = SType, unit = n.getChildren(), agg = reconstruct, ns = all children -- all children require dynamic casts
// T = B ... ?  cf. Stream<T>, for Choice reconstruct
public abstract class STypeVisitor<K extends ProtocolKind, B extends Seq<K, B>>
	extends STypeAgg<K, B, SType<K, B>>  // T = SType gives reconstruction flexibility/extensibility
{
	@Override
	protected final SType<K, B> unit(SType<K, B> n) throws ScribException
	{
		return n;
	}

	// Should generally disregard agg for STypeVisitors -- pattern is now to manually do reconstruct within visit dispatches
	@Override
	protected final SType<K, B> agg(SType<K, B> n, Stream<SType<K, B>> ns)
			throws ScribException
	{
		return ns.iterator().next();
		
		/*// agg = reconstruct, ns = "structural" (i.e, B) children -- problem with generic casts
		if (n instanceof Continue<?, ?> || n instanceof DirectedInteraction<?, ?>
				|| n instanceof DisconnectAction<?, ?> || n instanceof Do<?, ?, ?>)
		{
			return ns.iterator().next();
		}
		else if (n instanceof Choice<?, ?>)
		{
			Choice<K, B> c = (Choice<K, B>) n;
			return c.reconstruct(c.getSource(), c.subj,
					ns.map(x -> (B) x).collect(Collectors.toList()));
		}
		else if (n instanceof Recursion<?, ?>)
		{
			Recursion<K, B> r = (Recursion<K, B>) n;
			return r.reconstruct(r.getSource(), r.recvar, (B) ns.iterator().next());
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + n + " ,, " + ns);
					// agg should only be called from internal visit dispatch methods, so n's type has already been cased
		}*/
	}

	@Override
	public SType<K, B> visitChoice(Choice<K, B> n) throws ScribException
	{
		List<B> blocks = new LinkedList<>();
		for (B b : n.blocks)
		{
			blocks.add(b.visitWith(this));
		}
		return n.reconstruct(n.getSource(), n.subj, blocks);  // Skipping agg (reconstruction done here)
	}

	@Override
	public SType<K, B> visitRecursion(Recursion<K, B> n) throws ScribException
	{
		B body = n.body.visitWith(this);
		return n.reconstruct(n.getSource(), n.recvar, body);  // Skipping agg (reconstruction done here)
	}

	// "Hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	// This means a Visitor that needs to restructure a Seq should handle this within visitSeq
	// E.g., Seq "injection" by inlining and unfolding
	// For this purpose, visited children passed "directly" instead of via a reconstruction (cf. above methods)
	@Override
	public B visitSeq(B n) throws ScribException
	{
		List<SType<K, B>> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			elems.add(e.visitWith(this));
		}
		return n.reconstruct(n.getSource(), elems);  // N.B. skipping agg (reconstruction done here)
	}
}























/*
	// SType return for extensibility/flexibility
	public SType<K, B> visitContinue(Continue<K, B> n) throws ScribException
	{
		//return n.reconstruct(n.getSource(), n.recvar);
		return n;
	}

	public SType<K, B> visitChoice(Choice<K, B> n) throws ScribException
	{
		List<B> blocks = new LinkedList<>();
		for (B b : n.blocks)
		{
			blocks.add(b.visitWith(this));
		}
		return n.reconstruct(n.getSource(), n.subj, blocks);
	}

	public SType<K, B> visitDirectedInteraction(DirectedInteraction<K, B> n)
			throws ScribException
	{
		//return n.reconstruct(n.getSource(), n.msg, n.src, n.dst);
		return n;
	}

	public SType<K, B> visitDisconnect(DisconnectAction<K, B> n)
			throws ScribException
	{
		//return n.reconstruct(n.getSource(), n.left, n.right);
		return n;
	}

	public <N extends ProtocolName<K>> SType<K, B> visitDo(Do<K, B, N> n)
			throws ScribException
	{
		//return n.reconstruct(n.getSource(), n.proto, n.roles, n.args);
		return n;
	}

	public SType<K, B> visitRecursion(Recursion<K, B> n) throws ScribException
	{
		B body = n.body.visitWith(this);
		return n.reconstruct(n.getSource(), n.recvar, body);
	}
*/


/*@FunctionalInterface
interface STypeVisitorFunction<K extends ProtocolKind, B extends Seq<K, B>, R extends Stream<?>>
{
	R f(SType<K, B> n, STypeVisitor<K, B> v);
}*/
