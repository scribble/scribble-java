package org.scribble.ast.context.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;

public class LProtocolDeclContext extends ProtocolDeclContext<Local>
{
	protected LProtocolDeclContext(Set<Role> roles, LDependencyMap deps)
	{
		super(roles, deps);
	}

	public LProtocolDeclContext(LDependencyMap deps)
	{
		this(Collections.emptySet(), deps);
	}
	
	@Override
	protected LProtocolDeclContext copy()
	{
		return new LProtocolDeclContext(getRoleOccurrences(), getDependencyMap());
	}
	
	@Override
	public LDependencyMap getDependencyMap()
	{
		return (LDependencyMap) super.getDependencyMap();
	}
}
