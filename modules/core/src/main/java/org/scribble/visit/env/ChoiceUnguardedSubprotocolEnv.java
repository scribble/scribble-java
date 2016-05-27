package org.scribble.visit.env;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.sesstype.name.Role;

// Cf. UnfoldingEnv
public class ChoiceUnguardedSubprotocolEnv extends Env<ChoiceUnguardedSubprotocolEnv>
{
	private boolean shouldPrune;
	
	public Set<Role> subjs;
	
	public ChoiceUnguardedSubprotocolEnv()
	{
		this.shouldPrune = true;
		this.subjs = new HashSet<>();
	}

	protected ChoiceUnguardedSubprotocolEnv(boolean shouldUnfold, Set<Role> subjs)
	{
		this.shouldPrune = shouldUnfold;
		this.subjs = new HashSet<>(subjs);
	}

	@Override
	protected ChoiceUnguardedSubprotocolEnv copy()
	{
		return new ChoiceUnguardedSubprotocolEnv(this.shouldPrune, this.subjs);
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
		
		copy.subjs = envs.stream().flatMap((e) -> e.subjs.stream()).collect(Collectors.toSet());
		
		return copy;
	}
	
	public ChoiceUnguardedSubprotocolEnv setChoiceSubject(Role r)
	{
		ChoiceUnguardedSubprotocolEnv copy = copy();
		if (copy.subjs.isEmpty())
		{
			//System.out.println("BBB: " + r);
			copy.subjs.add(r);
		}
		return copy;
	}

	public boolean shouldPrune()
	{
		return this.shouldPrune;
	}

	/*public ChoiceUnguardedSubprotocolEnv pushLProtocolDeclParent()
	{
		ChoiceUnguardedSubprotocolEnv copy = copy();
		copy.shouldPrune = true;
		return copy;
	}*/
	
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
