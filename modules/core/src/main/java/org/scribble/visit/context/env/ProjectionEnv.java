package org.scribble.visit.context.env;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LNode;
import org.scribble.visit.env.Env;

// Could make projection type a class parameter
public class ProjectionEnv extends Env<ProjectionEnv>
{
	private ScribNode projection;  // Make local  // FIXME: need to generalise for do projection (target protocol as well as the do)
	
	public ProjectionEnv()
	{

	}

	@Override
	public ProjectionEnv copy()
	{
		ProjectionEnv copy = new ProjectionEnv();
		copy.projection = this.projection;
		return copy;
	}

	@Override
	public ProjectionEnv enterContext()
	{
		return copy();
	}
	
	public ScribNode getProjection()
	{
		return this.projection;
	}

	public ProjectionEnv setProjection(LNode projection)
	{
		ProjectionEnv copy = new ProjectionEnv();
		copy.projection = projection;
		return copy;
	}

	@Override
	public String toString()
	{
		return "projection=" + this.projection;
	}
}
