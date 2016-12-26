package org.scribble.f17.ast.global;

import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.f17.ast.F17Choice;
import org.scribble.f17.ast.global.action.F17GAction;

public class F17GChoice extends F17Choice<F17GAction, F17GType> implements F17GType
{
	public F17GChoice(Map<F17GAction, F17GType> cases)
	{
		super(cases);
	}
	
	/*@Override
	public Set<RecVar> freeVariables()
	{
		return cases.values().stream()
				.flatMap((v) -> v.body.freeVariables().stream())
				.collect(Collectors.toSet());
	}
	
	@Override
	public Set<Role> roles()
	{
		Set<Role> roles = cases.values().stream()
				.flatMap((v) -> v.body.roles().stream())
				.collect(Collectors.toSet());
		roles.addAll(java.util.Arrays.asList(src, dest));
		
		return roles;
	}*/
	
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
		if (!(obj instanceof F17GChoice))
		{
			return false;
		}
		F17GChoice them = (F17GChoice) obj;
		return them.canEquals(this) && this.cases.equals(them.cases);
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17GChoice;
	}
}
