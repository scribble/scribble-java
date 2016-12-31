package org.scribble.ext.f17.ast.global.action;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.scribble.sesstype.name.Role;


public class F17GDisconnect extends F17GAction
{
	public final Role dest;  // Here, src/dest simply means left/right roles
	
	private final Set<Role> roles = new HashSet<>();
	
	public F17GDisconnect(Role src, Role dest)
	{
		super(src, Arrays.asList(new Role[] { src, dest }), Collections.emptyList());  // Both are subjs; src is left ("first")
		this.dest = dest;
		this.roles.add(src);
		this.roles.add(dest);
	}
	
	@Override
	public Set<Role> getRoles()
	{
		//return Collections.unmodifiableSet(new HashSet<Role>() {{ add(src); add(dest); }});
		return this.roles;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "#" + this.dest;
	} 

	@Override
	public int hashCode()
	{
		int hash = 2333;
		hash = 31 * hash + dest.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17GDisconnect))
		{
			return false;
		}
		F17GDisconnect them = (F17GDisconnect) obj;
		return super.equals(obj)  // super does canEquals
				&& this.dest.equals(them.dest);
	}
	
	@Override
	protected boolean canEquals(Object o)
	{
		return o instanceof F17GDisconnect;
	}
}
