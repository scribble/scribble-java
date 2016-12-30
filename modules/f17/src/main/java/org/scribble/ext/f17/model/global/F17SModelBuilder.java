package org.scribble.ext.f17.model.global;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.name.Role;


// Duplicated from F17LTSBuilder
public class F17SModelBuilder
{
	public F17SModelBuilder()
	{

	}
	
	public F17SModel build(Map<Role, EState> E0, boolean explicit)
	{
		F17SState init = new F17SState(E0, explicit); 
		
		Set<F17SState> todo = new HashSet<>();
		Set<F17SState> seen = new HashSet<>();
		todo.add(init);
		
		while (!todo.isEmpty())
		{
			Iterator<F17SState> i = todo.iterator();
			F17SState curr = i.next();
			i.remove();
			seen.add(curr);

			Map<Role, List<EAction>> fireable = curr.getFireable();
			Set<Entry<Role, List<EAction>>> es = new HashSet<>(fireable.entrySet());
			while (!es.isEmpty())
			{
				Iterator<Entry<Role, List<EAction>>> j = es.iterator();
				Entry<Role, List<EAction>> e = j.next();
				j.remove();
				//boolean removed = es.remove(e);

				Role self = e.getKey();
				List<EAction
				> as = e.getValue();
				for (EAction a : as)
				{
					// cf. SState.getNextStates
					final F17SState tmp;
					if (a.isSend() || a.isReceive() || a.isDisconnect())
					{
						tmp = curr.fire(self, a);
					}
					else if (a.isConnect() || a.isAccept())
					{
						EAction dual = a.toDual(self);
						tmp = curr.sync(self, a, a.peer, dual);
						for (Entry<Role, List<EAction>> foo : es)
						{
							if (foo.getKey().equals(a.peer))
							{
								es.remove(foo);
								foo.getValue().remove(dual);  // remove side effect causes underlying hashing to become inconsistent, so need to manually remove/re-add
								es.add(foo);
								break;
							}
						}
						if (a.isAccept())
						{
							a = dual;  // HACK: draw connect/accept sync edges as connect (to stand for the sync of both) -- set of actions as edge label probably more consistent
						}
					}
					else
					{
						throw new RuntimeException("[f17] Shouldn't get in here: " + a);
					}

					F17SState next = tmp;
					if (seen.contains(tmp))
					{
						next = seen.stream().filter((s) -> s.equals(tmp)).iterator().next();
					}
					else if (todo.contains(tmp))
					{
						next = todo.stream().filter((s) -> s.equals(tmp)).iterator().next();
					}
					curr.addEdge(a.toGlobal(self), next);
					if (!seen.contains(tmp) && !todo.contains(next))
					{
						todo.add(next);
					}
				}
			}
		}
		
		return new F17SModel(E0, init, seen);
	}
}
