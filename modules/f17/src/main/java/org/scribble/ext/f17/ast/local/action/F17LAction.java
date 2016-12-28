package org.scribble.ext.f17.ast.local.action;

import org.scribble.ext.f17.ast.F17AstAction;
import org.scribble.sesstype.name.Role;


public abstract class F17LAction extends F17AstAction
{
	public final Role self;
	public final Role peer;

	public F17LAction(Role self, Role peer)  // local subj == peer
	{
		super(peer);
		this.self = self;
		this.peer = peer;
	}
	
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
		F17LReceive them = (F17LReceive) obj;
		return super.equals(obj)  // super does canEquals
				&& this.self.equals(them.self);  // N.B. considering self for local type equality
	}

	protected abstract boolean canEquals(Object o);
}
