package org.scribble.visit.env;

public class DummyEnv extends Env<DummyEnv>
{
	public static final DummyEnv DUMMY = new DummyEnv();

	protected DummyEnv()
	{

	}

	@Override
	protected DummyEnv copy()
	{
		return this;
	}

	@Override
	public DummyEnv enterContext()
	{
		return this;
	}
}
