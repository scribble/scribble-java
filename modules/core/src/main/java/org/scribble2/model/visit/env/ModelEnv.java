package org.scribble2.model.visit.env;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.model.ModelAction;
import org.scribble2.sesstype.name.Role;


public class ModelEnv extends Env
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
	
	public ModelEnv setActions(Collection<ModelAction> actions, Map<Role, ModelAction> leaves)
	{
		ModelEnv copy = new ModelEnv();
		copy.actions = new HashSet<>(actions);
		copy.leaves = new HashMap<>(leaves);
		return copy;
	}
	
	public Set<ModelAction> getActions()
	{
		return this.actions;
	}

	public Map<Role, ModelAction> getLeaves()
	{
		return this.leaves;
	}
	
	/*protected ModelEnv()
	{
	}*/

	@Override
	protected ModelEnv copy()
	{
		return new ModelEnv(this.actions, this.leaves);
	}

	@Override
	public Env enterContext()
	{
		return copy();
	}
}
