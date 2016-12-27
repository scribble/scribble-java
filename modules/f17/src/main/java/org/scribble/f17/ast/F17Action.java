package org.scribble.f17.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.scribble.sesstype.name.Role;


public abstract class F17Action
{
	public final Set<Role> subjs;
	
	public F17Action(Role... rs)
	{
		this.subjs = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(rs)));
	}
	
	public Set<Role> getSubjects()
	{
		return this.subjs;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.subjs.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17Action))
		{
			return false;
		}
		F17Action them = (F17Action) obj;
		return them.canEquals(this) && this.subjs.equals(them.subjs);
	}

	protected abstract boolean canEquals(Object o);
}
