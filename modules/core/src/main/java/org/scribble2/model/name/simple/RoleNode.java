package org.scribble2.model.name.simple;

import org.scribble2.model.RoleDecl;
import org.scribble2.model.RoleInstantiation;
import org.scribble2.sesstype.name.Role;


public class RoleNode extends SimpleNameNode implements RoleDecl, RoleInstantiation
{
	public RoleNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected RoleNode copy()
	{
		return new RoleNode(this.identifier);
	}
	
	/*@Override
	public RoleNode substitute(Substitutor subs)
	{
		return subs.getRoleSubstitution(toName());
	}*/
	
	@Override
	public Role toName()
	{
		return new Role(this.identifier);
	}
}
