package org.scribble.codegen.statetype;

import org.scribble.model.endpoint.EState;

// Named state
// Directly maps each method signature to one state
public abstract class STState//<A extends STAction, S extends STState<? extends STAction, S>>
{
	public final EState state;
	
	//public final String name;  // State type name (typestate state) -- used as object id
	//public final String type;  // Fully-qualified host type name
	
	//public STState(String name, String type, Map<A, S> actions)
	public STState(EState state)
	{
		checkAPIGenConditions(state);
		
		this.state = state;
		//this.name = name;
		//this.type = type;
	}
	
	private static void checkAPIGenConditions(EState state)
	{
		// TODO: e.g., determinstic
	}
	
	public abstract String makeName();

	public abstract String makeType();
	
	/*public Map<A, S> getActions()
	{
		return actions;
	}*/
	
	/*@Override
	public int hashCode()
	{
		int hash = 83;
		hash = 31 * hash + this.state.hashCode();
		//hash = 31 * hash + this.name.hashCode();
		hash = 31 * hash + this.type.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof STState))
		{
			return false;
		}
		STState<?, ?> s = (STState<?, ?>) o;
		//return s.canEqual(this) && this.name.equals(s.name);
		return s.canEqual(this) && this.state.equals(s.state) && this.type.equals(s.type);
	}
	
	protected abstract boolean canEqual(Object o);*/
}
