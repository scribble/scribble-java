package org.scribble.core.visit;

import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Seq;

public class RoleCollector<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeCollector<K, B, ProtocolName<K>, Role>
{

	@Override
	public Stream<Role> visitChoice(Choice<K, B> n)
	{
		return Stream.of(n.subj);
	}

	@Override
	public Stream<Role> visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		return Stream.of(n.src, n.dst);
	}

	@Override
	public Stream<Role> visitDisconnect(DisconnectAction<K, B> n)
	{
		return Stream.of(n.left, n.right);
	}
}
