package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;

public class RoleArg extends DoArg<RoleNode>
{
	public RoleArg(CommonTree source, RoleNode arg)
	{
		super(source, arg);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new RoleArg(this.source, getVal());
	}	
	
	@Override
	public RoleArg clone()
	{
		RoleNode role = getVal().clone();
		return AstFactoryImpl.FACTORY.RoleArg(this.source, role);
	}

	@Override
	public RoleArg reconstruct(RoleNode arg)
	{
		ScribDel del = del();
		RoleArg ri = new RoleArg(this.source, arg);
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
		RoleNode rn = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(this.val.source, RoleKind.KIND, this.val.toName().toString());
		return AstFactoryImpl.FACTORY.RoleArg(this.source, rn);
	}
}
