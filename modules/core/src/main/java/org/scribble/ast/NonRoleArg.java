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
		return new NonRoleArg(this.val);
	}

	@Override
	protected NonRoleArg reconstruct(NonRoleArgNode arg)
	{
		ScribDel del = del();
		NonRoleArg ai = new NonRoleArg(arg);
		ai = (NonRoleArg) ai.del(del);
		return ai;
	}
	
	@Override
	public NonRoleArg project(Role self)
	{
		/*ArgumentNode arg = (ArgumentNode) ((ProjectionEnv) this.arg.del().env()).getProjection();	
		return new ArgumentInstantiation(arg);*/
		//ArgumentNode an = new ArgumentNode(this.arg.toName().toString());
		//return new ArgumentInstantiation(this.arg);  // FIXME: arg needs projection?
		return AstFactoryImpl.FACTORY.NonRoleArg(this.val);  // FIXME: arg needs projection?
	}
}
