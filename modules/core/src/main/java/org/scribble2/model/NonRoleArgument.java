package org.scribble2.model;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.name.Role;


public class NonRoleArgument extends DoArg<ArgNode>
{
	public NonRoleArgument(ArgNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new NonRoleArgument(this.val);
	}

	@Override
	protected NonRoleArgument reconstruct(ArgNode arg)
	{
		ModelDel del = del();
		NonRoleArgument ai = new NonRoleArgument(arg);
		ai = (NonRoleArgument) ai.del(del);
		return ai;
	}
	
	@Override
	public NonRoleArgument project(Role self)
	{
		/*ArgumentNode arg = (ArgumentNode) ((ProjectionEnv) this.arg.del().env()).getProjection();	
		return new ArgumentInstantiation(arg);*/
		//ArgumentNode an = new ArgumentNode(this.arg.toName().toString());
		//return new ArgumentInstantiation(this.arg);  // FIXME: arg needs projection?
		return ModelFactoryImpl.FACTORY.ArgumentInstantiation(this.val);  // FIXME: arg needs projection?
	}
}
