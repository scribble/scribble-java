package org.scribble.model.local;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// FIXME: factor with GModelPath
public class IOTrace
{
	private final IOAction[] elements;

	public IOTrace(IOAction... elements)
	{
		this.elements = elements;
	}
	
	protected IOTrace(IOAction[] p1, IOAction... p2)
	{
		this(foo(p1, p2));
	}
	
	private static IOAction[] foo(IOAction[] p1, IOAction[] p2)
	{
		IOAction[] copy = Arrays.copyOf(p1, p1.length + p2.length);
		System.arraycopy(p2, 0, copy, p1.length, p2.length);
		return copy;
	}
	
	public boolean containsEdge(IOAction a)
	{
		return Arrays.stream(this.elements).anyMatch(x -> x.equals(a));
	}
	
	public IOTrace append(IOAction pe)
	{
		return new IOTrace(this.elements, pe);
	}

	public IOTrace concat(IOTrace p)
	{
		return new IOTrace(this.elements, p.elements);
	}
	
	/*public boolean isExit()
	{
		return this.elements.length == 0 || !(this.elements[this.elements.length - 1] instanceof RecVar);
	}*/
	
	public List<IOAction> getElements()
	{
		return Collections.unmodifiableList(Arrays.asList(this.elements));
	}
	
	public IOAction getLastElement()
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
		if (!(o instanceof IOTrace))
		{
			return false;
		}
		//return ((Path) o).elements.equals(this.elements);
		return Arrays.equals(((IOTrace) o).elements, this.elements);
	}
}
