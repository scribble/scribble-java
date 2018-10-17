package org.scribble.ext.go.type.index;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.util.Smt2Translator;

// Integer literal
public class RPIndexInt extends RPIndexExpr implements RPIndexVal
{
	public final int val;

	protected RPIndexInt(int i)
	{
		this.val = i; 
	}

	@Override
	public boolean gtEq(RPIndexVal them)
	{
		return this.val >= ((RPIndexInt) them).val;
	}

	@Override
	public RPIndexExpr minimise(int self)
	{
		return this;
	}

	@Override
	public boolean isConstant()
	{
		return true;
	}
	
	@Override
	public String toGoString()
	{
		return toString();
	}
		
	@Override
	public String toSmt2Formula(Smt2Translator smt2t)
	{
		return Integer.toString(this.val);
	}

	@Override
	public Set<RPIndexExpr> getVals()
	{
		return Stream.of(this).collect(Collectors.toSet());
	}
	
	@Override
	public Set<RPIndexVar> getVars()
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
		if (!(o instanceof RPIndexInt))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.val == ((RPIndexInt) o).val;
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPIndexInt;
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
