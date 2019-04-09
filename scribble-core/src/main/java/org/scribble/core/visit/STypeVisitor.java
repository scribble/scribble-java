package org.scribble.core.visit;

import java.util.List;
import java.util.stream.Collectors;

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

public abstract class STypeVisitor<K extends ProtocolKind, B extends Seq<K, B>>
{
	// SType return for extensibility/flexibility
	public SType<K, B> visitContinue(Continue<K, B> n)
	{
		//return n.reconstruct(n.getSource(), n.recvar);
		return n;
	}

	public SType<K, B> visitChoice(Choice<K, B> n)
	{
		List<B> blocks = n.blocks.stream()
				.map(x -> x.visitWith((STypeVisitor<K, B>) this))  // FIXME
				.collect(Collectors.toList());
		return n.reconstruct(n.getSource(), n.subj, blocks);
	}

	public SType<K,B> visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		//return n.reconstruct(n.getSource(), n.msg, n.src, n.dst);
		return n;
	}

	public SType<K, B> visitDisconnect(DisconnectAction<K, B> n)
	{
		//return n.reconstruct(n.getSource(), n.left, n.right);
		return n;
	}

	public <N extends ProtocolName<K>> SType<K, B> visitDo(Do<K, B, N> n)
	{
		//return n.reconstruct(n.getSource(), n.proto, n.roles, n.args);
		return n;
	}

	public SType<K, B> visitRecursion(Recursion<K, B> n)
	{
		B body = n.body.visitWith((STypeVisitor<K, B>) this);  // FIXME
		return n.reconstruct(n.getSource(), n.recvar, body);
	}

	// "Hardcoded" to B (cf. Seq, or SType return) -- this visitor pattern depends on B for Choice/Recursion/etc reconstruction
	// This means a Visitor that needs to restructure a Seq should handle this within visitSeq
	// E.g., Seq "injection" by inlining and unfolding
	// For this purpose, visited children passed "directly" instead of via a reconstruction (cf. above methods)
	public B visitSeq(B n)
	{
		List<SType<K, B>> elems = n.elems.stream()
				.map(x -> x.visitWith((STypeVisitor<K, B>) this))  // FIXME
				.collect(Collectors.toList());
		return n.reconstruct(n.getSource(), elems);
	}
}




















/*@FunctionalInterface
interface STypeVisitorFunction<K extends ProtocolKind, B extends Seq<K, B>, R extends Stream<?>>
{
	R f(SType<K, B> n, STypeVisitor<K, B> v);
}*/
