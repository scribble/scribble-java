package org.scribble.f17.ast.local;

import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.f17.ast.F17Choice;
import org.scribble.f17.ast.local.action.F17LAction;

public class F17LChoice extends F17Choice<F17LAction, F17LType> implements F17LType
{
	public F17LChoice(Map<F17LAction, F17LType> cases)
	{
		super(cases);
	}
	
	@Override
	public String toString()
	{
		/*return this.src + "->" + this.dest + ":{" +
				this.cases.entrySet().stream()
					.map((e) -> e.getKey().toString() + e.getValue().toString())
					.collect(Collectors.joining(", ")) + "}";*/
		return "(" + this.cases.entrySet().stream().map((e) -> e.getKey() + "." + e.getValue()).collect(Collectors.joining(" + ")) + ")";
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
