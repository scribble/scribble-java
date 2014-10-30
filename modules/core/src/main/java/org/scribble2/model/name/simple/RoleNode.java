package org.scribble2.model.name.simple;

import org.scribble2.model.RoleDecl;
import org.scribble2.model.RoleInstantiation;


public class RoleNode extends SimpleNameNode implements RoleDecl, RoleInstantiation
{
	public RoleNode(String name)
	{
		super(name);
	}
	
	/*@Override
	public RoleNode substitute(Substitutor subs)
	{
		return subs.getRoleSubstitution(toName());
	}
	
	@Override
	public Role toName()
	{
		return new Role(this.identifier);
	}*/
}
