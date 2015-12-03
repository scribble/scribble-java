package org.scribble.model.global;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.Role;

// Mutable
// FIXME: should be Communication, cf. IOAction (superclass?)
public class ModelAction
{
	private static int counter = 1;

	public final int id;
	public final Role src;
	public final IOAction action;

	private Set<ModelAction> deps = new HashSet<>();
	
	public ModelAction(Role src, IOAction action)
	//public ModelAction(Role peer, MessageId mid)  // Should be a send/receive
	{
		this.id = counter++;
		this.src = src;
		this.action = action;
	}
	
	public void addDependency(ModelAction ma)
	{
		this.deps.add(ma);
	}
	
	public Set<ModelAction> getDependencies()
	{
		return this.deps;
	}
	
	public boolean isDependentOn(ModelAction ma)
	{
		return this.deps.contains(ma);
	}
	
	@Override
	public int hashCode()
	{
		return 827 * this.id;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ModelAction))
		{
			return false;
		}
		return this.id == ((ModelAction) o).id;
	}

	@Override
	public String toString()
	{
		return this.id + ":" + this.src + ":" + this.action + " " + this.deps.stream().map((d) -> d.id).collect(Collectors.toList());
	}
}