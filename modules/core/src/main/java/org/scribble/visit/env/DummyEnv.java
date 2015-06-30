package org.scribble.visit.env;

public class DummyEnv extends Env
{
	public static final DummyEnv DUMMY = new DummyEnv();

	protected DummyEnv()
	{

	}

	@Override
	protected Env copy()
	{
		return this;
	}

	@Override
	public Env enterContext()
	{
		return this;
	}
}
