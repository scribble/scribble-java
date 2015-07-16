package org.scribble.ast.context.local;

import org.scribble.ast.context.DependencyMap;
import org.scribble.sesstype.name.LProtocolName;

public class LDependencyMap extends DependencyMap<LProtocolName>
{
	protected LDependencyMap(LDependencyMap deps)
	{
		super(deps);
	}
	
	public LDependencyMap()
	{
		super();
	}

	@Override
	public LDependencyMap clone()
	{
		return new LDependencyMap(this);
	}
}
