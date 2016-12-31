package org.scribble.ext.f17.ast.global.action;

import java.util.List;
import java.util.Set;

import org.scribble.ext.f17.ast.F17AstAction;
import org.scribble.sesstype.name.Role;


public abstract class F17GAction extends F17AstAction
{
	public final Role src;  // Not really suitable for F17GDisconnect
	
	public F17GAction(Role src, List<Role> subjs, List<Role> objs)
	{
		super(subjs, objs);
		this.src = src;  // this.src is "first" subj
	}
	
	public abstract Set<Role> getRoles();  // For projection
	
	@Override
	public String toString()
	{
		return this.src.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + super.hashCode();
		result = prime * result + this.src.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17GAction))
		{
			return false;
		}
		return super.equals(obj);
	}

	protected abstract boolean canEquals(Object o);
}
