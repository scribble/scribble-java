package org.scribble2.model;

import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.name.Role;


public class RoleInstantiation extends Instantiation<RoleNode>
{
	public RoleInstantiation(RoleNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new RoleInstantiation(this.arg);
	}	
	
	// FIXME: move to delegate?
	public RoleInstantiation project(Role self)
	{
		RoleNode rn = new RoleNode(this.arg.toName().toString());
		return new RoleInstantiation(rn);
	}
	
	/*@Override
	public RoleInstantiation substitute(Substitutor subs) throws ScribbleException
	{
		RoleNode arg = subs.substituteRole(this.arg.toName());
		return new RoleInstantiation(this.ct, arg, this.param);
	}
	
	@Override
	public boolean hasTargetParameter()
	{
		return this.param != null;
	}
	
	@Override
	public RoleInstantiation collectRoles(RoleCollector rc) throws ScribbleException
	{
		//rc.addRole(this.arg.toName());
		return (RoleInstantiation) super.collectRoles(rc);
	}* /

	@Override
	public String toString()
	{
		return this.arg.toString();
	}*/
}
