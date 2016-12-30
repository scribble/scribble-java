package org.scribble.ext.f17.lts;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.ast.local.action.F17LAccept;
import org.scribble.ext.f17.ast.local.action.F17LConnect;
import org.scribble.ext.f17.ast.local.action.F17LDisconnect;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.ext.f17.lts.action.F17LTSAction;
import org.scribble.sesstype.name.Role;


public class F17LTSBuilder
{
	public F17LTSBuilder()
	{

	}
	
	public F17LTS build(Map<Role, F17LType> P0, boolean explicit)
	{
		F17Session init = new F17Session(P0, explicit); 
		
		Set<F17Session> todo = new HashSet<>();
		Set<F17Session> seen = new HashSet<>();
		todo.add(init);
		
		while (!todo.isEmpty())
		{
			Iterator<F17Session> i = todo.iterator();
			F17Session curr = i.next();
			i.remove();
			seen.add(curr);

			Map<Role, List<F17LTSAction>> fireable = curr.getFireable();
			Set<Entry<Role, List<F17LTSAction>>> es = new HashSet<>(fireable.entrySet());
			while (!es.isEmpty())
			{
				Iterator<Entry<Role, List<F17LTSAction>>> j = es.iterator();
				Entry<Role, List<F17LTSAction>> e = j.next();
				j.remove();
				//boolean removed = es.remove(e);

				Role r = e.getKey();
				List<F17LTSAction> as = e.getValue();
				for (F17LTSAction a : as)
				{
					// cf. SState.getNextStates
					final F17Session tmp;
					if (a.action instanceof F17LSend || a.action instanceof F17LReceive || a.action instanceof F17LDisconnect)
					{
						tmp = curr.fire(r, a);
					}
					else if (a.action instanceof F17LConnect || a.action instanceof F17LAccept)
					{
						F17LTSAction dual = new F17LTSAction(a.action.toDual());
						tmp = curr.sync(r, a, a.action.peer, dual);
						for (Entry<Role, List<F17LTSAction>> foo : es)
						{
							if (foo.getKey().equals(a.action.peer))
							{
								es.remove(foo);
								foo.getValue().remove(dual);  // remove side effect causes underlying hashing to become inconsistent, so need to manually remove/re-add
								es.add(foo);
								break;
							}
						}
						if (a.action instanceof F17LAccept)
						{
							a = dual;  // HACK: draw connect/accept sync edges as connect (to stand for the sync of both) -- set of actions as edge label probably more consistent
						}
					}
					else
					{
						throw new RuntimeException("[f17] Shouldn't get in here: " + a);
					}

					F17Session next = tmp;
					if (seen.contains(tmp))
					{
						next = seen.stream().filter((s) -> s.equals(tmp)).iterator().next();
					}
					else if (todo.contains(tmp))
					{
						next = todo.stream().filter((s) -> s.equals(tmp)).iterator().next();
					}
					curr.addEdge(a, next);
					if (!seen.contains(tmp) && !todo.contains(next))
					{
						todo.add(next);
					}
				}
			}
		}
		
		return new F17LTS(P0, init, seen);
	}
}
