package org.scribble2.model;

import org.scribble2.sesstype.name.Role;


public class ArgumentInstantiation extends Instantiation<ArgumentNode>
{
	public ArgumentInstantiation(ArgumentNode arg)
	{
		super(arg);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new ArgumentInstantiation(this.arg);
	}
	
	public ArgumentInstantiation project(Role self)
	{
		/*ArgumentNode arg = (ArgumentNode) ((ProjectionEnv) this.arg.del().env()).getProjection();	
		return new ArgumentInstantiation(arg);*/
		throw new RuntimeException("TODO");
	}
}
