package org.scribble.ast.context.global;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;

public class GProtocolDeclContext extends ProtocolDeclContext<Global>
{
	protected GProtocolDeclContext(Set<Role> roles, GDependencyMap deps)
	{
		super(roles, deps);
	}
	
	public GProtocolDeclContext(GDependencyMap deps)
	{
		this(Collections.emptySet(), deps);
	}
	
	@Override
	protected GProtocolDeclContext copy()
	{
		return new GProtocolDeclContext(getRoleOccurrences(), getDependencyMap());
	}
	
	@Override
	public GDependencyMap getDependencyMap()
	{
		return (GDependencyMap) super.getDependencyMap();
	}
}
