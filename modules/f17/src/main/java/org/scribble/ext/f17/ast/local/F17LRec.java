package org.scribble.ext.f17.ast.local;

import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ext.f17.ast.F17AstFactory;
import org.scribble.ext.f17.ast.F17Rec;
import org.scribble.ext.f17.ast.local.action.F17LAction;
import org.scribble.sesstype.name.RecVar;

public class F17LRec extends F17Rec<F17LType> implements F17LType
{
	public F17LRec(RecVar recvar, F17LType body)
	{
		super(recvar, body);
	}

	@Override
	public F17LRec copy()
	{
		return F17AstFactory.FACTORY.LRec(this.recvar, this.body.copy());
	}
	
	public F17LType unfold()
	{
		F17LType tmp = this;
		while (tmp instanceof F17LRec)
		{
			F17LRec lr = (F17LRec) tmp;
			tmp = copyAndSubs(lr.body, lr.recvar, lr.copy());
		}
		return tmp;
	}

	private static F17LType copyAndSubs(F17LType lt, RecVar rv, F17LType subs)
	{
		// Integrates the copy from each
		if (lt instanceof F17LChoice)
		{
			Map<F17LAction, F17LType> copy = ((F17LChoice) lt).cases.entrySet().stream().collect(
					Collectors.toMap((e) -> e.getKey(), (e) -> copyAndSubs(e.getValue(), rv, subs)));
			return F17AstFactory.FACTORY.LChoice(copy);
		}
		else if (lt instanceof F17LRec)
		{
			F17LRec lr = (F17LRec) lt;
			return F17AstFactory.FACTORY.LRec(lr.recvar, copyAndSubs(lr.body, rv, subs));
		}
		else if (lt instanceof F17LRecVar)
		{
			if (((F17LRecVar) lt).var.equals(rv))
			{
				return subs;
			}
			else
			{
				return lt;
			}
		}
		else if (lt instanceof F17LEnd)
		{
			return lt;
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + lt);
		}
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
