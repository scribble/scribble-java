package org.scribble.visit.env;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnfoldingEnv extends InlineProtocolEnv
{
	//private Deque<Boolean> choiceParents = new LinkedList<>();
	private boolean shouldUnfold;
	
	public UnfoldingEnv()
	{
		this.shouldUnfold = false;
	}

	//protected UnfoldingEnv(Deque<Boolean> choiceParents)
	protected UnfoldingEnv(boolean shouldUnfold)
	{
		//this.choiceParents = new LinkedList<>(choiceParents);
		this.shouldUnfold = shouldUnfold;
	}

	@Override
	protected UnfoldingEnv copy()
	{
		//return new UnfoldingEnv(this.choiceParents);
		return new UnfoldingEnv(this.shouldUnfold);
	}

	@Override
	public UnfoldingEnv enterContext()
	{
		return copy();
	}

	@Override
	public UnfoldingEnv mergeContext(Env env)
	{
		return mergeContexts(Arrays.asList(env));
	}

	@Override
	public UnfoldingEnv mergeContexts(List<? extends Env> envs)
	{
		UnfoldingEnv copy = copy();
		//boolean merge = (castList(envs).stream().filter((e) -> e.choiceParents.peek()).count() > 0);
		boolean merge = (castList(envs).stream().filter((e) -> e.shouldUnfold).count() > 0);
		/*copy.choiceParents.pop();
		copy.choiceParents.push(merge);*/
		copy.shouldUnfold = merge;
		return copy;
	}

	public boolean shouldUnfold()
	{
		//return !this.choiceParents.isEmpty() && this.choiceParents.peek();
		return this.shouldUnfold;
	}

	public UnfoldingEnv pushChoiceParent()
	{
		//this.choiceParents.push(true);
		UnfoldingEnv copy = copy();
		copy.shouldUnfold = true;
		return copy;
	}
	
	public UnfoldingEnv noUnfold()
	{	
		/*if (!this.choiceParents.isEmpty())
		{
			this.choiceParents.pop();
			this.choiceParents.push(false);
		}*/
		UnfoldingEnv copy = copy();
		copy.shouldUnfold = false;
		return copy;
	}

	/*public void popChoiceParent()
	{
		this.choiceParents.pop();
	}*/
	
	@Override
	public String toString()
	{
		return super.toString() + ": " + this.shouldUnfold;
	}
	
	private static List<UnfoldingEnv> castList(List<? extends Env> envs)
	{
		return envs.stream().map((e) -> (UnfoldingEnv) e).collect(Collectors.toList());
	}
}
