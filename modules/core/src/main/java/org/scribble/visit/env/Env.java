package org.scribble.visit.env;

import java.util.List;

// Immutable
// Could use generic parameter for ? extends Env (e.g. for merge arguments)
public abstract class Env
{
	protected Env()
	{

	}
	
	protected abstract Env copy();  // Shallow copy

	// Default push for entering a compound interaction context (e.g. used in CompoundInteractionDelegate)
	public abstract Env enterContext();

	// Mostly for merging a compound interaction node context into the parent block context
	// Usually used in the base compound interaction node del when leaving and restoring the parent env in the visitor env stack (e.g. CompoundInteractionNodeDel for WF-choice)
  // By default: merge just discards the argument(s) -- not all EnvVisitors need to merge (e.g. projection)
	protected Env mergeContext(Env env)
	{
		//return mergeContexts(Arrays.asList(env));
		return this;
	}

	// Mostly for merging child blocks contexts into the parent compound interaction node context
	// Usually used in the parent compound interaction node del to update the parent context after visting each child block before leaving (e.g. GChoiceDel for WF-choice)
	protected Env mergeContexts(List<? extends Env> envs)
	{
		return this;
	}
}
