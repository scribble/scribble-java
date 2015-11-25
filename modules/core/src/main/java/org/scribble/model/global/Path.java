package org.scribble.model.global;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

public class Path
{
	private final List<PathElement> elements;

	public Path(List<PathElement> elements)
	{
		this.elements = new LinkedList<>(elements);
	}
	
	protected Path(Path path, PathElement pe)
	{
		/*this(
				new LinkedList<PathElement>(path.elements)
				{
					private static final long serialVersionUID = 1L;
					{ add(pe); }
				}
		);*/
		this(path.elements);
		this.elements.add(pe);
	}
	
	public Path append(PathElement pe)
	{
		return new Path(this, pe);
	}
	
	public boolean isExit()
	{
		return this.elements.isEmpty() || !(this.elements.get(this.elements.size() - 1) instanceof RecVar);
	}
	
	public List<PathElement> getElements()
	{
		return Collections.unmodifiableList(this.elements);
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
				roles.add(comm.peer);
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
		return this.elements.toString();
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
		return ((Path) o).elements.equals(this.elements);
	}
}
