package org.scribble.visit.wf.env;

import java.util.Arrays;
import java.util.List;

import org.scribble.visit.env.Env;

public class ExplicitCorrelationEnv extends Env<ExplicitCorrelationEnv>
{
	private boolean canAccept;
	
	public ExplicitCorrelationEnv()
	{
		this.canAccept = true;
	}

	protected ExplicitCorrelationEnv(boolean canAccept)
	{
		this.canAccept = canAccept;
	}

	@Override
	protected ExplicitCorrelationEnv copy()
	{
		return new ExplicitCorrelationEnv(this.canAccept);
	}

	@Override
	public ExplicitCorrelationEnv enterContext()
	{
		return copy();
	}

	@Override
	public ExplicitCorrelationEnv mergeContext(ExplicitCorrelationEnv env)
	{
		return mergeContexts(Arrays.asList(env));
	}

	@Override
	public ExplicitCorrelationEnv mergeContexts(List<ExplicitCorrelationEnv> envs)
	{
		ExplicitCorrelationEnv copy = copy();
		if (copy.canAccept)
		{
			copy.canAccept = !envs.stream().anyMatch((e) -> !e.canAccept);
		}
		return copy;
	}
	
	public boolean canAccept()
	{
		return this.canAccept;
	}
	
	public ExplicitCorrelationEnv disableAccept()
	{
		if (!this.canAccept)
		{
			return this;
		}
		ExplicitCorrelationEnv copy = copy();
		copy.canAccept = false;
		return copy;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ": " + this.canAccept;
	}
}
