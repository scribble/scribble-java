package org.scribble.model.local;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.main.ScribbleException;
import org.scribble.model.ModelState;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.RecVar;

// http://sandbox.kidstrythisathome.com/erdos/
public class EndpointState extends ModelState<IOAction, EndpointState, Local>
{
	public static enum Kind { OUTPUT, UNARY_INPUT, POLY_INPUT, TERMINAL, ACCEPT, //CONNECT
		}  // CONNECTION should just be sync?
			// FIXME: distinguish connection and message transfer
	
	/*private static int count = 0;
	
	public final int id;

	private final Set<RecVar> labs;  // Was RecVar and SubprotocolSigs, now using inlined protocol for FSM building so just RecVar
	private final LinkedHashMap<IOAction, EndpointState> edges;  // Want predictable ordering of entries for e.g. API generation (state enumeration)*/
	
	protected EndpointState(Set<RecVar> labs)
	{
		/*this.id = EndpointState.count++;
		this.labs = new HashSet<>(labs);
		this.edges = new LinkedHashMap<>();*/
		super(labs);
	}

	public EndpointState unfairClone()
	{
		EndpointState init = this.clone();
		
		System.out.println("333a:\n" + this.toDot());
		System.out.println("333b:\n" + init.toDot());
		
			Set<EndpointState> seen = new HashSet<>();
			Set<EndpointState> todo = new LinkedHashSet<>();
			todo.add(init);
			while (!todo.isEmpty())
			{
				Iterator<EndpointState> i = todo.iterator();
				EndpointState curr = i.next();
				i.remove();

				if (seen.contains(curr))
				{
					continue;
				}
				seen.add(curr);
				
				if (curr.getStateKind() == Kind.OUTPUT &&
					curr.getAllTakeable().size() > 1)
				{
					//if (curr.getAllTakeable().size() > 1)
					{
						Iterator<IOAction> as = curr.getAllTakeable().iterator();
						Iterator<EndpointState> ss = curr.getSuccessors().iterator();
						Map<IOAction, EndpointState> clones = new HashMap<>();
						while (as.hasNext())
						{
							IOAction a = as.next();
							EndpointState clone = curr.unfairClone(a, ss.next());
							//try { s.removeEdge(a, tmps); } catch (ScribbleException e) { throw new RuntimeException(e); }
							clones.put(a, clone);
						}
						as = new LinkedList<>(curr.getAllTakeable()).iterator();
						//Iterator<EndpointState>
						ss = new LinkedList<>(curr.getSuccessors()).iterator();
						while (as.hasNext())
						{
							IOAction a = as.next();
							EndpointState s = ss.next();
							try { curr.removeEdge(a, s); } catch (ScribbleException e) { throw new RuntimeException(e); }
						}
						for (Entry<IOAction, EndpointState> e : clones.entrySet())
						{
							curr.addEdge(e.getKey(), e.getValue());
							todo.add(e.getValue());
						}
						//continue;
					}
				}
				else
				{
					todo.addAll(curr.getSuccessors());
				}
			}
		return init;
	}
	
	// Returns the clone of the subgraph rooted at this.take(a), with all non-a pruned from the clone of this
	protected EndpointState unfairClone(IOAction a, EndpointState succ) // Need succ param for non-det
	{
		System.out.println("444a: " + a + ", " + succ +"\n" + this.toDot());
		
		//EndpointState succ = take(a);
		Set<EndpointState> all = new HashSet<>();
		all.add(succ);
		all.addAll(ModelState.getAllReachable(succ));
		Map<Integer, EndpointState> map = new HashMap<>();  // original s.id -> clones
		for (EndpointState s : all)
		{
			//map.put(s.id, newState(s.labs));
			map.put(s.id, newState(Collections.emptySet()));
		}
		System.out.println("555: " + all);
		for (EndpointState s : all)
		{
			Iterator<IOAction> as = s.getAllTakeable().iterator();
			Iterator<EndpointState> ss = s.getSuccessors().iterator();
			EndpointState clone = map.get(s.id);
			while (as.hasNext())
			{
				IOAction tmpa = as.next();
				EndpointState tmps = ss.next();
				if (s.id != this.id || tmpa.equals(a))  // Preserves non-det
				{
					clone.addEdge(tmpa, map.get(tmps.id));
				}
			}
		}
		
		System.out.println("444b:\n" + map.get(succ.id).toDot());
		return map.get(succ.id);
	}
	
	
	@Override
	protected EndpointState newState(Set<RecVar> labs)
	{
		return new EndpointState(labs);
	}
	
	public boolean isConnectOnly()
	{
		return getStateKind() == Kind.OUTPUT && getAllTakeable().stream().allMatch((a) -> a.isConnect());
	}
	
	public Kind getStateKind()
	{
		List<IOAction> as = this.getAllTakeable();
		if (as.size() == 0)
		{
			return Kind.TERMINAL;
		}
		else
		{
			IOAction a = as.iterator().next();
			return (a.isSend() || a.isConnect() || a.isDisconnect()) ? Kind.OUTPUT
						//: (a.isConnect() || a.isAccept()) ? Kind.CONNECTION  // FIXME: states can have mixed connects and sends
						//: (a.isConnect()) ? Kind.CONNECT
						: (a.isAccept()) ? Kind.ACCEPT  // Accept is always unary, guaranteed by treating as a unit message id (wrt. branching)
						: (as.size() > 1) ? Kind.POLY_INPUT : Kind.UNARY_INPUT;
		}
	}
}
