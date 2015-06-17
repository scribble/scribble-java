package org.scribble.ast.visit.env;

import org.scribble.ast.ScribNode;

// could make projection type a class parameter
public class ProjectionEnv extends Env
{
	private ScribNode projection;  // FIXME: need to generalise for do projection (target protocol as well as the do)
	
	//.. do projection for each node

	//public ProjectionEnv(JobContext jcontext, ModuleContext mcontext, Node projection)
	//public ProjectionEnv(JobContext jcontext, ModuleDelegate mcontext)
	public ProjectionEnv()
	{
		//super(jcontext, mcontext);
		//this.projection = projection;
	}

	// FIXME: make into a defensive setter (or just a setter?)
	//public ProjectionEnv(JobContext jcontext, ModuleDelegate mcontext, ModelNode projection)
	public ProjectionEnv(ScribNode projection)
	{
		//this(jcontext, mcontext);
		this.projection = projection;
	}
	
	/*protected ProjectionEnv(JobContext jcontext, ModuleContext mcontext, ProjectionEnv root, ProjectionEnv parent, Node projection)
	{
		super(jcontext, mcontext, root, parent);
		this.projection = projection;
	}*/
	
	//public LocalNode getProjection()
	public ScribNode getProjection()
	{
		return this.projection;
	}

	@Override
	public ProjectionEnv copy()
	{
		//return new ProjectionEnv(getJobContext(), getModuleDelegate(), this.projection);
		return new ProjectionEnv(this.projection);
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
	public ProjectionEnv enterContext()
	{
		//return new ProjectionEnv(getJobContext(), getModuleDelegate(), this.projection);
		return copy();
	}
}
