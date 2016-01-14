package org.scribble.ast;

import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;

public class RoleArg extends DoArg<RoleNode>
{
	public RoleArg(RoleNode arg)
	{
		super(arg);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new RoleArg(getVal());
	}	
	
	@Override
	public RoleArg clone()
	{
		RoleNode role = getVal().clone();
		return AstFactoryImpl.FACTORY.RoleArg(role);
	}

	@Override
	public RoleArg reconstruct(RoleNode arg)
	{
		ScribDel del = del();
		RoleArg ri = new RoleArg(arg);
		ri = (RoleArg) ri.del(del);
		return ri;
	}
	
	@Override
	public RoleNode getVal()
	{
		return (RoleNode) super.getVal();
	}
	
	// FIXME: move to delegate?
	@Override
	public RoleArg project(Role self)
	{
		RoleNode rn = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, this.val.toName().toString());
		return AstFactoryImpl.FACTORY.RoleArg(rn);
	}
}
