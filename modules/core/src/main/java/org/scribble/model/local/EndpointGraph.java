package org.scribble.model.local;

public class EndpointGraph
{
	public final EndpointState init;
	public final EndpointState term;
	
	public EndpointGraph(EndpointState init, EndpointState term)
	{
		this.init = init;
		this.term = term;
	}
	
	@Override
	public String toString()
	{
		return this.init.toDot();
	}
}
