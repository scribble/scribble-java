package org.scribble.ast.context.global;

import org.scribble.ast.context.DependencyMap;
import org.scribble.sesstype.name.GProtocolName;

public class GDependencyMap extends DependencyMap<GProtocolName>
{
	protected GDependencyMap(GDependencyMap deps)
	{
		super(deps);
	}

	public GDependencyMap()
	{
		super();
	}

	@Override
	public GDependencyMap clone()
	{
		return new GDependencyMap(this);
	}
}
