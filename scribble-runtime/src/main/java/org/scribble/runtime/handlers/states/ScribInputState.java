package org.scribble.runtime.handlers.states;

import org.scribble.type.name.Role;

abstract public class ScribInputState extends ScribState
{
	public final Role peer;

	public ScribInputState(String name, Role peer)
	{
		super(name);
		this.peer = peer;
	}

	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof ScribInputState;
	}
}
