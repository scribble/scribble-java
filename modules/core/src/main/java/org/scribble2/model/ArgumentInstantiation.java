package org.scribble2.model;

import org.scribble2.model.del.ModelDel;
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

	@Override
	protected ArgumentInstantiation reconstruct(ArgumentNode arg)
	{
		ModelDel del = del();
		ArgumentInstantiation ai = new ArgumentInstantiation(arg);
		ai = (ArgumentInstantiation) ai.del(del);
		return ai;
	}
	
	@Override
	public ArgumentInstantiation project(Role self)
	{
		/*ArgumentNode arg = (ArgumentNode) ((ProjectionEnv) this.arg.del().env()).getProjection();	
		return new ArgumentInstantiation(arg);*/
		//ArgumentNode an = new ArgumentNode(this.arg.toName().toString());
		//return new ArgumentInstantiation(this.arg);  // FIXME: arg needs projection?
		return ModelFactoryImpl.FACTORY.ArgumentInstantiation(this.arg);  // FIXME: arg needs projection?
	}
}
