package org.scribble.core.visit;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;

public abstract class InlinedVisitor<K extends ProtocolKind, B extends Seq<K, B>>
	extends STypeVisitor<K, B>
{
	@Override
	public final <N extends ProtocolName<K>> SType<K, B> visitDo(Do<K, B, N> n)
	{
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}
}
