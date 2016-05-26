package org.scribble.visit.env;

import java.util.Arrays;
import java.util.List;

// Cf. UnfoldingEnv
public class ProjectedSubprotocolPruningEnv extends Env<ProjectedSubprotocolPruningEnv>
{
	private boolean shouldPrune;
	
	public ProjectedSubprotocolPruningEnv()
	{
		this.shouldPrune = false;
	}

	protected ProjectedSubprotocolPruningEnv(boolean shouldUnfold)
	{
		this.shouldPrune = shouldUnfold;
	}

	@Override
	protected ProjectedSubprotocolPruningEnv copy()
	{
		return new ProjectedSubprotocolPruningEnv(this.shouldPrune);
	}

	@Override
	public ProjectedSubprotocolPruningEnv enterContext()
	{
		return copy();
	}

	@Override
	public ProjectedSubprotocolPruningEnv mergeContext(ProjectedSubprotocolPruningEnv env)
	{
		return mergeContexts(Arrays.asList(env));
	}

	@Override
	public ProjectedSubprotocolPruningEnv mergeContexts(List<ProjectedSubprotocolPruningEnv> envs)
	{
		ProjectedSubprotocolPruningEnv copy = copy();
		boolean merge = (envs.stream().filter((e) -> e.shouldPrune).count() > 0);
		copy.shouldPrune = merge;
		return copy;
	}

	public boolean shouldPrune()
	{
		return this.shouldPrune;
	}

	public ProjectedSubprotocolPruningEnv pushLProtocolDeclParent()
	{
		ProjectedSubprotocolPruningEnv copy = copy();
		copy.shouldPrune = true;
		return copy;
	}
	
	public ProjectedSubprotocolPruningEnv disablePrune()
	{	
		ProjectedSubprotocolPruningEnv copy = copy();
		copy.shouldPrune = false;
		return copy;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ": " + this.shouldPrune;
	}
}
