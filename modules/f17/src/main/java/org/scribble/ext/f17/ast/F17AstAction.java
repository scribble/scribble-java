package org.scribble.ext.f17.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.scribble.sesstype.name.Role;


public abstract class F17AstAction
{
	public final Set<Role> subjs;
	
	public F17AstAction(Role... rs)
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
		int hash = 43;
		hash = 31 * hash + this.subjs.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (!(obj instanceof F17AstAction))
		{
			return false;
		}
		F17AstAction them = (F17AstAction) obj;
		return them.canEquals(this) && this.subjs.equals(them.subjs);
	}

	protected abstract boolean canEquals(Object o);
}
