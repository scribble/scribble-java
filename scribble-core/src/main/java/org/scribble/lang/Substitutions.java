package org.scribble.lang;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.scribble.type.name.Name;

// CHECKME: move to util?
public class Substitutions<N extends Name<?>>  // CHECKME: too restricting?
{
	// Old name -> new name
	private final Map<N, N> subs = new HashMap<>();

	public Substitutions(List<N> old, List<N> neu)
	{
		if (old.size() != neu.size())
		{
			throw new RuntimeException("Unqeual sizes: " + old + " ; " + neu);
		}
		Iterator<N> i = neu.iterator();
		for (N n : old)
		{
			this.subs.put(n, i.next());
		}
	}
	
	/*public void put(N old, N neu)
	{
		this.subs.put(old, neu);
	}*/
	
	public N apply(N old)
	{
		return this.subs.get(old);
	}

	@Override
	public String toString()
	{
		return this.subs.toString();
	}

	@Override
	public int hashCode()
	{
		int hash = 1889;
		hash = 31 * hash + this.subs.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Substitutions))
		{
			return false;
		}
		return this.subs.equals(((Substitutions<?>) o).subs);
	}
}
