package org.scribble.visit.env;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.model.global.GModelAction;
import org.scribble.sesstype.name.Role;

@Deprecated
public class ModelEnv extends Env<ModelEnv>
{
	private Set<GModelAction> actions;
	private Map<Role, GModelAction> leaves;
	
	public ModelEnv()
	{
		this.actions = new HashSet<>();
		this.leaves = new HashMap<>();
	}

	protected ModelEnv(Collection<GModelAction> actions, Map<Role, GModelAction> leaves)
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
	
	public Set<GModelAction> getActions()
	{
		return Collections.unmodifiableSet(this.actions);
	}
	
	public ModelEnv setActions(Collection<GModelAction> actions, Map<Role, GModelAction> leaves)
	{
		ModelEnv copy = new ModelEnv();
		copy.actions = new HashSet<>(actions);
		copy.leaves = new HashMap<>(leaves);
		return copy;
	}

	public Map<Role, GModelAction> getLeaves()
	{
		return Collections.unmodifiableMap(this.leaves);
	}
}
