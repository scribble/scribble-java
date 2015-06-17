package org.scribble.ast;

import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ModelDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;


public class RoleArg extends DoArg<RoleNode>
{
	public RoleArg(RoleNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new RoleArg(this.val);
	}	

	@Override
	protected RoleArg reconstruct(RoleNode arg)
	{
		ModelDel del = del();
		RoleArg ri = new RoleArg(arg);
		ri = (RoleArg) ri.del(del);
		return ri;
	}
	
	// FIXME: move to delegate?
	@Override
	public RoleArg project(Role self)
	{
		//RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, this.arg.toName().toString());
		RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.val.toName().toString());
		//return new RoleInstantiation(rn);
		return ModelFactoryImpl.FACTORY.RoleArg(rn);
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
