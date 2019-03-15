package org.scribble.lang.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.type.name.RecVar;

public class ReachabilityEnv
{
	public final boolean postcont;  // For checking bad sequencing of unreachable code: false after a continue; true if choice has an exit (false inherited for all other constructs)
	public final Set<RecVar> recvars;  // For checking "reachable code" also satisfies tail recursion (e.g., after choice-with-exit)

	public ReachabilityEnv(boolean postcont, Set<RecVar> recvars)
	{
		this.postcont = postcont;
		this.recvars = Collections.unmodifiableSet(recvars);
	}

	public boolean isSeqable()
	{
		return !this.postcont && this.recvars.isEmpty();
	}
}
