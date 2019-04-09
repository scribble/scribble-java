package org.scribble.core.visit;

import java.util.stream.Stream;

import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.Seq;

public class RecVarCollector<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeGather<K, B, RecVar>
{
	@Override
	public Stream<RecVar> visitContinue(Continue<K, B> n)
	{
		return Stream.of(n.recvar);
	}
}
