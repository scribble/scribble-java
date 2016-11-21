package org.scribble.model.global;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.scribble.model.endpoint.EndpointState;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.sesstype.name.Role;

public class GMStateErrors
{
	// FIXME: could also check for roles stuck on unconnected sends here (probably better, than current syntax check)
	public final Map<Role, EReceive> stuck;
	public final Set<Set<Role>> waitFor;
	public final Map<Role, Set<ESend>> orphans;
	public Map<Role, EndpointState> unfinished;

	public GMStateErrors(Map<Role, EReceive> receptionErrors, Set<Set<Role>> deadlocks, Map<Role, Set<ESend>> orphans, Map<Role, EndpointState> unfinished)
	{
		this.stuck = Collections.unmodifiableMap(receptionErrors);
		this.waitFor = Collections.unmodifiableSet(deadlocks);
		this.orphans = Collections.unmodifiableMap(orphans);
		this.unfinished = Collections.unmodifiableMap(unfinished);
	}
	
	public boolean isEmpty()
	{
		return this.stuck.isEmpty() && this.waitFor.isEmpty() && this.orphans.isEmpty();
	}
}
