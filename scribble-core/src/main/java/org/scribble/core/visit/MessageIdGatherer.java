package org.scribble.core.visit;

import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.MessageId;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.Seq;

public class MessageIdGatherer<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeGatherer<K, B, MessageId<?>>
{

	@Override
	public Stream<MessageId<?>> visitDirectedInteraction(
			DirectedInteraction<K, B> n)
	{
		return Stream.of(n.msg.getId());
	}
}
