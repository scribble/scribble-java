package org.scribble.ext.f17.model.endpoint;

import java.util.Collections;

import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;

public class F17EState extends EState
{
	public F17EState()
	{
		super(Collections.emptySet());
	}
	
	// TODO: subfsm/prune/unfair (otherwise this subclass is not needed -- and F17EGraphBuilderUtil)

	@Override
	public int hashCode()
	{
		int hash = 3121;
		hash = 31 * hash + super.hashCode();  // N.B. uses state ID only
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof F17EState))
		{
			return false;
		}
		return super.equals(o);  // Checks canEquals
	}
	
	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof F17EState;
	}
}
