package org.scribble.model.local;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.name.RecVar;

// Helper class for EndpointGraphBuilder -- can access the protected setters of EndpointState
public class GraphBuilder
{
	private final Map<RecVar, EndpointState> recvars = new HashMap<>();
	private final Map<SubprotocolSig, EndpointState> subprotos = new HashMap<>();  // Not scoped sigs

	private EndpointState entry;
	private EndpointState exit;
	
	public GraphBuilder()
	{

	}
	
	public void reset()
	{
		this.recvars.clear();
		this.entry = newState(Collections.emptySet());
		this.exit = newState(Collections.emptySet());
	}
	
	public EndpointState newState(Set<RecVar> labs)
	{
		return new EndpointState(labs);
	}
	
	/*public void addEntryLabel(RecVar lab)
	{
		this.entry.addLabel(lab);
	}*/

	public void addEdge(EndpointState s, IOAction a, EndpointState succ)
	{
		s.addEdge(a, succ);
	}

	public EndpointState getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar);
	}	
	
	public void setRecursionEntry(RecVar recvar)
	{
		this.entry.addLabel(recvar);
		this.recvars.put(recvar, this.entry);
	}	

	public void removeRecursionEntry(RecVar recvar)
	{
		this.recvars.remove(recvar);
	}	
	
	public EndpointState getSubprotocolEntry(SubprotocolSig subsig)
	{
		return this.subprotos.get(subsig);
	}

	public void setSubprotocolEntry(SubprotocolSig subsig)
	{
		this.subprotos.put(subsig, this.entry);
	}	

	public void removeSubprotocolEntry(SubprotocolSig subsig)
	{
		this.subprotos.remove(subsig);
	}
	
	public EndpointState getEntry()
	{
		return this.entry;
	}

	public void setEntry(EndpointState entry)
	{
		this.entry = entry;
	}

	public EndpointState getExit()
	{
		return this.exit;
	}

	public void setExit(EndpointState exit)
	{
		this.exit = exit;
	}
}
