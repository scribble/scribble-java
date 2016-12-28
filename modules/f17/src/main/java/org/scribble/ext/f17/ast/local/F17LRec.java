package org.scribble.ext.f17.ast.local;

import org.scribble.ext.f17.ast.F17Rec;
import org.scribble.sesstype.name.RecVar;

public class F17LRec extends F17Rec<F17LType> implements F17LType
{
	public F17LRec(RecVar recvar, F17LType body)
	{
		super(recvar, body);
	}
	
	/*@Override
	public Set<RecVar> freeVariables()
	{
		Set<RecVar> res = body.freeVariables();
		res.remove(recvar);
		return res;
	}
	
	@Override
	public Set<Role> roles()
	{
		return body.roles();
	}*/
	
	@Override
	public String toString()
	{
		return "mu " + this.recvar + "." + this.body;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17LRec))
		{
			return false;
		}
		F17LRec them = (F17LRec) obj;
		return them.canEquals(this) && this.recvar.equals(them.recvar) && this.body.equals(them.body);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17LRec;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((recvar == null) ? 0 : recvar.hashCode());
		return result;
	}
}
