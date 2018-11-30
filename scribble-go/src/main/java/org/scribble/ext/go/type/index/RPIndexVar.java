package org.scribble.ext.go.type.index;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.scribble.ext.go.util.Smt2Translator;

// Variable occurrence
public class RPIndexVar extends RPIndexExpr  // FIXME: extend AbstractName?  cf. Role -- e.g., for compatibility with NameCollector
{
	public static final Comparator<RPIndexVar> COMPARATOR = new Comparator<RPIndexVar>()
			{
				@Override
				public int compare(RPIndexVar i1, RPIndexVar i2)
				{
					return i1.toString().compareTo(i2.toString());
				}
			};

	public final String name; 

	protected RPIndexVar(String name)
	{
		this.name = name; 
	}

	@Override
	public RPIndexExpr minimise(int self)
	{
		return this;
	}
	
	/*public RPRoleParam toRoleParam()
	{
		return new RPRoleParam(this.name);
	}*/
	
	@Override
	public String toGoString()
	{
		return toString();
	}
		
	@Override
	public String toSmt2Formula(Smt2Translator smt2t)
	{
		/*if (this.name.startsWith("_dum"))  // FIXME
		{
			throw new RuntimeException("[assrt] Use squash first: " + this);
		}*/
		//return "(" + this.name + ")";
		return this.name;
	}
	
	@Override
	public Set<RPIndexExpr> getVals()
	{
		return Collections.emptySet();
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
