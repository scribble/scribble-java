package org.scribble.visit.env;

import java.util.Arrays;
import java.util.List;

// Cf. UnfoldingEnv
public class ChoiceUnguardedSubprotocolEnv extends Env<ChoiceUnguardedSubprotocolEnv>
{
	private boolean shouldPrune;
	
	public ChoiceUnguardedSubprotocolEnv()
	{
		this.shouldPrune = true;
	}

	protected ChoiceUnguardedSubprotocolEnv(boolean shouldUnfold)
	{
		this.shouldPrune = shouldUnfold;
	}

	@Override
	protected ChoiceUnguardedSubprotocolEnv copy()
	{
		return new ChoiceUnguardedSubprotocolEnv(this.shouldPrune);
	}

	@Override
	public ChoiceUnguardedSubprotocolEnv enterContext()
	{
		return copy();
	}

	@Override
	public ChoiceUnguardedSubprotocolEnv mergeContext(ChoiceUnguardedSubprotocolEnv env)
	{
		return mergeContexts(Arrays.asList(env));
	}

	@Override
	public ChoiceUnguardedSubprotocolEnv mergeContexts(List<ChoiceUnguardedSubprotocolEnv> envs)
	{
		ChoiceUnguardedSubprotocolEnv copy = copy();
		boolean merge = (envs.stream().filter((e) -> !e.shouldPrune).count() > 0);  // Look for false, cf. UnfoldingEnv
		copy.shouldPrune = merge;
		return copy;
	}

	public boolean shouldPrune()
	{
		return this.shouldPrune;
	}

	public ChoiceUnguardedSubprotocolEnv pushLProtocolDeclParent()
	{
		ChoiceUnguardedSubprotocolEnv copy = copy();
		copy.shouldPrune = true;
		return copy;
	}
	
	public ChoiceUnguardedSubprotocolEnv disablePrune()
	{	
		ChoiceUnguardedSubprotocolEnv copy = copy();
		copy.shouldPrune = false;
		return copy;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ": " + this.shouldPrune;
	}
}
