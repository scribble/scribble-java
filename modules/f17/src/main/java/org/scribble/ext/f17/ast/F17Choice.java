package org.scribble.ext.f17.ast;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ext.f17.ast.F17AstAction;

public abstract class F17Choice<A extends F17AstAction, C extends F17Type> implements F17Type
{
	public final Map<A, C> cases;
	
	public F17Choice(Map<A, C> cases)
	{
		this.cases = Collections.unmodifiableMap(cases);
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
		String s = this.cases.entrySet().stream().map((e) -> e.getKey() + "." + e.getValue()).collect(Collectors.joining(" + "));
		if (this.cases.size() > 1)
		{
			s = "(" + s + ")";
		}
		return s;
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
		if (!(obj instanceof F17Choice))
		{
			return false;
		}
		F17Choice<?, ?> them = (F17Choice<?, ?>) obj;
		return them.canEquals(this) && this.cases.equals(them.cases);
				// FIXME: check A, C are equal
	}
	
	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof F17Choice;
	}
}
