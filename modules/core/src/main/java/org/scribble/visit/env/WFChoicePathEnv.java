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

public class WFChoicePathEnv extends Env<WFChoicePathEnv>
{
	private final Set<Path> paths;  // FIXME: LinkedHashSet?  or just Deque?
	
	public WFChoicePathEnv()
	{
		this(Collections.emptySet());
	}
	
	protected WFChoicePathEnv(Set<Path> paths)
	{
		this.paths = new HashSet<>(paths);
	}

	@Override
	protected WFChoicePathEnv copy()
	{
		return new WFChoicePathEnv(this.paths);
	}
	
	public WFChoicePathEnv clear()
	{
		WFChoicePathEnv copy = copy();
		copy.paths.clear();
		return copy;
	}

	@Override
	public WFChoicePathEnv enterContext()
	{
		return new WFChoicePathEnv(this.paths);
	}
	
	@Override
	public WFChoicePathEnv mergeContext(WFChoicePathEnv child)
	{
		return mergeContexts(Arrays.asList(child));
	}

	@Override
	public WFChoicePathEnv mergeContexts(List<WFChoicePathEnv> children)
	{
		WFChoicePathEnv copy = copy();
		for (WFChoicePathEnv child : children)
		{
			merge(copy.paths, child.paths);
		}
		return copy;
	}

	private static void merge(Set<Path> foo, Set<Path> child)
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
				if (p1.isExit())  // Sound?
				{
					for (Path p2 : child)
					{
						foo.add(p1.concat(p2));
					}
				}
			}
		}
	}
	
	@Override
	public WFChoicePathEnv composeContext(WFChoicePathEnv child)
	{
		WFChoicePathEnv copy = copy();
		compose(copy.paths, child.paths);
		return copy;
	}

	private static void compose(Set<Path> foo, Set<Path> child)
	{
		foo.addAll(child);
	}
	
	public WFChoicePathEnv append(PathElement pe)
	{
		if (this.paths.isEmpty())
		{
			//Path p = new Path(Arrays.asList(pe));
			Path p = new Path(pe);
			Set<Path> ps = new HashSet<>();
			ps.add(p);
			return new WFChoicePathEnv(ps);
		}
		else
		{
			return new WFChoicePathEnv(this.paths.stream().map((p) -> p.append(pe)).collect(Collectors.toSet()));
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
