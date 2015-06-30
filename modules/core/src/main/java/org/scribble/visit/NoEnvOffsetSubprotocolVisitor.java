package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.env.DummyEnv;

public abstract class NoEnvOffsetSubprotocolVisitor extends OffsetSubprotocolVisitor<DummyEnv>
{
	public NoEnvOffsetSubprotocolVisitor(Job job)
	{
		super(job);
	}

	@Override
	protected final DummyEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return DummyEnv.DUMMY;
	}
}
