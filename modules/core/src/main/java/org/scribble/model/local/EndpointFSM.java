package org.scribble.model.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.model.local.EndpointState.Kind;

public class EndpointFSM extends EndpointGraph
{
	public final EndpointState curr;
	
	//public EndpointFSM(EndpointState init, EndpointState term)
	protected EndpointFSM(EndpointGraph graph)
	{
		this(graph.init, graph.term, graph.init);
	}

	//public EndpointFSM(EndpointState init, EndpointState term, EndpointState curr)
	//public EndpointFSM(EndpointGraph graph, EndpointState curr)
	protected EndpointFSM(EndpointState init, EndpointState term, EndpointState curr)
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

	public Kind getStateKind()
	{
		return this.curr.getStateKind();
	}

	public List<EndpointFSM> takeAll(IOAction a)
	{
		//return this.curr.takeAll(a).stream().map((s) -> new EndpointFSM(this.init, this.term, s)).collect(Collectors.toList());
		return this.curr.takeAll(a).stream().map((s) -> new EndpointFSM(this.init, this.term, s)).collect(Collectors.toList());
	}

	public List<IOAction> getAllTakeable()
	{
		return this.curr.getAllTakeable();
	}
	
	public boolean isTakeable(IOAction a)
	{
		return this.curr.isTakeable(a);
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
		if (!(o instanceof EndpointFSM))
		{
			return false;
		}
		EndpointFSM them = (EndpointFSM) o;
		return this.init.equals(them.init) && this.curr.equals(them.curr);
	}
}
