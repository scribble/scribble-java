package org.scribble.f17.ast.global;

import org.scribble.f17.ast.F17Rec;
import org.scribble.sesstype.name.RecVar;

public class F17GRec extends F17Rec<F17GType> implements F17GType
{
	public F17GRec(RecVar recvar, F17GType body)
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
		if (!(obj instanceof F17GRec))
		{
			return false;
		}
		F17GRec them = (F17GRec) obj;
		return them.canEquals(this) && this.recvar.equals(them.recvar) && this.body.equals(them.body);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17GRec;
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
