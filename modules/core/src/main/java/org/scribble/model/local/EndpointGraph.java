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
	
	public EndpointFSM toFsm()
	{
		return new EndpointFSM(this);
	}
	
	@Override
	public String toString()
	{
		return this.init.toDot();
	}
	
	/*public static EndpointGraph getOutputSubtypes(EndpointGraph g)
	{
		LGraphBuilder builder = new LGraphBuilder();
		Map<Integer, EndpointState> map = new HashMap<>();  // g nodes -> new nodes
		map.put(g.init.id, builder.getEntry());
		if (g.term != null)
		{
			map.put(g.term.id, builder.getExit());
		}
		for (EndpointState s : EndpointState.getAllReachable(g.init))
		{
			if (!map.containsKey(s.id))  // init and term
			{
				EndpointState tmp = builder.newState(s.getLabels());
				map.put(s.id, tmp);
			}
		}
		Map<Integer, EndpointState> todo = new LinkedHashMap<>();
		todo.put(g.init.id, g.init);
		while (!todo.isEmpty())
		{
			EndpointState s = todo.values().iterator().next();
			todo.remove(s);
			
			if (s.getStateKind() == Kind.OUTPUT && s.getAllTakeable().size() > 1)
			{
				
			}
			else
			{
				
			}
		}
		return builder.finalise();
	}*/
}
