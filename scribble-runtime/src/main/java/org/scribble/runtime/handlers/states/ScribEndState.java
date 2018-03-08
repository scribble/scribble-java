package org.scribble.runtime.handlers.states;

abstract public class ScribEndState extends ScribState
{
	//public ScribEndState()
	public ScribEndState(String name)
	{
		//super("End");
		super(name);
	}

	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof ScribEndState;
	}
}
