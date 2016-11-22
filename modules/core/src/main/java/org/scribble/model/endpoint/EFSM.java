package org.scribble.model.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.model.endpoint.actions.EAction;

public class EFSM extends EGraph
{
	public final EState curr;
	
	//public EndpointFSM(EndpointState init, EndpointState term)
	protected EFSM(EGraph graph)
	{
		this(graph.init, graph.term, graph.init);
	}

	//public EndpointFSM(EndpointState init, EndpointState term, EndpointState curr)
	//public EndpointFSM(EndpointGraph graph, EndpointState curr)
	protected EFSM(EState init, EState term, EState curr)
	{
		super(init, term);
		this.curr = curr;
	}
	
	/*public EndpointState getCurrent()
	{
		return this.curr;
	}*/
	
	@Override
	public String toString()
	{
		return Integer.toString(this.curr.id);
	}

	// FIXME: check if unfolded initial accept is possible, and if it breaks anything
	public boolean isInitial()
	{
		return this.curr.equals(this.init);
	}
	
	// FIXME: rename isTerminated
	public boolean isTerminal()
	{
		return this.curr.isTerminal();
	}

	public EStateKind getStateKind()
	{
		return this.curr.getStateKind();
	}

	public List<EFSM> takeAll(EAction a)
	{
		//return this.curr.takeAll(a).stream().map((s) -> new EndpointFSM(this.init, this.term, s)).collect(Collectors.toList());
		return this.curr.getSuccessors(a).stream().map((s) -> new EFSM(this.init, this.term, s)).collect(Collectors.toList());
	}

	public List<EAction> getAllTakeable()
	{
		return this.curr.getAllActions();
	}
	
	public boolean isTakeable(EAction a)
	{
		return this.curr.hasAction(a);
	}
	
	public boolean isConnectOrWrapClientOnly()
	{
		return this.curr.isConnectOrWrapClientOnly();
	}

	@Override
	public final int hashCode()
	{
		int hash = 1049;
		hash = 31 * hash + this.init.hashCode();
		hash = 31 * hash + this.curr.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof EFSM))
		{
			return false;
		}
		EFSM them = (EFSM) o;
		return this.init.equals(them.init) && this.curr.equals(them.curr);
	}
}
