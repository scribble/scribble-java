package org.scribble.model.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

@Deprecated
public class Path
{
	//private final List<PathElement> elements;
	private final PathElement[] elements;

	//public Path(List<PathElement> elements)
	public Path(PathElement... elements)
	{
		//this.elements = new LinkedList<>(elements);
		this.elements = elements;
	}
	
	/*protected Path(Path path, PathElement pe)
	{
		/*this(
				new LinkedList<PathElement>(path.elements)
				{
					private static final long serialVersionUID = 1L;
					{ add(pe); }
				}
		);* /
		this(path.elements);
		this.elements.add(pe);* /
		this(path, new Path(Arrays.asList(pe)));  // FIXME: maybe use varargs constructor instead
	}*/

	//protected Path(Path p1, Path p2)
	protected Path(PathElement[] p1, PathElement... p2)
	{
		/*this(p1.elements);
		this.elements.addAll(p2.elements);*/
		this(foo(p1, p2));
	}
	
	private static PathElement[] foo(PathElement[] p1, PathElement[] p2)
	{
		PathElement[] copy = Arrays.copyOf(p1, p1.length + p2.length);
		System.arraycopy(p2, 0, copy, p1.length, p2.length);
		return copy;
	}
	
	public Path append(PathElement pe)
	{
		return new Path(this.elements, pe);
	}

	public Path concat(Path p)
	{
		return new Path(this.elements, p.elements);
	}
	
	public boolean isExit()
	{
		//return this.elements.isEmpty() || !(this.elements.get(this.elements.size() - 1) instanceof RecVar);
		return this.elements.length == 0 || !(this.elements[this.elements.length - 1] instanceof RecVar);
	}
	
	public List<PathElement> getElements()
	{
		//return Collections.unmodifiableList(this.elements);
		return Collections.unmodifiableList(Arrays.asList(this.elements));
	}
	
	public Set<Role> getRoles()
	{
		Set<Role> roles = new HashSet<>();
		for (PathElement pe : this.elements)
		{
			if (pe instanceof Communication)
			{
				Communication comm = (Communication) pe;
				roles.add(comm.src);
				roles.add(comm.obj);
			}
			/*else  //if (pe instanceof RecVar)
			{
				
			}*/
		}
		return roles;
	}

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
		if (!(o instanceof Path))
		{
			return false;
		}
		//return ((Path) o).elements.equals(this.elements);
		return Arrays.equals(((Path) o).elements, this.elements);
	}
}
