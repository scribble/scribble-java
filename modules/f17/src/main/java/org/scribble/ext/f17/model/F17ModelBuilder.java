package org.scribble.ext.f17.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.model.action.F17Action;
import org.scribble.sesstype.name.Role;


public class F17ModelBuilder
{
	public F17ModelBuilder()
	{

	}
	
	public F17State build(Map<Role, F17LType> P0)
	{
		F17State init = new F17State(P0);
		
		Set<F17State> todo = new HashSet<>();
		todo.add(init);
		
		while (!todo.isEmpty())
		{
			Iterator<F17State> i = todo.iterator();
			F17State curr = i.next();
			i.remove();

			Map<Role, List<F17Action>> fireable = curr.getFireable();
			for (Entry<Role, List<F17Action>> e : fireable.entrySet())
			{
				Role r = e.getKey();
				List<F17Action> as = e.getValue();
				for (F17Action a : as)
				{
					F17State next = curr.fire(r, a);
					if (!todo.contains(next) && !next.equals(curr))
					{
						curr.addEdge(a, next);
						todo.add(next);
					}
				}
			}
		}
		
		return init;
	}
}
