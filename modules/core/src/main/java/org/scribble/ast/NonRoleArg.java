package org.scribble.ast;

import org.scribble.del.ModelDel;
import org.scribble.sesstype.name.Role;


public class NonRoleArg extends DoArg<ArgNode>
{
	public NonRoleArg(ArgNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new NonRoleArg(this.val);
	}

	@Override
	protected NonRoleArg reconstruct(ArgNode arg)
	{
		ModelDel del = del();
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
		return ModelFactoryImpl.FACTORY.NonRoleArg(this.val);  // FIXME: arg needs projection?
	}
}
