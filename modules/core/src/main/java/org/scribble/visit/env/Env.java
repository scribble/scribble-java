package org.scribble.visit.env;

import java.util.List;



// Immutable
public abstract class Env
{
	protected Env()
	{

	}
	
	protected abstract Env copy();  // Shallow copy

	// Default push for entering a compound interaction context (e.g. used in CompoundInteractionDelegate)
	public abstract Env enterContext();

  //  By default: merge just discards the argument(s) -- not all EnvVisitors need to merge (e.g. projection)
	protected Env mergeContext(Env env)
	{
		return this;
	}

	protected Env mergeContexts(List<? extends Env> envs)
	{
		return this;
	}
}
