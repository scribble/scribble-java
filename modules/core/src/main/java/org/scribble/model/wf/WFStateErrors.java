package org.scribble.model.wf;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.Role;

public class WFStateErrors
{
	// FIXME: could also check for roles stuck on unconnected sends here (probably better, than current syntax check)
	public final Map<Role, Receive> stuck;
	public final Set<Set<Role>> waitFor;
	public final Map<Role, Set<Send>> orphans;  // Don't think these can arise, due to MPST "pair-oriented" constructors (even with connect/disconnect)

	public WFStateErrors(Map<Role, Receive> receptionErrors, Set<Set<Role>> deadlocks, Map<Role, Set<Send>> orphans)
	{
		this.stuck = Collections.unmodifiableMap(receptionErrors);
		this.waitFor = Collections.unmodifiableSet(deadlocks);
		this.orphans = Collections.unmodifiableMap(orphans);
	}
	
	public boolean isEmpty()
	{
		return this.stuck.isEmpty() && this.waitFor.isEmpty() && this.orphans.isEmpty();
	}
}
