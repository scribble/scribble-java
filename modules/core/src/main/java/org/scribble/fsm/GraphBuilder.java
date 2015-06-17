package org.scribble.fsm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.name.RecVar;

// If not used as a standalone library, then integrate into FsmConverter
public class GraphBuilder
{
	//private final Map<LProtocolName, ProtocolState> graphs = new HashMap<>();  // Move to FsmConverter probably
	
	private final Map<RecVar, ProtocolState> recvars = new HashMap<>();
	private final Map<SubprotocolSig, ProtocolState> subprotos = new HashMap<>();  // Not scoped sigs

	private ProtocolState entry;
	private ProtocolState exit;
	
	public GraphBuilder()
	{
		//this.entry = root;
		//this.exit = term;
	}
	
	public void reset()
	{
		this.recvars.clear();
		this.entry = newState(Collections.emptySet());
		this.exit = newState(Collections.emptySet());
	}
	
	/*public void addGraph(LProtocolName pn, ProtocolState root)
	{
		this.graphs.put(pn, root);
	}
	
	public Map<LProtocolName, ProtocolState> getGraphs()
	{
		return this.graphs;
	}*/
	
	//public ProtocolState newState(Set<RecVar> labs)
	public ProtocolState newState(Set<String> labs)
	{
		ProtocolState s = new ProtocolState(labs);
		//this.states.add(s);
		return s;
	}
	
	public void addEdge(ProtocolState s, IOAction a, ProtocolState succ)
	{
		s.addEdge(a, succ);
	}

	public ProtocolState getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar);
	}	
	
	public void setRecursionEntry(RecVar recvar)
	{
		this.recvars.put(recvar, this.entry);
	}	

	public void removeRecursionEntry(RecVar recvar)
	{
		this.recvars.remove(recvar);
	}	
	
	public ProtocolState getSubprotocolEntry(SubprotocolSig subsig)
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
	
	public ProtocolState getEntry()
	{
		return this.entry;
	}

	public void setEntry(ProtocolState entry)
	{
		this.entry = entry;
	}

	public ProtocolState getExit()
	{
		return this.exit;
	}

	public void setExit(ProtocolState exit)
	{
		this.exit = exit;
	}
}
