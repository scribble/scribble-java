package org.scribble.model.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.model.local.IOAction;
import org.scribble.model.local.IOTrace;
import org.scribble.sesstype.name.Role;

@Deprecated
public class GModelPath
{
	private final GModelAction[] elements;

	public GModelPath(GModelAction... elements)
	{
		this.elements = elements;
	}
	
	protected GModelPath(GModelAction[] p1, GModelAction... p2)
	{
		this(foo(p1, p2));
	}
	
	private static GModelAction[] foo(GModelAction[] p1, GModelAction[] p2)
	{
		GModelAction[] copy = Arrays.copyOf(p1, p1.length + p2.length);
		System.arraycopy(p2, 0, copy, p1.length, p2.length);
		return copy;
	}
	
	public Set<Role> getRoles()
	{
		return Arrays.stream(this.elements).flatMap((a) -> a.getRoles().stream()).collect(Collectors.toSet());
	}
	
	public IOTrace project(Role self)
	{
		return new IOTrace(
				Arrays.stream(this.elements)
					.filter((a) -> a.containsRole(self))
					.map((a) -> a.project(self))
					.collect(Collectors.toList())
					.toArray(new IOAction[0]));
	}
	
	public boolean containsEdge(GModelAction a)
	{
		return Arrays.stream(this.elements).anyMatch(x -> x.equals(a));
	}
	
	public GModelPath append(GModelAction pe)
	{
		return new GModelPath(this.elements, pe);
	}

	public GModelPath concat(GModelPath p)
	{
		return new GModelPath(this.elements, p.elements);
	}
	
	/*public boolean isExit()
	{
		return this.elements.length == 0 || !(this.elements[this.elements.length - 1] instanceof RecVar);
	}*/
	
	public List<GModelAction> getElements()
	{
		return Collections.unmodifiableList(Arrays.asList(this.elements));
	}
	
	public GModelAction getLastElement()
	{
		return this.elements[this.elements.length - 1];
	}
	
	/*public Set<Role> getRoles()
	{
		Set<Role> roles = new HashSet<>();
		for (GModelState pe : this.elements)
		{
			if (pe instanceof Communication)
			{
				Communication comm = (Communication) pe;
				roles.add(comm.src);
				roles.add(comm.peer);
			}
			/*else  //if (pe instanceof RecVar)
			{
				
			}* /
		}
		return roles;
	}*/

	@Override
	public String toString()
	{
		//return this.elements.toString();
		return Arrays.toString(this.elements);
	}
	
	@Override
	public int hashCode()
	{
		return this.elements.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GModelPath))
		{
			return false;
		}
		//return ((Path) o).elements.equals(this.elements);
		return Arrays.equals(((GModelPath) o).elements, this.elements);
	}
}
