package org.scribble2.foo.ast;


public interface RoleInstantiation extends Instantiation//<RoleNode>
{
	/*public RoleInstantiation(CommonTree ct, RoleNode arg)
	{
		super(ct, arg);
	}	
	
	public RoleInstantiation project(Role self)
	{
		RoleNode rn = new RoleNode(null, this.arg.toName().toString());
		return new RoleInstantiation(null, rn);
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
