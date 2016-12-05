package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.name.Role;

public class NonRoleArg extends DoArg<NonRoleArgNode>
{
	public NonRoleArg(CommonTree source, NonRoleArgNode arg)
	{
		super(source, arg);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArg(this.source, getVal());
	}
	
	@Override
	public NonRoleArg clone()
	{
		NonRoleArgNode arg = (NonRoleArgNode) getVal().clone();
		return AstFactoryImpl.FACTORY.NonRoleArg(this.source, arg);
	}

	@Override
	public NonRoleArg reconstruct(NonRoleArgNode arg)
	{
		ScribDel del = del();
		NonRoleArg ai = new NonRoleArg(this.source, arg);
		ai = (NonRoleArg) ai.del(del);
		return ai;
	}
	
	@Override
	public NonRoleArgNode getVal()
	{
		return (NonRoleArgNode) super.getVal();
	}
	
	@Override
	public NonRoleArg project(Role self)
	{
		return AstFactoryImpl.FACTORY.NonRoleArg(this.source, getVal());  // arg needs projection?
	}
}
