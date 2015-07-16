package org.scribble.visit.env;

import java.util.Arrays;
import java.util.List;

public class UnfoldingEnv extends Env<UnfoldingEnv>
{
	private boolean shouldUnfold;
	
	public UnfoldingEnv()
	{
		this.shouldUnfold = false;
	}

	protected UnfoldingEnv(boolean shouldUnfold)
	{
		this.shouldUnfold = shouldUnfold;
	}

	@Override
	protected UnfoldingEnv copy()
	{
		return new UnfoldingEnv(this.shouldUnfold);
	}

	@Override
	public UnfoldingEnv enterContext()
	{
		return copy();
	}

	@Override
	public UnfoldingEnv mergeContext(UnfoldingEnv env)
	{
		return mergeContexts(Arrays.asList(env));
	}

	@Override
	public UnfoldingEnv mergeContexts(List<UnfoldingEnv> envs)
	{
		UnfoldingEnv copy = copy();
		boolean merge = (envs.stream().filter((e) -> e.shouldUnfold).count() > 0);
		copy.shouldUnfold = merge;
		return copy;
	}

	public boolean shouldUnfold()
	{
		return this.shouldUnfold;
	}

	public UnfoldingEnv pushChoiceParent()
	{
		UnfoldingEnv copy = copy();
		copy.shouldUnfold = true;
		return copy;
	}
	
	public UnfoldingEnv noUnfold()
	{	
		UnfoldingEnv copy = copy();
		copy.shouldUnfold = false;
		return copy;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ": " + this.shouldUnfold;
	}
}
