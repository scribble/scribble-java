package org.scribble2.model.visit.env;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ModuleDelegate;
import org.scribble2.model.visit.JobContext;

public class ProjectionEnv extends Env
{
	private ModelNode projection;  // FIXME: need to generalise for do projection (target protocol as well as the do)
	
	//.. do projection for each node

	//public ProjectionEnv(JobContext jcontext, ModuleContext mcontext, Node projection)
	public ProjectionEnv(JobContext jcontext, ModuleDelegate mcontext)
	{
		super(jcontext, mcontext);
		//this.projection = projection;
	}

	public ProjectionEnv(JobContext jcontext, ModuleDelegate mcontext, ModelNode projection)
	{
		this(jcontext, mcontext);
		this.projection = projection;
	}
	
	/*protected ProjectionEnv(JobContext jcontext, ModuleContext mcontext, ProjectionEnv root, ProjectionEnv parent, Node projection)
	{
		super(jcontext, mcontext, root, parent);
		this.projection = projection;
	}*/
	
	//public LocalNode getProjection()
	public ModelNode getProjection()
	{
		return this.projection;
	}

	@Override
	public ProjectionEnv copy()
	{
		return new ProjectionEnv(getJobContext(), getModuleDelegate(), this.projection);
	}

	/*@Override
	public ProjectionEnv getProtocolDeclEnv()
	{
		return (ProjectionEnv) super.getProtocolDeclEnv();
	}
	
	@Override
	public ProjectionEnv getParent()
	{
		return (ProjectionEnv) super.getParent();
	}*/

	@Override
	public String toString()
	{
		return "projection=" + this.projection;
	}

	@Override
	public ProjectionEnv push()
	{
		//return new ProjectionEnv(getJobContext(), getModuleDelegate(), this.projection);
		return copy();
	}
}
