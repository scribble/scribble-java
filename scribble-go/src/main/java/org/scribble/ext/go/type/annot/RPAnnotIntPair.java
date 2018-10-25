package org.scribble.ext.go.type.annot;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.util.Smt2Translator;


// Currently only considered as a value
public class RPAnnotIntPair extends RPAnnotExpr implements RPAnnotVal
{
	public final RPAnnotExpr left;
	public final RPAnnotExpr right;

	protected RPAnnotIntPair(RPAnnotExpr left, RPAnnotExpr right)
	{
		//if (!isUnaryExpr(left) || !isUnaryExpr(right))
		if (!(left instanceof RPAnnotInt) || !(right instanceof RPAnnotInt))
		{
			throw new RuntimeException("TODO: (" + left + ", " + right + ")");
		}
		this.left = left;
		this.right = right;
	}
	
	/*private static boolean isUnaryExpr(RPAnnotExpr e)
	{
		return (e instanceof RPAnnotInt) || (e instanceof RPAnnotVar);
	}*/
	
	@Override
	public String toGoString()
	{
		return "(session2.XY(" + this.left.toGoString() + ", " + this.right.toGoString() + "))";
	}
	
	@Override
	public String toSmt2Formula(Smt2Translator smt2t)
	{
		String left = this.left.toSmt2Formula(smt2t);
		String right = this.right.toSmt2Formula(smt2t);
		return "(mk-pair " + left + " " + right + ")";
	}

	@Override
	public Set<RPAnnotExpr> getVals()
	{
		return Stream.of(this).collect(Collectors.toSet());
	}

	@Override
	public Set<RPAnnotVar> getVars()
	{
		//return Stream.of(this.left.getVars(), this.right.getVars()).flatMap(x -> x.stream()).collect(Collectors.toSet());
		return Collections.emptySet();  // Currently only considered as a value
	}

	@Override
	public String toString()
	{
		return "(" + this.left.toString() + ", " + this.right.toString() + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPAnnotIntPair))
		{
			return false;
		}
		RPAnnotIntPair f = (RPAnnotIntPair) o;
		return super.equals(this)  // Does canEqual
				&& this.left.equals(f.left) && this.right.equals(f.right);  
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPAnnotIntPair;
	}

	@Override
	public int hashCode()
	{
		int hash = 6311;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.left.hashCode();
		hash = 31 * hash + this.right.hashCode();
		return hash;
	}
}
