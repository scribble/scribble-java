package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.visit.env.DummyEnv;

public abstract class NoEnvSubprotocolVisitor extends SubprotocolVisitor<DummyEnv>
{
	public NoEnvSubprotocolVisitor(Job job)
	{
		super(job);
	}

	@Override
	protected final DummyEnv makeRootProtocolDeclEnv(ProtocolDecl<?> pd)
	{
		return DummyEnv.DUMMY;
	}
}
