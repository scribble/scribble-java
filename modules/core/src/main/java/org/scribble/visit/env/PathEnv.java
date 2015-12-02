package org.scribble.visit.env;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.model.global.Path;
import org.scribble.model.global.PathElement;
import org.scribble.sesstype.name.Role;

public class PathEnv extends Env<PathEnv>
{
	private final Set<Path> paths;
	
	public PathEnv()
	{
		this(Collections.emptySet());
	}
	
	protected PathEnv(Set<Path> paths)
	{
		this.paths = new HashSet<>(paths);  // FIXME: LinkedHashSet?  or just Deque?
	}

	@Override
	protected PathEnv copy()
	{
		return new PathEnv(this.paths);
	}
	
	public PathEnv clear()
	{
		PathEnv copy = copy();
		copy.paths.clear();
		return copy;
	}

	@Override
	public PathEnv enterContext()
	{
		return new PathEnv(this.paths);
	}
	
	@Override
	public PathEnv mergeContext(PathEnv child)
	{
		return mergeContexts(Arrays.asList(child));
	}

	@Override
	public PathEnv mergeContexts(List<PathEnv> children)
	{
		PathEnv copy = copy();
		for (PathEnv child : children)
		{
			merge(this, copy.paths, child.paths);
		}
		return copy;
	}

	private static void merge(PathEnv parent, Set<Path> foo, Set<Path> child)
	{
		if (foo.isEmpty())
		{
			foo.addAll(child);
		}
		else
		{
			Set<Path> tmp = new HashSet<>(foo);
			foo.clear();
			for (Path p1 : tmp)
			{
				for (Path p2 : child)
				{
					foo.add(p1.concat(p2));
				}
			}
		}
	}
	
	@Override
	public PathEnv composeContext(PathEnv child)
	{
		PathEnv copy = copy();
		compose(copy.paths, child.paths);
		return copy;
	}

	private static void compose(Set<Path> foo, Set<Path> child)
	{
		foo.addAll(child);
	}
	
	public PathEnv append(PathElement pe)
	{
		if (this.paths.isEmpty())
		{
			Path p = new Path(Arrays.asList(pe));
			Set<Path> ps = new HashSet<>();
			ps.add(p);
			return new PathEnv(ps);
		}
		else
		{
			return new PathEnv(this.paths.stream().map((p) -> p.append(pe)).collect(Collectors.toSet()));
		}
	}

	public Set<Path> getPaths()
	{
		return Collections.unmodifiableSet(this.paths);
	}
	
	public Set<Role> getRoles()
	{
		return this.paths.stream().flatMap((p) -> p.getRoles().stream()).collect(Collectors.toSet());
	}

	@Override
	public String toString()
	{
		return "paths=" + this.paths;
	}
}
