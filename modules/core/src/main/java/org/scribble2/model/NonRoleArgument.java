package org.scribble2.model;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.name.Role;


public class NonRoleArgument extends DoArgument<ArgumentNode>
{
	public NonRoleArgument(ArgumentNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new NonRoleArgument(this.arg);
	}

	@Override
	protected NonRoleArgument reconstruct(ArgumentNode arg)
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
		return ModelFactoryImpl.FACTORY.ArgumentInstantiation(this.arg);  // FIXME: arg needs projection?
	}
}
