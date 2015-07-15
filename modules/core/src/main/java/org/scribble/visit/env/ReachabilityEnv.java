package org.scribble.visit.env;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.sesstype.name.RecVar;

public class ReachabilityEnv extends Env
{
	private final Set<RecVar> contlabs;  // Used to check non-tail recursion (in the presence of sequencing)
	private boolean contExitable;  // false after a continue; true if choice has an exit (false inherited for all other constructs)
	
	public ReachabilityEnv()
	{
		this(Collections.emptySet(), true);
	}
	
	protected ReachabilityEnv(Set<RecVar> contlabs, boolean contExitable)
	{
		this.contlabs = new HashSet<RecVar>(contlabs);
		this.contExitable = contExitable;
	}
	
	public boolean isExitable()
	{
		return this.contlabs.isEmpty() && this.contExitable;
	}

	@Override
	public ReachabilityEnv copy()
	{
		return new ReachabilityEnv(this.contlabs, this.contExitable);
	}

	@Override
	public ReachabilityEnv enterContext()
	{
		return copy();
	}

	@Override
	public ReachabilityEnv mergeContext(Env child)
	{
		return mergeContexts(Arrays.asList((ReachabilityEnv) child));  // Should not be used for single block choice 
	}

	// Shouldn't be used for Choice
	@Override
	public ReachabilityEnv mergeContexts(List<? extends Env> children)
	{
		return merge(false, castList(children));
	}

	public ReachabilityEnv mergeForChoice(List<ReachabilityEnv> children)
	{
		return merge(true, children);
	}

	// Does merge depend on choice/par etc?
	private ReachabilityEnv merge(boolean isChoice, List<ReachabilityEnv> children)
	{
		ReachabilityEnv copy = copy();
		children.stream().forEach((e) -> copy.contlabs.addAll(e.contlabs));
		copy.contExitable =
				(isChoice)
					? children.stream().filter((e) -> e.contExitable).count() > 0
					: children.stream().filter((e) -> !e.contExitable).count() == 0;
		return copy;
	}
	
	public ReachabilityEnv leaveContinue(RecVar recvar)
	{
		ReachabilityEnv copy = copy();
		copy.contlabs.add(recvar);
		copy.contExitable = false;
		return copy;
	}
	
	public ReachabilityEnv removeContinueLabel(RecVar recvar)
	{
		ReachabilityEnv copy = copy();
		copy.contlabs.remove(recvar);
		return copy;
	}
	
	private static List<ReachabilityEnv> castList(List<? extends Env> envs)
	{
		return envs.stream().map((e) -> (ReachabilityEnv) e).collect(Collectors.toList());
	}
}
