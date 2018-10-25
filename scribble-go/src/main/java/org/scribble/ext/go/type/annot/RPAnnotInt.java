package org.scribble.ext.go.type.annot;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.util.Smt2Translator;

// Integer literal
public class RPAnnotInt extends RPAnnotExpr implements RPAnnotVal
{
	public final int val;

	protected RPAnnotInt(int i)
	{
		this.val = i; 
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
	public Set<RPAnnotExpr> getVals()
	{
		return Stream.of(this).collect(Collectors.toSet());
	}
	
	@Override
	public Set<RPAnnotVar> getVars()
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
		if (!(o instanceof RPAnnotInt))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.val == ((RPAnnotInt) o).val;
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPAnnotInt;
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
