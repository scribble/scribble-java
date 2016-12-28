package org.scribble.ext.f17.ast.local;

import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ext.f17.ast.F17AstFactory;
import org.scribble.ext.f17.ast.F17Choice;
import org.scribble.ext.f17.ast.local.action.F17LAction;

public class F17LChoice extends F17Choice<F17LAction, F17LType> implements F17LType
{
	public F17LChoice(Map<F17LAction, F17LType> cases)
	{
		super(cases);
	}
	
	@Override
	public F17LChoice copy()
	{
		Map<F17LAction, F17LType> copy = this.cases.entrySet().stream().collect(
				Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue().copy()));
		return F17AstFactory.FACTORY.LChoice(copy);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 29;
		hash = 31 * hash + this.cases.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof F17LChoice))
		{
			return false;
		}
		F17LChoice them = (F17LChoice) obj;
		return them.canEquals(this) && this.cases.equals(them.cases);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17LChoice;
	}
}
