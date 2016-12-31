package org.scribble.ext.f17.ast.global;

import java.util.Set;

import org.scribble.ext.f17.ast.F17Rec;
import org.scribble.ext.f17.main.F17Exception;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

public class F17GRec extends F17Rec<F17GType> implements F17GType
{
	public F17GRec(RecVar recvar, F17GType body)
	{
		super(recvar, body);
	}

	/*@Override
	public F17GType onceUnfolding()
	{

	}

	private static F17GType copyAndSubs(F17GType lt, RecVar rv, F17GType subs)
	{
		// Integrates the copy from each
		if (lt instanceof F17GChoice)
		{
			Map<F17GAction, F17GType> copy = ((F17GChoice) lt).cases.entrySet().stream().collect(
					Collectors.toMap((e) -> e.getKey(), (e) -> copyAndSubs(e.getValue(), rv, subs)));
			return F17AstFactory.FACTORY.GChoice(copy);
		}
		else if (lt instanceof F17GRec)
		{
			F17GRec lr = (F17GRec) lt;
			return F17AstFactory.FACTORY.GRec(lr.recvar, copyAndSubs(lr.body, rv, subs));
		}
		else if (lt instanceof F17GRecVar)
		{
			if (((F17GRecVar) lt).var.equals(rv))
			{
				return subs;
			}
			else
			{
				return lt;
			}
		}
		else if (lt instanceof F17GEnd)
		{
			return lt;
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + lt);
		}
	}*/

	@Override
	public void checkRoleEnabling(Set<Role> enabled) throws F17Exception
	{
		this.body.checkRoleEnabling(enabled);
	}
	
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
