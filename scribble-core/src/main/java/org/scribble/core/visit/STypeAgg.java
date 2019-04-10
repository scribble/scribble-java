package org.scribble.core.visit;

import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;

// Visitor could be refactored as an Agg, but not worth it?  (T = List<SType>, unit = n.getChildren(), agg = reconstruct)
// (Could also do T = SType, unit = n and if agg method extended to take n as a param, for n-sensitive agg, e.g., Choice)
// Another factor is about throws ScribException
public abstract class STypeAgg<K extends ProtocolKind, B extends Seq<K, B>, T>
{
	// Internal use
	protected abstract T unit(SType<K, B> n);

	// Internal use
	protected abstract T agg(SType<K, B> n, Stream<T> ts);  // Cf. generic varargs, heap pollution issue
	
	// SType return for extensibility/flexibility
	public T visitContinue(Continue<K, B> n)
	{
		return agg(n, Stream.of(unit(n)));
	}

	public T visitChoice(Choice<K, B> n)
	{
		return agg(n, n.blocks.stream().map(x -> x.aggregate(this)));
	}

	public T visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		return agg(n, Stream.of(unit(n)));
	}

	public T visitDisconnect(DisconnectAction<K, B> n)
	{
		return agg(n, Stream.of(unit(n)));
	}

	public <N extends ProtocolName<K>> T visitDo(Do<K, B, N> n)
	{
		return agg(n, Stream.of(unit(n)));
	}

	public T visitRecursion(Recursion<K, B> n)
	{
		return agg(n, Stream.of(n.body.aggregate(this)));
	}

	// "Hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	public T visitSeq(B n)
	{
		return agg(n, n.elems.stream().map(x -> x.aggregate(this)));
	}
}




















/*@FunctionalInterface
interface STypeVisitorFunction<K extends ProtocolKind, B extends Seq<K, B>, R extends Stream<?>>
{
	R f(SType<K, B> n, STypeVisitor<K, B> v);
}*/
