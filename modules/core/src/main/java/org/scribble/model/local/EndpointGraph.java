package org.scribble.model.local;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.scribble.model.local.EndpointState.Kind;

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
	
	/*.. move back to endpointstate
	.. use getallreachable to get subgraph, make a graph clone method
	.. for each poly output, clone a (non-det) edge to clone of the reachable subgraph with the clone of the current node pruned to this single choice
	..     be careful of original non-det edges, need to do each separately
	.. do recursively on the subgraphs, will end up with a normal form with subgraphs without output choices
	.. is it equiv to requiring all roles to see every choice path?  except initial accepting roles -- yes
	.. easier to implement as a direct check on the standard global model, rather than model hacking -- i.e. liveness is not just about terminal sets, but about "branching condition", c.f. julien?
	.. the issue is connect/accept -- makes direct check a bit more complicated, maybe value in doing it by model hacking to rely on standard liveness checking?
	..     should be fine, check set of roles on each path is equal, except for accept-guarded initial roles*/
	public static EndpointGraph getOutputSubtypes(EndpointGraph g)
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
	}
}
