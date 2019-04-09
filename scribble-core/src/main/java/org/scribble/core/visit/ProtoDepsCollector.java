package org.scribble.core.visit;

import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Seq;

// Result should not contain duplicates (i.e., due to Choice/Seq)
// Result does not necessarily contain root proto (protodecl is not an SType), but may do so via dependencies
public class ProtoDepsCollector<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeGather<K, B, ProtocolName<K>>
{

	@Override
	public Stream<ProtocolName<K>> visitChoice(Choice<K, B> n)
	{
		return super.visitChoice(n).distinct();
	}

	@Override
	public Stream<ProtocolName<K>> visitSeq(Seq<K, B> n)
	{
		return super.visitSeq(n).distinct();
	}

	@Override
	public <N extends ProtocolName<K>> Stream<ProtocolName<K>> visitDo(
			Do<K, B, N> n)
	{
		return Stream.of(n.proto);
	}
}
