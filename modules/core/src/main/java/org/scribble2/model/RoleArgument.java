package org.scribble2.model;

import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Role;


public class RoleArgument extends DoArg<RoleNode>
{
	public RoleArgument(RoleNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new RoleArgument(this.val);
	}	

	@Override
	protected RoleArgument reconstruct(RoleNode arg)
	{
		ModelDel del = del();
		RoleArgument ri = new RoleArgument(arg);
		ri = (RoleArgument) ri.del(del);
		return ri;
	}
	
	// FIXME: move to delegate?
	@Override
	public RoleArgument project(Role self)
	{
		//RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, this.arg.toName().toString());
		RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.val.toName().toString());
		//return new RoleInstantiation(rn);
		return ModelFactoryImpl.FACTORY.RoleInstantiation(rn);
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
