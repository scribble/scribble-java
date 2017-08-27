package org.scribble.ext.go.type.index;

import java.util.Collections;
import java.util.Set;

// Integer literal
public class ParamIndexInt extends ParamIndexExpr
{
	public final int val;

	protected ParamIndexInt(int i)
	{
		this.val = i; 
	}

	@Override
	public boolean isConstant()
	{
		return true;
	}
		
	@Override
	public String toSmt2Formula()
	{
		return Integer.toString(this.val);
	}
	
	@Override
	public Set<ParamIndexVar> getVars()
	{
		return Collections.emptySet();	
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.val); 
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ParamIndexInt))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.val == ((ParamIndexInt) o).val;
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof ParamIndexInt;
	}

	@Override
	public int hashCode()
	{
		int hash = 5897;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.val;
		return hash;
	}
}
