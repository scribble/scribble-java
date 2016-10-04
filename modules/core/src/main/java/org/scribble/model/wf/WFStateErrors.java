package org.scribble.model.wf;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.scribble.model.local.EndpointState;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.Role;

public class WFStateErrors
{
	// FIXME: could also check for roles stuck on unconnected sends here (probably better, than current syntax check)
	public final Map<Role, Receive> stuck;
	public final Set<Set<Role>> waitFor;
	public final Map<Role, Set<Send>> orphans;
	public Map<Role, EndpointState> unfinished;

	public WFStateErrors(Map<Role, Receive> receptionErrors, Set<Set<Role>> deadlocks, Map<Role, Set<Send>> orphans, Map<Role, EndpointState> unfinished)
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
