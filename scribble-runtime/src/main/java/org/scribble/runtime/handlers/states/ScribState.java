package org.scribble.runtime.handlers.states;

import java.util.HashMap;
import java.util.Map;

import org.scribble.type.name.Op;

abstract public class ScribState
{
	public final String name;

	//public final Map<Op, ScribState> succs = new HashMap<>(); // FIXME
	public final Map<Op, String> succs = new HashMap<>();

	public ScribState(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ScribState))
		{
			return false;
		}
		ScribState n = (ScribState) o;
		return n.canEqual(this) && this.name.equals(n.name);
	}
	
	protected abstract boolean canEqual(Object o);

	@Override
	public int hashCode()
	{
		int hash = 7907;
		hash = 31 * hash + this.name.hashCode();
		return hash;
	}
}
