package org.scribble.core.visit;

import java.util.stream.Stream;

import org.scribble.core.type.kind.Global;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.global.GSeq;

public class RecVarCollector
		extends STypeCollector<Global, GSeq, GProtocolName, RecVar>
{
	@Override
	public Stream<RecVar> visitContinue
	(Continue<Global, GSeq> n)
	{
		return Stream.of(n.recvar);
	}
}
