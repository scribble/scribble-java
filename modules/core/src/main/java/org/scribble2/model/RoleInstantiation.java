package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.KindedRoleNode;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Role;


//public class RoleInstantiation extends Instantiation<RoleNode>
public class RoleInstantiation extends Instantiation<KindedRoleNode>
{
	//public RoleInstantiation(RoleNode arg)
	public RoleInstantiation(KindedRoleNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new RoleInstantiation(this.arg);
	}	

	@Override
	//protected RoleInstantiation reconstruct(RoleNode arg)
	protected RoleInstantiation reconstruct(KindedRoleNode arg)
	{
		ModelDelegate del = del();
		RoleInstantiation ri = new RoleInstantiation(arg);
		ri = (RoleInstantiation) ri.del(del);
		return ri;
	}
	
	// FIXME: move to delegate?
	@Override
	public RoleInstantiation project(Role self)
	{
		//RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, this.arg.toName().toString());
		SimpleKindedNameNode<RoleKind> rn = ModelFactoryImpl.FACTORY.SimpleKindedNameNode(RoleKind.KIND, this.arg.toName().toString());
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
