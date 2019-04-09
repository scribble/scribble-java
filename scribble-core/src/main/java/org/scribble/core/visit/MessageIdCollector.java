package org.scribble.core.visit;

import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.MessageId;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.Seq;

public class MessageIdCollector<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeCollector<K, B, ProtocolName<K>, MessageId<?>>
{

	@Override
	public Stream<MessageId<?>> visitDirectedInteraction(
			DirectedInteraction<K, B> n)
	{
		return Stream.of(n.msg.getId());
	}
}
