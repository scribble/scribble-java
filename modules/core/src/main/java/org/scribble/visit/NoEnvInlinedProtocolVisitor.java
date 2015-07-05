package org.scribble.visit;

import org.scribble.visit.env.DummyEnv;

public abstract class NoEnvInlinedProtocolVisitor extends InlinedProtocolVisitor<DummyEnv>
{
	public NoEnvInlinedProtocolVisitor(Job job)
	{
		super(job);
	}
}
