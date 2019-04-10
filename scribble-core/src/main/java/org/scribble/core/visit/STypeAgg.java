package org.scribble.core.visit;

import java.util.LinkedList;
import java.util.List;
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
import org.scribble.util.ScribException;

public abstract class STypeAgg<K extends ProtocolKind, B extends Seq<K, B>, T>
{
	// Internal use
	// Pre: agg(Stream.of(unit())) = unit()
	protected abstract T unit(SType<K, B> n) throws ScribException;

	// Internal use -- by default, agg applied to all cases (including unit)
	// Pre: agg(Stream.of(unit())) = unit()
	protected abstract T agg(SType<K, B> n, Stream<T> ts) throws ScribException;  // Cf. generic varargs, heap pollution issue
	
	// SType return for extensibility/flexibility
	public T visitContinue(Continue<K, B> n) throws ScribException
	{
		return unit(n);
	}

	public T visitChoice(Choice<K, B> n) throws ScribException
	{
		List<T> blocks = new LinkedList<>();
		for (SType<K, B> e : n.blocks)
		{
			blocks.add(e.aggregate(this));
		}
		return agg(n, blocks.stream());
	}

	public T visitDirectedInteraction(DirectedInteraction<K, B> n)
			throws ScribException
	{
		return unit(n);
	}

	public T visitDisconnect(DisconnectAction<K, B> n) throws ScribException
	{
		return unit(n);
	}

	public <N extends ProtocolName<K>> T visitDo(Do<K, B, N> n)
			throws ScribException
	{
		return unit(n);
	}

	public T visitRecursion(Recursion<K, B> n) throws ScribException
	{
		return agg(n, Stream.of(n.body.aggregate(this)));
	}

	// Param "hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	public T visitSeq(B n) throws ScribException
	{
		List<T> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			elems.add(e.aggregate(this));
		}
		return agg(n, elems.stream());
	}
}




















/*@FunctionalInterface
interface STypeVisitorFunction<K extends ProtocolKind, B extends Seq<K, B>, R extends Stream<?>>
{
	R f(SType<K, B> n, STypeVisitor<K, B> v);
}*/
