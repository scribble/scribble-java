package org.scribble.ext.go.type.index;

import java.util.HashSet;
import java.util.Set;

import org.scribble.ext.go.type.name.ParamRoleParam;

// Variable occurrence
public class ParamIndexVar extends ParamIndexExpr
{
	public final String name; 

	protected ParamIndexVar(String name)
	{
		this.name = name; 
	}
	
	public ParamRoleParam toRoleParam()
	{
		return new ParamRoleParam(this.name);
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
	public Set<ParamIndexVar> getVars()
	{
		Set<ParamIndexVar> vars = new HashSet<>();
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
		if (!(o instanceof ParamIndexVar))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.name.equals(((ParamIndexVar) o).name);
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof ParamIndexVar;
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
