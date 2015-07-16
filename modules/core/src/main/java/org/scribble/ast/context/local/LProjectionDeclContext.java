package org.scribble.ast.context.local;

import java.util.Set;

import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;


@Deprecated
public class LProjectionDeclContext extends LProtocolDeclContext
{
	private GProtocolName fullname;
	private Role self;
	
	protected LProjectionDeclContext(Set<Role> roles, LDependencyMap deps, GProtocolName gpn, Role self)
	{
		super(roles, deps);
	}
	
	@Override
	public LProjectionDeclContext copy()
	{
		return new LProjectionDeclContext(getRoleOccurrences(), getDependencyMap(), getSourceProtocol(), getSelfRole());
	}
	
	public GProtocolName getSourceProtocol()
	{
		return this.fullname;
	}
	
	// Redundant with SelfRoleDecl in header
	public Role getSelfRole()
	{
		return this.self;
	}
}

