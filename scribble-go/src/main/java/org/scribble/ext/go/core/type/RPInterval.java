package org.scribble.ext.go.core.type;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;

public class RPInterval
{
	public static final Comparator<RPInterval> COMPARATOR = new Comparator<RPInterval>()
			{
				@Override
				public int compare(RPInterval i1, RPInterval i2)
				{
					return i1.toString().compareTo(i2.toString());
				}
			};

	/*public final ParamRoleParam start;
	public final ParamRoleParam end;  // Inclusive*/
	
	public final RPIndexExpr start;
	public final RPIndexExpr end;
	
	//public ParamRange(ParamRoleParam start, ParamRoleParam end)
	public RPInterval(RPIndexExpr start, RPIndexExpr end)
	{
		this.start = start;
		this.end = end;
	}
	
	// FIXME: rename
	public RPInterval minimise(int self)
	{
		return new RPInterval(this.start.minimise(self), this.end.minimise(self));
	}
	
	public Set<RPIndexExpr> getIndexVals()
	{
		return Stream.of(this.start, this.end).flatMap(p -> p.getVals().stream()).collect(Collectors.toSet());
	}

	//public Set<ParamRoleParam> getActualParams()  // Hack
	public Set<RPIndexVar> getIndexVars()
	{
		//return Stream.of(this.start, this.end).filter(p -> !p.isConstant()).collect(Collectors.toSet());
		return Stream.of(this.start, this.end).flatMap(p -> p.getVars().stream()).collect(Collectors.toSet());
	}
	
	// FIXME: cf. equals -- this is to avoid awkwardness of RPInterval and RPAnnotatedInterval
	// Althoug, also cf. "contains" -- currently "syntactic equality", generalise?
	public boolean isSame(RPInterval i)
	{
		return this.start.equals(i.start) && this.end.equals(i.end); 
	}
	
	public boolean isSingleton()
	{
		return this.start.equals(this.end);
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ((this.start.equals(this.end)) ? "" : "," + this.end) + "]";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7151;
		hash = 31 * hash + this.start.hashCode();
		hash = 31 * hash + this.end.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof RPInterval))
		{
			return false;
		}
		RPInterval them = (RPInterval) obj;
		return them.canEqual(this) && this.start.equals(them.start) && this.end.equals(them.end);
	}
	
	public boolean canEqual(RPInterval o)
	{
		return o instanceof RPInterval;
	}
}
