package org.scribble.ext.f17.ast.local.action;

import java.util.Arrays;
import java.util.Collections;

import org.scribble.ext.f17.ast.F17AstAction;
import org.scribble.sesstype.name.Role;


public abstract class F17LAction extends F17AstAction
{
	public final Role self;
	public final Role peer;

	public F17LAction(Role self, Role peer)  // local subj == peer
	{
		super(Arrays.asList(new Role[] { peer }), Collections.emptyList());
		this.self = self;
		this.peer = peer;
	}
	
	public abstract F17LAction toDual();
	
	public boolean isOutput()
	{
		return false;
	}
	
	public boolean isInput()
	{
		return false;
	}	

	@Override
	public String toString()
	{
		return this.self + ":" + this.peer;
	}

	@Override
	public int hashCode()
	{
		int hash = 41;
		hash = 31 * hash + super.hashCode();  // does peer
		hash = 31 * hash + this.self.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof F17LAction))
		{
			return false;
		}
		F17LAction them = (F17LAction) obj;
		return super.equals(obj)  // super does canEquals; also checks peer
				&& this.self.equals(them.self);  // N.B. considering self for local type equality
	}

	protected abstract boolean canEquals(Object o);
}
