package org.scribble.ext.go.core.type;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;

public class RPInterval
{
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
	
	//public Set<ParamRoleParam> getActualParams()  // Hack
	public Set<RPIndexVar> getVars()
	{
		//return Stream.of(this.start, this.end).filter(p -> !p.isConstant()).collect(Collectors.toSet());
		return Stream.of(this.start, this.end).flatMap(p -> p.getVars().stream()).collect(Collectors.toSet());
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ((this.start == this.end) ? "" : ".." + this.end) + "]";
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
		return this.start.equals(them.start) && this.end.equals(them.end);
	}
}
