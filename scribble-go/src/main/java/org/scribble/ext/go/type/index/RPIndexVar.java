package org.scribble.ext.go.type.index;

import java.util.HashSet;
import java.util.Set;

// Variable occurrence
public class RPIndexVar extends RPIndexExpr  // FIXME: extend AbstractName?  cf. Role -- e.g., for compatibility with NameCollector
{
	public final String name; 

	protected RPIndexVar(String name)
	{
		this.name = name; 
	}
	
	/*public RPRoleParam toRoleParam()
	{
		return new RPRoleParam(this.name);
	}*/
	
	@Override
	public String toGoString()
	{
		return this.name;
	}
		
	@Override
	public String toSmt2Formula()
	{
		/*if (this.name.startsWith("_dum"))  // FIXME
		{
			throw new RuntimeException("[assrt] Use squash first: " + this);
		}*/
		//return "(" + this.name + ")";
		return this.name;
	}
	
	@Override
	public Set<RPIndexVar> getVars()
	{
		Set<RPIndexVar> vars = new HashSet<>();
		vars.add(this);  // FIXME: currently may also be a role
		return vars; 
	}
	
	@Override
	public String toString()
	{
		return this.name; 
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPIndexVar))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.name.equals(((RPIndexVar) o).name);
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPIndexVar;
	}

	@Override
	public int hashCode()
	{
		int hash = 5903;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.name.hashCode();
		return hash;
	}
}
