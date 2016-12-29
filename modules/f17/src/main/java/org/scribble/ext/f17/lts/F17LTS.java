package org.scribble.ext.f17.lts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.sesstype.name.Role;

// 1-bounded LTS
public class F17LTS
{
	public final Map<Role, F17LType> P0;
	public final F17Session init;
	
	private final Set<F17Session> all;

	protected F17LTS(Map<Role, F17LType> P0, F17Session init, Set<F17Session> all)
	{
		this.P0 = Collections.unmodifiableMap(P0);
		this.init = init;
		this.all = new HashSet<>(all);
	}
	
	public F17LTSSafetyErrors getSafetyErrors()
	{
		Set<F17Session> conns = this.all.stream().filter((s) -> s.isConnectionError()).collect(Collectors.toSet());
		Set<F17Session> disconns = this.all.stream().filter((s) -> s.isDisconnectedError()).collect(Collectors.toSet());
		Set<F17Session> unconns = this.all.stream().filter((s) -> s.isUnconnectedError()).collect(Collectors.toSet());
		Set<F17Session> unfins = this.all.stream().filter((s) -> s.isUnfinishedRoleError(this.P0)).collect(Collectors.toSet());
		return new F17LTSSafetyErrors(conns, disconns, unconns, unfins);
	}
	
	@Override
	public String toString()
	{
		return this.init.toString();
	}
	
	public String toDot()
	{
		return this.init.toDot();
	}
	
	@Override
	public final int hashCode()
	{
		int hash = 2887;
		hash = 31 * hash + this.init.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof F17LTS))
		{
			return false;
		}
		return this.init.id == ((F17LTS) o).init.id;
	}
}
