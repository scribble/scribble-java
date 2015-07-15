package org.scribble.visit.env;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.model.global.ModelAction;
import org.scribble.sesstype.name.Role;

public class ModelEnv extends Env<ModelEnv>
{
	private Set<ModelAction> actions;
	private Map<Role, ModelAction> leaves;
	
	public ModelEnv()
	{
		this.actions = new HashSet<>();
		this.leaves = new HashMap<>();
	}

	protected ModelEnv(Collection<ModelAction> actions, Map<Role, ModelAction> leaves)
	{
		this.actions = new HashSet<>(actions);
		this.leaves = new HashMap<>(leaves);
	}

	@Override
	protected ModelEnv copy()
	{
		return new ModelEnv(this.actions, this.leaves);
	}

	@Override
	public ModelEnv enterContext()
	{
		return copy();
	}
	
	public Set<ModelAction> getActions()
	{
		return Collections.unmodifiableSet(this.actions);
	}
	
	public ModelEnv setActions(Collection<ModelAction> actions, Map<Role, ModelAction> leaves)
	{
		ModelEnv copy = new ModelEnv();
		copy.actions = new HashSet<>(actions);
		copy.leaves = new HashMap<>(leaves);
		return copy;
	}

	public Map<Role, ModelAction> getLeaves()
	{
		return Collections.unmodifiableMap(this.leaves);
	}
}
