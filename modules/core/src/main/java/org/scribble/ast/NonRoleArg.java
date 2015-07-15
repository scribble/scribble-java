package org.scribble.ast;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.name.Role;

public class NonRoleArg extends DoArg<NonRoleArgNode>
{
	public NonRoleArg(NonRoleArgNode arg)
	{
		super(arg);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArg(getVal());
	}
	
	@Override
	public NonRoleArg clone()
	{
		NonRoleArgNode arg = (NonRoleArgNode) getVal().clone();
		return AstFactoryImpl.FACTORY.NonRoleArg(arg);
	}

	@Override
	public NonRoleArg reconstruct(NonRoleArgNode arg)
	{
		ScribDel del = del();
		NonRoleArg ai = new NonRoleArg(arg);
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
		return AstFactoryImpl.FACTORY.NonRoleArg(getVal());  // arg needs projection?
	}
}
