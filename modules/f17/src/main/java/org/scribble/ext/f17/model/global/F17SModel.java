package org.scribble.ext.f17.model.global;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.model.endpoint.EState;
import org.scribble.sesstype.name.Role;

// 1-bounded LTS
public class F17SModel
{
	public final Map<Role, EState> E0;
	public final F17SState init;
	
	private final Set<F17SState> all;

	protected F17SModel(Map<Role, EState> E0, F17SState init, Set<F17SState> all)
	{
		this.E0 = Collections.unmodifiableMap(E0);
		this.init = init;
		this.all = new HashSet<>(all);
	}
	
	public F17SafetyErrors getSafetyErrors()
	{
		Set<F17SState> conns = this.all.stream().filter((s) -> s.isConnectionError()).collect(Collectors.toSet());
		Set<F17SState> disconns = this.all.stream().filter((s) -> s.isDisconnectedError()).collect(Collectors.toSet());
		Set<F17SState> unconns = this.all.stream().filter((s) -> s.isUnconnectedError()).collect(Collectors.toSet());
		Set<F17SState> unfins = this.all.stream().filter((s) -> s.isUnfinishedRoleError(this.E0)).collect(Collectors.toSet());
		return new F17SafetyErrors(conns, disconns, unconns, unfins);
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
		if (!(o instanceof F17SModel))
		{
			return false;
		}
		return this.init.id == ((F17SModel) o).init.id;
	}
}
