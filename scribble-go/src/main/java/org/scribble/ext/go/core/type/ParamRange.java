package org.scribble.ext.go.core.type;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexVar;

public class ParamRange
{
	/*public final ParamRoleParam start;
	public final ParamRoleParam end;  // Inclusive*/
	
	public final ParamIndexExpr start;
	public final ParamIndexExpr end;
	
	//public ParamRange(ParamRoleParam start, ParamRoleParam end)
	public ParamRange(ParamIndexExpr start, ParamIndexExpr end)
	{
		this.start = start;
		this.end = end;
	}
	
	//public Set<ParamRoleParam> getActualParams()  // Hack
	public Set<ParamIndexVar> getVars()
	{
		//return Stream.of(this.start, this.end).filter(p -> !p.isConstant()).collect(Collectors.toSet());
		return Stream.of(this.start, this.end).flatMap(p -> p.getVars().stream()).collect(Collectors.toSet());
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
		if (!(obj instanceof ParamRange))
		{
			return false;
		}
		ParamRange them = (ParamRange) obj;
		return this.start.equals(them.start) && this.end.equals(them.end);
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ((this.start == this.end) ? "" : ".." + this.end) + "]";
	}
}
