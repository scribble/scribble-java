package org.scribble.ext.go.type.annot;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.scribble.ext.go.util.Smt2Translator;

// Variable occurrence
public class RPAnnotVar extends RPAnnotExpr  // FIXME: extend AbstractName?  cf. Role -- e.g., for compatibility with NameCollector
{
	public final String name; 

	protected RPAnnotVar(String name)
	{
		this.name = name; 
	}

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
	public Set<RPAnnotExpr> getVals()
	{
		return Collections.emptySet();
	}

	@Override
	public Set<RPAnnotVar> getVars()
	{
		Set<RPAnnotVar> vars = new HashSet<>();
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
		if (!(o instanceof RPAnnotVar))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.name.equals(((RPAnnotVar) o).name);
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPAnnotVar;
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
